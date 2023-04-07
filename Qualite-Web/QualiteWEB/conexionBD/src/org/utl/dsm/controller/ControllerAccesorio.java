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
import java.sql.Types;
import org.utl.dsm.bd.ConexionBD;
import org.utl.dsm.model.Accesorio;
import org.utl.dsm.model.Producto;

/**
 *
 * @author carlossanchez
 */
public class ControllerAccesorio {
    
    public int insert(Accesorio a) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call insertarAccesorio(?, ?, ?, ?, ?, ?,?, ?)}";

        //2. Preparar las variables para recibir los valores de retorno
        int idProductoG = 0;
        int idAccesorioG = 0;
        String codigoBarrasG = "";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getMarca());
        cstmt.setDouble(3, a.getProducto().getPrecioCompra());
        cstmt.setDouble(4, a.getProducto().getPrecioVenta());
        cstmt.setInt(5, a.getProducto().getExistencias());

        cstmt.registerOutParameter(6, Types.INTEGER);
        cstmt.registerOutParameter(7, Types.INTEGER);
        cstmt.registerOutParameter(8, Types.VARCHAR);


        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //7. Recuperar los parametros de retorno
        idProductoG = cstmt.getInt(6);
        idAccesorioG = cstmt.getInt(7);
        codigoBarrasG = cstmt.getString(8);


        //8. Colocar los varlores recuperados dentro del objeto
        a.getProducto().setIdProducto(idProductoG);
        a.getProducto().setCodigoBarras(codigoBarrasG);
        a.setIdAccesorio(idAccesorioG);
        


        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

        //10. Devolver el id que se genero
        return idAccesorioG;
    }

    public void update(Accesorio a) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call actualizarAccesorio(?, ?, ?, ?, ?, ?, ?)}";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, a.getProducto().getNombre());
        cstmt.setString(2, a.getProducto().getMarca());
        cstmt.setString(3, a.getProducto().getCodigoBarras());
        cstmt.setDouble(4, a.getProducto().getPrecioCompra());
        cstmt.setDouble(5, a.getProducto().getPrecioVenta());
        cstmt.setInt(6, a.getProducto().getExistencias());
        cstmt.setInt(7, a.getProducto().getIdProducto());

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void delete(Accesorio a) throws Exception {

        String query = "{call eliminarAccesorio(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, a.getProducto().getIdProducto());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void activate(Accesorio a) throws Exception {

        String query = "{call activarAccesorio(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, a.getProducto().getIdProducto());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Accesorio> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM  v_accesorio WHERE estatus = " + filtro + ";";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<Accesorio> accesorios = new ArrayList<>();

        while (rs.next()) {
            accesorios.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return accesorios;
    }

    private Accesorio fill(ResultSet rs) throws Exception {
        Accesorio a = new Accesorio();
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

        a.setIdAccesorio(rs.getInt("idAccesorio"));

        return a;

    }
    
    public List<Accesorio> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_accesorio WHERE nombre LIKE '%" + busqueda + "%' OR marca LIKE '%" + busqueda + "%' OR precioCompra LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Accesorio> accesorios = new ArrayList<>();
        
        while(rs.next()){
            accesorios.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return accesorios;
       
    }
}