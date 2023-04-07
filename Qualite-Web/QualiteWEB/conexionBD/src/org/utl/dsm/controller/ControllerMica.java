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
import org.utl.dsm.model.TipoMica;

/**
 *
 * @author carlossanchez
 */

public class ControllerMica {
    
    public int insert(TipoMica m) throws Exception {

        String query = "{call insertarMica(?, ?, ?, ?)}";

        int idTipoMicaG = 0;

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, m.getNombre());
        cstmt.setDouble(2, m.getPrecioCompra());
        cstmt.setDouble(3, m.getPrecioVenta());

        cstmt.registerOutParameter(4, Types.INTEGER);

        cstmt.executeUpdate();

        idTipoMicaG = cstmt.getInt(4);

        m.setIdTipoMica(idTipoMicaG);

        cstmt.close();
        conexion.close();
        objConexion.close();

        return idTipoMicaG;
    }

    public void update(TipoMica m) throws Exception {

        String query = "{call actualizarMica(?, ?, ?, ?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, m.getNombre());
        cstmt.setDouble(2, m.getPrecioCompra());
        cstmt.setDouble(3, m.getPrecioVenta());
        cstmt.setInt(4, m.getIdTipoMica());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<TipoMica> getAll() throws Exception {

        String sql = "SELECT * FROM v_tipomica;";

        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        List<TipoMica> micas = new ArrayList<>();

        while (rs.next()) {
            micas.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return micas;
    }

    private TipoMica fill(ResultSet rs) throws Exception {
        
        TipoMica m = new TipoMica();

        m.setIdTipoMica(rs.getInt("idTipoMica"));
        m.setNombre(rs.getString("nombre"));
        m.setPrecioCompra(rs.getDouble("precioCompra"));
        m.setPrecioVenta(rs.getDouble("precioVenta"));

        return m;

    }
    
    public List<TipoMica> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_tipomica WHERE nombre LIKE '%" + busqueda + "%' OR precioCompra LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<TipoMica> micas = new ArrayList<>();
        
        while(rs.next()){
            micas.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return micas;
       
    }
}