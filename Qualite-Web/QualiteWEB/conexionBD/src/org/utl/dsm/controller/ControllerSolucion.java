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
import org.utl.dsm.model.Producto;
import org.utl.dsm.model.Solucion;

/**
 *
 * @author carlossanchez
 */
public class ControllerSolucion {
    
    public int insert(Solucion s) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call insertarSolucion(?, ?, ?, ?, ?, ?, ?, ?)}";

        //2. Preparar las variables para recibir los valores de retorno
        int idProductoG = 0;
        int idSolucionG = 0;
        String codigoBarrasG = "";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, s.getProducto().getNombre());
        cstmt.setString(2, s.getProducto().getMarca());
        cstmt.setDouble(3, s.getProducto().getPrecioCompra());
        cstmt.setDouble(4, s.getProducto().getPrecioVenta());
        cstmt.setInt(5, s.getProducto().getExistencias());

        cstmt.registerOutParameter(6, Types.INTEGER);
        cstmt.registerOutParameter(7, Types.INTEGER);
        cstmt.registerOutParameter(8, Types.VARCHAR);


        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //7. Recuperar los parametros de retorno
        idProductoG = cstmt.getInt(6);
        idSolucionG = cstmt.getInt(7);
        codigoBarrasG = cstmt.getString(8);


        //8. Colocar los varlores recuperados dentro del objeto
        s.getProducto().setIdProducto(idProductoG);
        s.getProducto().setCodigoBarras(codigoBarrasG);
        s.setIdSolucion(idSolucionG);
        


        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

        //10. Devolver el id que se genero
        return idSolucionG;
    }

    public void update(Solucion s) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call actualizarSolucion(?, ?, ?, ?, ?, ?, ?)}";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, s.getProducto().getNombre());
        cstmt.setString(2, s.getProducto().getMarca());
        cstmt.setString(3, s.getProducto().getCodigoBarras());
        cstmt.setDouble(4, s.getProducto().getPrecioCompra());
        cstmt.setDouble(5, s.getProducto().getPrecioVenta());
        cstmt.setInt(6, s.getProducto().getExistencias());
        cstmt.setInt(7, s.getProducto().getIdProducto());
        
        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void delete(Solucion s) throws Exception {

        String query = "{call eliminarSolucion(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, s.getProducto().getIdProducto());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void activate(Solucion s) throws Exception {

        String query = "{call activarSolucion(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, s.getProducto().getIdProducto());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Solucion> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM  v_solucion WHERE estatus = " + filtro + ";";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<Solucion> soluciones = new ArrayList<>();

        while (rs.next()) {
            soluciones.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return soluciones;
    }

    private Solucion fill(ResultSet rs) throws Exception {
        Solucion s = new Solucion();
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setMarca(rs.getString("marca"));
        p.setCodigoBarras(rs.getString("codigoBarras"));
        p.setPrecioCompra(rs.getDouble("precioCompra"));
        p.setPrecioVenta(rs.getDouble("precioVenta"));
        p.setExistencias(rs.getInt("existencias"));
        p.setEstatus(rs.getInt("estatus"));
        s.setProducto(p);

        s.setIdSolucion(rs.getInt("idSolucion"));

        return s;

    }
    
    public List<Solucion> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_solucion WHERE nombre LIKE '%" + busqueda + "%' OR marca LIKE '%" + busqueda + "%' OR precioCompra LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Solucion> soluciones = new ArrayList<>();
        
        while(rs.next()){
            soluciones.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return soluciones;
       
    }
}