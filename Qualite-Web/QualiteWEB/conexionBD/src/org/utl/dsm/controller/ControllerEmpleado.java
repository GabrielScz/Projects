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
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Persona;
import org.utl.dsm.model.Usuario;

/**
 *
 * @author carlossanchez
 */
public class ControllerEmpleado {

    public int insert(Empleado e) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                + "?, ?, ?, ?,"
                + "?, ?, ?, ?)}";

        //2. Preparar las variables para recibir los valores de retorno
        int idPersonaG = 0;
        int idUsuarioG = 0;
        int idEmpleadoG = 0;
        String numUnicoG = "";
        String lastTokenG = "";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoPaterno());
        cstmt.setString(3, e.getPersona().getApellidoMaterno());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getFechaNacimiento());
        cstmt.setString(6, e.getPersona().getCalle());
        cstmt.setString(7, e.getPersona().getNumero());
        cstmt.setString(8, e.getPersona().getColonia());
        cstmt.setString(9, e.getPersona().getCp());
        cstmt.setString(10, e.getPersona().getCiudad());
        cstmt.setString(11, e.getPersona().getEstado());
        cstmt.setString(12, e.getPersona().getTelcasa());
        cstmt.setString(13, e.getPersona().getTelmovil());
        cstmt.setString(14, e.getPersona().getEmail());

        cstmt.setString(15, e.getUsuario().getNombreUsuario());
        cstmt.setString(16, e.getUsuario().getContrasenia());
        cstmt.setString(17, e.getUsuario().getRol());

        cstmt.registerOutParameter(18, Types.INTEGER);
        cstmt.registerOutParameter(19, Types.INTEGER);
        cstmt.registerOutParameter(20, Types.INTEGER);
        cstmt.registerOutParameter(21, Types.VARCHAR);
        cstmt.registerOutParameter(22, Types.VARCHAR);

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //7. Recuperar los parametros de retorno
        idPersonaG = cstmt.getInt(18);
        idUsuarioG = cstmt.getInt(19);
        idEmpleadoG = cstmt.getInt(20);
        numUnicoG = cstmt.getString(21);
        lastTokenG = cstmt.getString(22);

        //8. Colocar los varlores recuperados dentro del objeto
        e.getPersona().setIdPersona(idPersonaG);
        e.getUsuario().setIdUsuario(idUsuarioG);
        e.setIdEmpleado(idEmpleadoG);
        e.setNumeroUnico(numUnicoG);
        e.getUsuario().setLastToken(lastTokenG);

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

        //10. Devolver el id que se genero
        return idEmpleadoG;
    }

    public void update(Empleado e) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call actualizarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                + "?, ?, ?, ?, ?, ?)}";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, e.getPersona().getNombre());
        cstmt.setString(2, e.getPersona().getApellidoPaterno());
        cstmt.setString(3, e.getPersona().getApellidoMaterno());
        cstmt.setString(4, e.getPersona().getGenero());
        cstmt.setString(5, e.getPersona().getFechaNacimiento());
        cstmt.setString(6, e.getPersona().getCalle());
        cstmt.setString(7, e.getPersona().getNumero());
        cstmt.setString(8, e.getPersona().getColonia());
        cstmt.setString(9, e.getPersona().getCp());
        cstmt.setString(10, e.getPersona().getCiudad());
        cstmt.setString(11, e.getPersona().getEstado());
        cstmt.setString(12, e.getPersona().getTelcasa());
        cstmt.setString(13, e.getPersona().getTelmovil());
        cstmt.setString(14, e.getPersona().getEmail());
        cstmt.setInt(15, e.getPersona().getIdPersona());

        cstmt.setString(16, e.getUsuario().getNombreUsuario());
        cstmt.setString(17, e.getUsuario().getContrasenia());
        cstmt.setString(18, e.getUsuario().getRol());
        cstmt.setInt(19, e.getUsuario().getIdUsuario());

        cstmt.setInt(20, e.getEstatus());

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public void delete(Empleado e) throws Exception {

        String query = "{call eliminarEmpleado(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, e.getIdEmpleado());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public void activate(Empleado e) throws Exception {

        String query = "{call activarEmpleado(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, e.getIdEmpleado());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Empleado> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM  v_empleado WHERE estatus = " + filtro + ";";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<Empleado> empleados = new ArrayList<>();

        while (rs.next()) {
            empleados.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return empleados;
    }

    public List<Empleado> getUser() throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM  usuario;";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<Empleado> empleados = new ArrayList<>();

        while (rs.next()) {
            empleados.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return empleados;
    }

    private Empleado fill(ResultSet rs) throws Exception {
        Empleado e = new Empleado();
        Persona p = new Persona();

        p.setIdPersona(rs.getInt("idPersona"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidoPaterno(rs.getString("apellidoPaterno"));
        p.setApellidoMaterno(rs.getString("apellidoMaterno"));
        p.setGenero(rs.getString("genero"));
        p.setFechaNacimiento(rs.getString("fechaNacimiento"));
        p.setCalle(rs.getString("calle"));
        p.setColonia(rs.getString("colonia"));
        p.setNumero(rs.getString("numero"));
        p.setCp(rs.getString("cp"));
        p.setCiudad(rs.getString("ciudad"));
        p.setEstado(rs.getString("estado"));
        p.setTelcasa(rs.getString("telcasa"));
        p.setTelmovil(rs.getString("telmovil"));
        p.setEmail(rs.getString("email"));
        p.setIdPersona(rs.getInt("idPersona"));
        e.setPersona(p);

        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setEstatus(rs.getInt("estatus"));
        e.setNumeroUnico(rs.getString("numeroUnico"));

        e.setUsuario(new Usuario());
        e.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        e.getUsuario().setNombreUsuario(rs.getString("nombreUsuario"));
        e.getUsuario().setContrasenia(rs.getString("contrasenia"));
        e.getUsuario().setRol(rs.getString("rol"));
        e.getUsuario().setLastToken(rs.getString("lastToken"));
        e.getUsuario().setDateLastToken(rs.getString("dateLastToken"));

        return e;

    }

    public List<Empleado> search(String busqueda) throws Exception {

        String sql = "SELECT * FROM v_empleado WHERE nombre LIKE '%" + busqueda + "%' OR apellidoPaterno LIKE '%" + busqueda + "%' OR apellidoMaterno LIKE '%" + busqueda + "%' OR ciudad LIKE '%" + busqueda + "%' OR cp LIKE '%" + busqueda + "%' OR estado LIKE '%" + busqueda + "%';";

        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        List<Empleado> empleados = new ArrayList<>();

        while (rs.next()) {
            empleados.add(fill(rs));
        }

        rs.close();
        stmt.close();
        connMySQL.close();
        return empleados;

    }
}