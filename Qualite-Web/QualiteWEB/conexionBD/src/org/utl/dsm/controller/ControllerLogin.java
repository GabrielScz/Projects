/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

import java.sql.CallableStatement;
import org.utl.dsm.model.Persona;
import org.utl.dsm.model.Usuario;
import org.utl.dsm.model.Empleado;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.utl.dsm.bd.ConexionBD;

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
        Persona p = new Persona();

        p.setApellidoMaterno(rs.getString("apellidoMaterno"));
        p.setApellidoPaterno(rs.getString("apellidoPaterno"));
        p.setCalle(rs.getString("calle"));
        p.setCiudad(rs.getString("ciudad"));
        p.setColonia(rs.getString("colonia"));
        p.setCp(rs.getString("cp"));
        p.setEmail(rs.getString("email"));
        p.setEstado(rs.getString("estado"));
        p.setFechaNacimiento(rs.getString("fechaNacimiento"));
        p.setGenero(rs.getString("genero"));
        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setNumero(rs.getString("numero"));
        p.setTelcasa(rs.getString("telcasa"));
        p.setTelmovil(rs.getString("telmovil"));

        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroUnico(rs.getString("numeroUnico"));
        e.setUsuario(new Usuario());
        e.getUsuario().setContrasenia(rs.getString("contrasenia"));
        e.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        e.getUsuario().setNombreUsuario(rs.getString("nombreUsuario"));
        e.getUsuario().setRol(rs.getString("rol"));
        e.getUsuario().setLastToken(rs.getString("lastToken"));
        e.getUsuario().setDateLastToken(rs.getString("dateLastToken"));
        e.setNumeroUnico(rs.getString("numeroUnico"));

        e.setPersona(p);

        return e;
    }

    public void generarToken(int idUsuario, String token) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call generarTokens(?, ?)}";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setInt(1, idUsuario);
        cstmt.setString(2, token);

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public boolean eliminarToken(Empleado e) throws SQLException {
        boolean r = false;
        String query = "UPDATE usuario SET lastToken = '' WHERE idUsuario = ?";

        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();
        PreparedStatement preparedStatement = conn.prepareCall(query);
        
        preparedStatement.setInt(1, e.getUsuario().getIdUsuario());
        preparedStatement.execute();
        r = true;
        
        preparedStatement.close();
        conn.close();
        connMySQL.close();
        
        return r;

    }
    
    public boolean validarToken(String t) throws SQLException{
        boolean r = false;
        String query = "SELECT * FROM v_empleado WHERE lastToken = '" + t + "'";
        ConexionBD connMySQL = new ConexionBD();
        Connection conn = connMySQL.open();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if(rs.next())
            r = true;
        
        stmt.close();
        conn.close();
        connMySQL.close();
        
        return r;
    }

}
