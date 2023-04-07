/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.utl.dsm.bd.ConexionBD;
import org.utl.dsm.model.Accesorio;
import org.utl.dsm.model.DetalleVentaProducto;
import org.utl.dsm.model.Producto;

/**
 *
 * @author carlossanchez
 */
public class ControllerVenta {
    
    public List<Producto> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM v_producto WHERE estatus = "  + filtro + ";";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<Producto> productos = new ArrayList<>();

        while (rs.next()) {
            productos.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return productos;
    }
    
    private Producto fill(ResultSet rs) throws Exception {
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setExistencias(rs.getInt("existencias"));
        p.setEstatus(rs.getInt("estatus"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));

        return p;

    }
    
    public List<Producto> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_producto WHERE nombre LIKE '%" + busqueda + "%' OR marca LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Producto> productos = new ArrayList<>();
        
        while(rs.next()){
            productos.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return productos;
       
    }
    
    public boolean generarVenta(DetalleVentaProducto dvp) {
        ConexionBD connMYSQL = new ConexionBD();
        Connection conn = connMYSQL.open();
        Statement stm = null;
        ResultSet rs = null;
        boolean r = false;

        try {
            conn.setAutoCommit(false);
            String query1 = "INSERT INTO venta(clave, idEmpleado) VALUES (" + dvp.getVenta().getClave() + "," + dvp.getVenta().getEmpleado().getIdEmpleado() + ");";
            stm = conn.createStatement();
            stm.execute(query1);

            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stm.executeQuery(query2);

            if (rs.next()) {
                dvp.getVenta().setIdVenta(rs.getInt(1));
            }

            for (int i = 0; i < dvp.getListaVentaProducto().size(); i++) {
                String query3 = "INSERT INTO venta_producto VALUES(" + dvp.getVenta().getIdVenta() + "," + dvp.getListaVentaProducto().get(i).getProducto().getIdProducto() + "," + dvp.getListaVentaProducto().get(i).getCantidad() + "," + dvp.getListaVentaProducto().get(i).getPrecioUnitario() + "," + dvp.getListaVentaProducto().get(i).getDescuento() + ");";
                stm.execute(query3);
            }
            
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stm.close();
            conn.close();
            r = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                rs.close();
                stm.close();
                conn.close();
                r = false;
            } catch  (SQLException ex1){
                Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex1);
                r = false;
            }
        }

        return r;
    }
    
}
