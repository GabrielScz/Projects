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
import org.utl.dsm.model.Material;

/**
 *
 * @author carlossanchez
 */

public class ControllerMaterial {
    
     public int insert(Material m) throws Exception {

        String query = "{call insertarMaterial(?, ?, ?, ?)}";

        int idMaterialG = 0;
        
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, m.getNombre());
        cstmt.setDouble(2, m.getPrecioCompra());
        cstmt.setDouble(3, m.getPrecioVenta());

        cstmt.registerOutParameter(4, Types.INTEGER);

        cstmt.executeUpdate();

        idMaterialG = cstmt.getInt(4);

        m.setIdMaterial(idMaterialG);

        cstmt.close();
        conexion.close();
        objConexion.close();

        return idMaterialG;
    }

    public void update(Material m) throws Exception {

        String query = "{call actualizarMaterial(?, ?, ?, ?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, m.getNombre());
        cstmt.setDouble(2, m.getPrecioCompra());
        cstmt.setDouble(3, m.getPrecioVenta());
        cstmt.setInt(4, m.getIdMaterial());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Material> getAll() throws Exception {

        String sql = "SELECT * FROM v_material;";

        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<Material> materiales = new ArrayList<>();

        while (rs.next()) {
            materiales.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return materiales;
    }

    private Material fill(ResultSet rs) throws Exception {
        
        Material m = new Material();

        m.setIdMaterial(rs.getInt("idMaterial"));
        m.setNombre(rs.getString("nombre"));
        m.setPrecioCompra(rs.getDouble("precioCompra"));
        m.setPrecioVenta(rs.getDouble("precioVenta"));

        return m;

    }
    
    public List<Material> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_material WHERE nombre LIKE '%" + busqueda + "%' OR precioCompra LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Material> materiales = new ArrayList<>();
        
        while(rs.next()){
            materiales.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return materiales;
       
    }
}