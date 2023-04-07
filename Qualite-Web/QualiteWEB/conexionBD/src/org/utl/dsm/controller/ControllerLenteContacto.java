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
import org.utl.dsm.model.LenteContacto;
import org.utl.dsm.model.Producto;

/**
 *
 * @author carlossanchez
 */
public class ControllerLenteContacto {
    
    public int insert(LenteContacto lc) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call insertarLenteContacto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        //2. Preparar las variables para recibir los valores de retorno
        int idProductoG = 0;
        int idLenteContactoG = 0;
        String codigoBarrasG = "";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, lc.getProducto().getNombre());
        cstmt.setString(2, lc.getProducto().getMarca());
        cstmt.setDouble(3, lc.getProducto().getPrecioCompra());
        cstmt.setDouble(4, lc.getProducto().getPrecioVenta());
        cstmt.setInt(5, lc.getProducto().getExistencias());
        cstmt.setInt(6, lc.getKeratometria());
        cstmt.setString(7, lc.getFotografia());

        cstmt.registerOutParameter(8, Types.INTEGER);
        cstmt.registerOutParameter(9, Types.INTEGER);
        cstmt.registerOutParameter(10, Types.VARCHAR);

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //7. Recuperar los parametros de retorno
        idProductoG = cstmt.getInt(8);
        idLenteContactoG = cstmt.getInt(9);
        codigoBarrasG = cstmt.getString(10);

        //8. Colocar los varlores recuperados dentro del objeto
        lc.getProducto().setIdProducto(idProductoG);
        lc.getProducto().setCodigoBarras(codigoBarrasG);
        lc.setIdLenteContacto(idLenteContactoG);
        
        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

        //10. Devolver el id que se genero
        return idLenteContactoG;
    }

    public void update(LenteContacto lc) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call actualizarLenteContacto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, lc.getProducto().getNombre());
        cstmt.setString(2, lc.getProducto().getMarca());
        cstmt.setString(3, lc.getProducto().getCodigoBarras());
        cstmt.setDouble(4, lc.getProducto().getPrecioCompra());
        cstmt.setDouble(5, lc.getProducto().getPrecioVenta());
        cstmt.setInt(6, lc.getProducto().getExistencias());
        cstmt.setInt(7, lc.getKeratometria());
        cstmt.setString(8, lc.getFotografia());
        cstmt.setInt(9, lc.getProducto().getIdProducto());
        cstmt.setInt(10, lc.getIdLenteContacto());

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void activate(LenteContacto lc) throws Exception {

        String query = "{call activarLenteContacto(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, lc.getProducto().getIdProducto());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void delete(LenteContacto lc) throws Exception {

        String query = "{call eliminarLenteContacto(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, lc.getProducto().getIdProducto());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    
      public List<LenteContacto> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM v_lenteContacto WHERE estatus = " + filtro + ";";

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
            lentesContacto.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return lentesContacto;
    }
    
    private LenteContacto fill(ResultSet rs) throws Exception {
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
    
    public List<LenteContacto> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_lenteContacto WHERE nombre LIKE '%" + busqueda + "%' OR marca LIKE '%" + busqueda + "%' OR precioCompra LIKE '%" + busqueda + "%' OR precioVenta LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<LenteContacto> lentesContacto = new ArrayList<>();
        
        while(rs.next()){
            lentesContacto.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return lentesContacto;
       
    }
    
}
