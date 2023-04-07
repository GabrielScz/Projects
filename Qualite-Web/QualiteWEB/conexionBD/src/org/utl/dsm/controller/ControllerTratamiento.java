/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.bd.ConexionBD;
import org.utl.dsm.model.Tratamiento;

/**
 *
 * @author carlossanchez
 */

public class ControllerTratamiento {
    
        public int insert(Tratamiento t) throws Exception {

        String query = "{call insertarTratamiento(?, ?, ?, ?)}";

        int idTratamientoG = 0;

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();
        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, t.getNombre());
        cstmt.setDouble(2, t.getPrecioCompra());
        cstmt.setDouble(3, t.getPrecioVenta());

        cstmt.registerOutParameter(4, Types.INTEGER);

        cstmt.executeUpdate();
        
        idTratamientoG = cstmt.getInt(4);

        t.setIdTratamiento(idTratamientoG);

        cstmt.close();
        conexion.close();
        objConexion.close();

        return idTratamientoG;
    }

    public void update(Tratamiento t) throws Exception {

        String query = "{call actualizarTratamiento(?, ?, ?, ?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, t.getNombre());
        cstmt.setDouble(2, t.getPrecioCompra());
        cstmt.setDouble(3, t.getPrecioVenta());
        cstmt.setInt(4, t.getIdTratamiento());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void activate(Tratamiento t) throws Exception {

        String query = "{call activarTratamiento(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, t.getIdTratamiento());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public void delete(Tratamiento t) throws Exception {

        String query = "{call eliminarTratamiento(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, t.getIdTratamiento());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Tratamiento> getAll(String filtro) throws Exception {

        String sql = "SELECT * FROM v_tratamiento WHERE estatus = " + filtro + ";";

        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<Tratamiento> tratamientos = new ArrayList<>();

        while (rs.next()) {
            tratamientos.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return tratamientos;
    }

    private Tratamiento fill(ResultSet rs) throws Exception {
        Tratamiento t = new Tratamiento();

        t.setIdTratamiento(rs.getInt("idTratamiento"));
        t.setNombre(rs.getString("nombre"));
        t.setPrecioCompra(rs.getDouble("precioCompra"));
        t.setPrecioVenta(rs.getDouble("precioVenta"));
        t.setEstatus(rs.getInt("estatus"));

        return t;

    }
    
    public List<Tratamiento> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_tratamiento WHERE nombre LIKE '%" + busqueda + "%' OR precioCompra LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Tratamiento> tratamientos = new ArrayList<>();
        
        while(rs.next()){
            tratamientos.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return tratamientos;
       
    }    
}