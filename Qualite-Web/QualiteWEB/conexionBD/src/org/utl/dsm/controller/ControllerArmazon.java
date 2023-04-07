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
import org.utl.dsm.model.Armazon;
import org.utl.dsm.model.Producto;

/**
 *
 * @author carlossanchez
 */
public class ControllerArmazon {
    public int insert(Armazon a) throws Exception {

        String query = "{call insertarArmazon(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        int idProductoG = 0;
        int idArmazonG = 0;
        String codigoBarrasG = "";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();
     
        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getMarca());
        cstmt.setString(3, a.getModelo());
        cstmt.setString(4, a.getColor());
        cstmt.setString(5, a.getDimensiones());
        cstmt.setString(6, a.getDescripcion());
        cstmt.setDouble(7, a.getProducto().getPrecioCompra());
        cstmt.setDouble(8, a.getProducto().getPrecioVenta());
        cstmt.setInt(9, a.getProducto().getExistencias());
        
        cstmt.registerOutParameter(10, Types.INTEGER);
        cstmt.registerOutParameter(11, Types.INTEGER);
        cstmt.registerOutParameter(12, Types.VARCHAR);

        cstmt.executeUpdate();

        idProductoG = cstmt.getInt(10);
        idArmazonG = cstmt.getInt(11);
        codigoBarrasG = cstmt.getString(12);

        a.getProducto().setIdProducto(idProductoG);
        a.getProducto().setCodigoBarras(codigoBarrasG);
        a.setIdArmazon(idArmazonG);
        
        cstmt.close();
        conexion.close();
        objConexion.close();

        return idArmazonG;
    }

    public void update(Armazon a) throws Exception {

        String query = "{call actualizarArmazon(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getMarca());
        cstmt.setString(3, a.getProducto().getCodigoBarras());
        cstmt.setString(4, a.getModelo());
        cstmt.setString(5, a.getColor());
        cstmt.setString(6, a.getDimensiones());
        cstmt.setString(7, a.getDescripcion());
        cstmt.setDouble(8, a.getProducto().getPrecioCompra());
        cstmt.setDouble(9, a.getProducto().getPrecioVenta());
        cstmt.setInt(10, a.getProducto().getExistencias());
        cstmt.setInt(11, a.getProducto().getIdProducto());
        cstmt.setInt(12, a.getIdArmazon());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void activate(Armazon a) throws Exception {

        String query = "{call activarArmazon(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, a.getProducto().getIdProducto());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void delete(Armazon a) throws Exception {

        String query = "{call eliminarArmazon(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, a.getProducto().getIdProducto());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Armazon> getAll(String filtro) throws Exception {

        String sql = "SELECT * FROM  v_armazon WHERE estatus = " + filtro + ";";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Armazon> armazones = new ArrayList<>();

        while (rs.next()) {
            armazones.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return armazones;
    }

    private Armazon fill(ResultSet rs) throws Exception {
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
        a.setModelo(rs.getString("modelo"));
        a.setColor(rs.getString("color"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setDimensiones(rs.getString("dimensiones"));

        return a;

    }
    
    public List<Armazon> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_armazon WHERE nombre LIKE '%" + busqueda + "%' OR marca LIKE '%" + busqueda + "%' OR modelo LIKE '%" + busqueda + "%' OR color LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Armazon> armazones = new ArrayList<>();
        
        while(rs.next()){
            armazones.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return armazones;
       
    }   
}