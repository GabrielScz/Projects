/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.utl.dsm.bd.ConexionBD;
import org.utl.dsm.model.Armazon;
import org.utl.dsm.model.Cliente;
import org.utl.dsm.model.DetalleVentaPresupuestoLentes;
import org.utl.dsm.model.ExamenVista;
import org.utl.dsm.model.Graduacion;
import org.utl.dsm.model.Material;
import org.utl.dsm.model.Producto;
import org.utl.dsm.model.TipoMica;
import org.utl.dsm.model.Tratamiento;

/**
 *
 * @author carlossanchez
 */

public class ControllerVentaLente {
    
    public boolean generarVentaLentes(DetalleVentaPresupuestoLentes dvpl) {
        boolean r = false;

        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String query = "SELECT LAST_INSERT_ID()";

            //Se genera la venta
            String query0 = "INSERT INTO venta(idEmpleado, clave) VALUES ("
                    + dvpl.getVenta().getEmpleado().getIdEmpleado() + ","
                    + dvpl.getVenta().getClave() + ");";
            stmt.execute(query0);
            //Se obtiene el id genrado con la insercion de venta
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                dvpl.getVenta().setIdVenta(rs.getInt(1));
            }

            //Se insertan varios presupuestos, por lo tanto se Cicla
            for (int i = 0; i < dvpl.getListaVentaPresupuestoLentes().size(); i++) {

                //Se insertan los presupuestos
                String query1 = "INSERT INTO presupuesto(idExamenVista, clave)"
                        + "VALUES (" + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getPresupuesto().getExamenVista().getIdExamenVista()+ ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getPresupuesto().getClave()+ ");";
                stmt.execute(query1);
                rs = stmt.executeQuery(query);
                //Obtenemos el id de presupuesto y lo guardamos
                if (rs.next()) {
                    dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getPresupuesto().setIdPresupuesto(rs.getInt(1));
                }

                //Se insertan los presupuestos de lentes
                String query2 = "INSERT INTO presupuesto_Lentes(idPresupuesto, alturaOblea, idTipoMica, idMaterial, idArmazon)"
                        + "VALUES (" + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getPresupuesto().getIdPresupuesto() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getAlturaOblea() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getTipoMica().getIdTipoMica()+ ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getMaterial().getIdMaterial() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getArmazon().getIdArmazon() + ");";
                stmt.execute(query2);
                //Obtenermos el id de presupuesto_Lentes  y se guarda en su objeto
                rs = stmt.executeQuery(query);                
                if (rs.next()) {
                    dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().setIdPresupuestoLentes(rs.getInt(1));
                }
                
                //Se almacenana los tratamientos que tiene ese lente presupuestado
                //Va en un ciclo por que se tiene la posibilidad de elegir varios tratamientos
                for (int j = 0; j < dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getListaTratamiento().size(); j++) {
                    String query3 = "INSERT INTO presupuesto_lentes_tratamientos VALUES("
                            + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getIdPresupuestoLentes() + ","
                            + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getListaTratamiento().get(j).getIdTratamiento()+ ");";
                    stmt.execute(query3);
                    
                }
                //Query numero 4 para almacenar la relacion de venta_presupuesto
                String query4 = "INSERT INTO venta_presupuesto VALUES("
                        + dvpl.getVenta().getIdVenta() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPresupuestoLentes().getPresupuesto().getIdPresupuesto() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getCantidad() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getPrecioUnitario() + ","
                        + dvpl.getListaVentaPresupuestoLentes().get(i).getDescuento() + ");";
                stmt.execute(query4);
            }
            
