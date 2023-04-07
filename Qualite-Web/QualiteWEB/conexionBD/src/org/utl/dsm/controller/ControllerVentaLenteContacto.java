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
import org.utl.dsm.model.Cliente;
import org.utl.dsm.model.DetalleVentaPresupuesto;
import org.utl.dsm.model.ExamenVista;
import org.utl.dsm.model.Graduacion;
import org.utl.dsm.model.LenteContacto;
import org.utl.dsm.model.Persona;
import org.utl.dsm.model.Producto;

/**
 *
 * @author carlossanchez
 */
public class ControllerVentaLenteContacto {

    public boolean generarLenteContaco(DetalleVentaPresupuesto dvp) {
        boolean r = false;
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();
        Statement stmnt = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            stmnt = conn.createStatement();
            
            String query1 = "INSERT INTO venta (idEmpleado, clave) "
                    + "VALUES (" + dvp.getVenta().getEmpleado().getIdEmpleado()
                    + ',' + dvp.getVenta().getClave() + ");";
            stmnt.execute(query1);
            
            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmnt.executeQuery(query2);
            if (rs.next()) {
                dvp.getVenta().setIdVenta(rs.getInt(1));
            }
            for (int i = 0; i < dvp.getListaVentaPresupuesto().size(); i++) {
                String query3 = "INSERT INTO presupuesto"
                        + "(idExamenVista, clave)"
                        + "VALUES (" + dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getExamenVista().getIdExamenVista()
                        + "," + dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getPresupuesto().getClave() + " );";
                stmnt.execute(query3);
                rs = stmnt.executeQuery(query2);
                if (rs.next()) {
                    dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getPresupuesto().setIdPresupuesto(rs.getInt(1));
                }
                
                String query4 = "INSERT INTO presupuesto_lentescontacto"
                        + "(idExamenVista, idLenteContacto, clave)"
                        + "VALUES (" + dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getExamenVista().getIdExamenVista() + ","
                        + dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getLenteContacto().getIdLenteContacto() + ","
                        + dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getClave() + ");";
                stmnt.execute(query4);
                
                rs = stmnt.executeQuery(query2);
                if (rs.next()) {
                    dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().setIdPresupuestoLentesContacto(rs.getInt(1));
                }
                String query5 = "INSERT INTO venta_presupuesto "
                        + "(idVenta, idPresupuesto, cantidad, precioUnitario, descuento) "
                        + "VALUES ("
                        + dvp.getVenta().getIdVenta() + ","
                        + dvp.getListaVentaPresupuesto().get(i).getPresupuestoLentesContacto().getPresupuesto().getIdPresupuesto() + ","
                        + dvp.getListaVentaPresupuesto().get(i).getCantidad() + ","
                        + dvp.getListaVentaPresupuesto().get(i).getPrecioUnitario() + ","
                        + dvp.getListaVentaPresupuesto().get(i).getDescuento() + ");";
                stmnt.execute(query5);
            }
            conn.commit();
            conn.setAutoCommit(true);
            stmnt.close();
            conn.close();
            r = true;
        } catch (SQLException ex) {

            Logger.getLogger(ControllerVentaLenteContacto.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
                r = false;
            } catch (SQLException ex1) {
                r = false;
            }
        }
        return r;
    }

    public List<ExamenVista> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM v_examenVista WHERE idCliente = " + filtro + ";";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<ExamenVista> examenVista = new ArrayList<>();

        while (rs.next()) {
            examenVista.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return examenVista;
    }

    public List<LenteContacto> getAllLC() throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM v_lenteContacto;";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<LenteContacto> lentesContacto = new ArrayList<>();

        while (rs.next()) {
            lentesContacto.add(fillLC(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return lentesContacto;
    }

    private LenteContacto fillLC(ResultSet rs) throws Exception {
        Producto p = new Producto();
        LenteContacto lc = new LenteContacto();

        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));
        p.setPrecioCompra(rs.getDouble("precioCompra"));
        p.setExistencias(rs.getInt("existencias"));
        p.setEstatus(rs.getInt("estatus"));
        lc.setProducto(p);

        lc.setIdLenteContacto(rs.getInt("idLenteContacto"));
        lc.setKeratometria(rs.getInt("keratometria"));
        lc.setFotografia(rs.getString("fotografia"));

        return lc;

    }

    private ExamenVista fill(ResultSet rs) throws Exception {
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
}
