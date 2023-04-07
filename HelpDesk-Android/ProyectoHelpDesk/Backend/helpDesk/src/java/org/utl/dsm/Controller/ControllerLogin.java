/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.Controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.utl.dsm.Model.Departamento;
import org.utl.dsm.Model.Empleado;
import org.utl.dsm.Model.Usuario;

/**
 *
 * @author carlossanchez
 */
public class ControllerLogin {
    
    public Empleado login(String usuario, String contrasenia) throws Exception {

        String sql = "SELECT * FROM v_empleado VE WHERE VE.nombreUsuario = ? AND VE.contrasenia = ?";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = null;

        pstmt.setString(1, usuario);
        pstmt.setString(2, contrasenia);

        rs = pstmt.executeQuery();
        Empleado e = null;

        if (rs.next()) {
            e = fill(rs);
        }

        rs.close();
        pstmt.close();
        connMySQL.close();

        return e;
    }
    
     private Empleado fill(ResultSet rs) throws Exception {
        Empleado e = new Empleado();
        Usuario u = new Usuario();
        Departamento d = new Departamento();

        e.setNombreEmpleado(rs.getString("nombreEmpleado"));
        e.setPrimerApellido(rs.getString("primerApellido"));
        e.setSegundoApellido(rs.getString("segundoApellido"));
        e.setTelefono(rs.getString("telefono"));
        e.setEmail(rs.getString("email"));
        e.setRfc(rs.getString("rfc"));
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        
        u.setNombreUsuario(rs.getString("nombreUsuario"));
        u.setContrasenia(rs.getString("contrasenia"));
        u.setRol(rs.getString("rol"));
        u.setUltimaConexion(rs.getString("ultimaConexion"));
        u.setIdUsuario(rs.getInt("idUsuario"));

        e.setUsuario(u);
        
        d.setNombreDepartamento("nombreDepartamaento");
        d.setSucursal("sucursal");
        d.setIdDepartamento(rs.getInt("idDepartamento"));
        
        e.setDepartamento(d);

        return e;
    }
     
     public void generarUltimaConexion(int idUsuario) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "call generarUltimaConexion(?)";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setInt(1, idUsuario);

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
}