            //Ya con todas las sentencias ejecutadas, se envia la conformación de ejecutar la transaccion
            conn.commit();
            //Se cierran los objetos de BD
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            r = true;

        } catch (SQLException ex) {
            Logger.getLogger(ControllerVentaLente.class.getName()).log(Level.SEVERE, null, ex);
            try {
                //En caso de error se indica un rollback a la transaccion. 
                conn.rollback();
                conn.setAutoCommit(true);
                r = false;

            } catch (SQLException ex1) {
                Logger.getLogger(ControllerVentaLente.class.getName()).log(Level.SEVERE, null, ex);
                r = false;
            }
        }
        return r;
    }

    public List<ExamenVista> getAllExamenVista(String filtro) throws Exception {

        String sql = "SELECT * FROM v_examenVista WHERE idCliente = " + filtro + ";";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<ExamenVista> examenVista = new ArrayList<>();

        while (rs.next()) {
            examenVista.add(fillExamenVista(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return examenVista;
    }
    
    private ExamenVista fillExamenVista(ResultSet rs) throws Exception {
        
        ExamenVista ev = new ExamenVista();
        Cliente c = new Cliente();
        Graduacion g = new Graduacion();

        ev.setIdExamenVista(rs.getInt("idExamenVista"));
        ev.setClave(rs.getString("clave"));

        c.setIdCliente(rs.getInt("idCliente"));
        ev.setCliente(c);

        g.setIdGraduacion(rs.getInt("idGraduacion"));
        ev.setGraduacion(g);

        return ev;
    }

    public List<TipoMica> getAllTipoMica() throws Exception {

        String sql = "SELECT * FROM v_tipoMica;";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<TipoMica> tiposMica = new ArrayList<>();

        while (rs.next()) {
            tiposMica.add(fillTipoMica(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return tiposMica;
    }

    private TipoMica fillTipoMica(ResultSet rs) throws Exception {
        
        TipoMica tm = new TipoMica();

        tm.setIdTipoMica(rs.getInt("idTipoMica"));
        tm.setNombre(rs.getString("nombre"));
        tm.setPrecioCompra(rs.getDouble("precioCompra"));
        tm.setPrecioVenta(rs.getDouble("precioVenta"));

        return tm;

    }
    
    public List<Material> getAllMaterial() throws Exception {

        String sql = "SELECT * FROM v_material;";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Material> materiales = new ArrayList<>();

        while (rs.next()) {
            materiales.add(fillMaterial(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return materiales;
    }

    private Material fillMaterial(ResultSet rs) throws Exception {
        
        Material m = new Material();

        m.setIdMaterial(rs.getInt("idMaterial"));
        m.setNombre(rs.getString("nombre"));
        m.setPrecioCompra(rs.getDouble("precioCompra"));
        m.setPrecioVenta(rs.getDouble("precioVenta"));

        return m;
    }
    
    public List<Armazon> getAllArmazon() throws Exception {

        String sql = "SELECT * FROM v_armazon;";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Armazon> armazones = new ArrayList<>();

        while (rs.next()) {
            armazones.add(fillArmazon(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return armazones;
    }

    private Armazon fillArmazon(ResultSet rs) throws Exception {
        
        Armazon a = new Armazon();
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setPrecioCompra(rs.getDouble("precioCompra"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));
        p.setExistencias(rs.getInt("existencias"));
        p.setEstatus(rs.getInt("estatus"));
        a.setProducto(p);
        
        a.setIdArmazon(rs.getInt("idArmazon"));
        a.setColor(rs.getString("color"));
        a.setModelo(rs.getString("modelo"));
        a.setDimensiones(rs.getString("dimensiones"));
        a.setDescripcion(rs.getString("descripcion"));

        return a;
    }
    
    public List<Tratamiento> getAllTratamiento() throws Exception {

        String sql = "SELECT * FROM v_tratamiento;";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Tratamiento> tratamientos = new ArrayList<>();

        while (rs.next()) {
            tratamientos.add(fillTratamiento(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return tratamientos;
    }

    private Tratamiento fillTratamiento(ResultSet rs) throws Exception {
        
        Tratamiento t = new Tratamiento();

        t.setIdTratamiento(rs.getInt("idTratamiento"));
        t.setNombre(rs.getString("nombre"));
        t.setPrecioCompra(rs.getDouble("precioCompra"));
        t.setPrecioVenta(rs.getDouble("precioVenta"));
        t.setEstatus(rs.getInt("estatus"));

        return t;
    }
}
