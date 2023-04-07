/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.Controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.Model.Departamento;
import org.utl.dsm.Model.Empleado;
import org.utl.dsm.Model.Usuario;

/**
 *
 * @author carlossanchez
 */
public class ControllerEmpleado {
    
    public int insert(Empleado e) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        //2. Preparar las variables para recibir los valores de retorno
        int idUsuarioG = 0;
        int idEmpleadoG = 0;

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, e.getNombreEmpleado());
        cstmt.setString(2, e.getPrimerApellido());
        cstmt.setString(3, e.getSegundoApellido());
        cstmt.setString(4, e.getTelefono());
        cstmt.setString(5, e.getEmail());
        cstmt.setString(6, e.getRfc());
        cstmt.setString(7, e.getFotografia());
        
        cstmt.setString(8, e.getUsuario().getNombreUsuario());
        cstmt.setString(9, e.getUsuario().getContrasenia());
        cstmt.setString(10, e.getUsuario().getRol());
        
        cstmt.setInt(11, e.getDepartamento().getIdDepartamento());

        cstmt.registerOutParameter(12, Types.INTEGER);
        cstmt.registerOutParameter(13, Types.INTEGER);
        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //7. Recuperar los parametros de retorno
        idEmpleadoG = cstmt.getInt(12);
        idUsuarioG = cstmt.getInt(13);
        
        //8. Colocar los varlores recuperados dentro del objeto
        e.setIdEmpleado(idEmpleadoG);
        e.getUsuario().setIdUsuario(idUsuarioG);

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

        //10. Devolver el id que se genero
        return idEmpleadoG;
    }
    
    public List<Empleado> getAll() throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM v_empleado;";

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

        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNombreEmpleado(rs.getString("nombreEmpleado"));
        e.setPrimerApellido(rs.getString("primerApellido"));
        e.setSegundoApellido(rs.getString("segundoApellido"));
        e.setTelefono(rs.getString("telefono"));
        e.setEmail(rs.getString("email"));
        e.setRfc(rs.getString("rfc"));
        e.setFotografia(rs.getString("fotografia"));

        e.setUsuario(new Usuario());
        e.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        e.getUsuario().setNombreUsuario(rs.getString("nombreUsuario"));
        e.getUsuario().setContrasenia(rs.getString("contrasenia"));
        e.getUsuario().setRol(rs.getString("rol"));
        
        e.setDepartamento(new Departamento());
        e.getDepartamento().setIdDepartamento(rs.getInt("idDepartamento"));
        e.getDepartamento().setNombreDepartamento(rs.getString("nombreDepartamento"));
        e.getDepartamento().setSucursal(rs.getString("sucursal"));

        return e;

    }
    
}
