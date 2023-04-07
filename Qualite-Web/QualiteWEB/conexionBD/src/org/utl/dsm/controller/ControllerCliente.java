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
import org.utl.dsm.model.Cliente;
import org.utl.dsm.model.Persona;

/**
 *
 * @author carlossanchez
 */
public class ControllerCliente {

    public int insert(Cliente c) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                + "?, ?, ?)}";

        //2. Preparar las variables para recibir los valores de retorno
        int idPersonaG = 0;
        int idClienteG = 0;
        String numUnicoG = "";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoPaterno());
        cstmt.setString(3, c.getPersona().getApellidoMaterno());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getFechaNacimiento());
        cstmt.setString(6, c.getPersona().getCalle());
        cstmt.setString(7, c.getPersona().getNumero());
        cstmt.setString(8, c.getPersona().getColonia());
        cstmt.setString(9, c.getPersona().getCp());
        cstmt.setString(10, c.getPersona().getCiudad());
        cstmt.setString(11, c.getPersona().getEstado());
        cstmt.setString(12, c.getPersona().getTelcasa());
        cstmt.setString(13, c.getPersona().getTelmovil());
        cstmt.setString(14, c.getPersona().getEmail());

        cstmt.registerOutParameter(15, Types.INTEGER);
        cstmt.registerOutParameter(16, Types.INTEGER);
        cstmt.registerOutParameter(17, Types.INTEGER);

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //7. Recuperar los parametros de retorno
        idPersonaG = cstmt.getInt(15);
        idClienteG = cstmt.getInt(16);
        numUnicoG = cstmt.getString(17);

        //8. Colocar los varlores recuperados dentro del objeto
        c.getPersona().setIdPersona(idPersonaG);
        c.setIdCliente(idClienteG);
        c.setNumeroUnico(numUnicoG);


        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

        //10. Devolver el id que se genero
        return idClienteG;
    }

    public void update(Cliente c) throws Exception {

        //1. Generar consulta que vamos a enviar a la BD
        String query = "{call actualizarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                + "?)}";

        //3. Conectarse a la BD
        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        //4. Generar el objeto que va a invocar el Store Procedure
        CallableStatement cstmt = conexion.prepareCall(query);

        //5. Asignar a cada uno los valores que se requieren
        cstmt.setString(1, c.getPersona().getNombre());
        cstmt.setString(2, c.getPersona().getApellidoPaterno());
        cstmt.setString(3, c.getPersona().getApellidoMaterno());
        cstmt.setString(4, c.getPersona().getGenero());
        cstmt.setString(5, c.getPersona().getFechaNacimiento());
        cstmt.setString(6, c.getPersona().getCalle());
        cstmt.setString(7, c.getPersona().getNumero());
        cstmt.setString(8, c.getPersona().getColonia());
        cstmt.setString(9, c.getPersona().getCp());
        cstmt.setString(10, c.getPersona().getCiudad());
        cstmt.setString(11, c.getPersona().getEstado());
        cstmt.setString(12, c.getPersona().getTelcasa());
        cstmt.setString(13, c.getPersona().getTelmovil());
        cstmt.setString(14, c.getPersona().getEmail());
        
        cstmt.setInt(15, c.getPersona().getIdPersona());

        //6. Ejecutar la instruccion
        cstmt.executeUpdate();

        //9. Cerrar los objetos de uso de BD
        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void delete(Cliente c) throws Exception {

        String query = "{call eliminarCliente(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, c.getIdCliente());

        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }
    
    public void activate(Cliente c) throws Exception {

        String query = "{call activarCliente(?)}";

        ConexionBD objConexion = new ConexionBD();
        Connection conexion = objConexion.open();

        CallableStatement cstmt = conexion.prepareCall(query);

        cstmt.setInt(1, c.getIdCliente());
        
        cstmt.executeUpdate();

        cstmt.close();
        conexion.close();
        objConexion.close();

    }

    public List<Cliente> getAll(String filtro) throws Exception {

        //1. La Consulta SQL que vamos a ejecutar
        String sql = "SELECT * FROM  v_cliente WHERE estatus = " + filtro + ";";

        //2. Con este objeto nos conectamos a la base de datos
        ConexionBD connMySQL = new ConexionBD();

        //3.Abrimos las conexion
        Connection conn = connMySQL.open();

        //4, Con este objeto ejecutaremos la consulta
        PreparedStatement pstmt = conn.prepareStatement(sql);

        //5. Aqui guardaremos los resultados de la cosulta
        ResultSet rs = pstmt.executeQuery();

        List<Cliente> clientes = new ArrayList<>();

        while (rs.next()) {
            clientes.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        connMySQL.close();
        return clientes;
    }

    private Cliente fill(ResultSet rs) throws Exception {
        Cliente c = new Cliente();
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
        c.setPersona(p);

        c.setIdCliente(rs.getInt("idCliente"));
        c.setEstatus(rs.getInt("estatus"));
        c.setNumeroUnico(rs.getString("numeroUnico"));

        return c;

    }
    
    public List<Cliente> search(String busqueda) throws Exception {
        
        String sql = "SELECT * FROM v_cliente WHERE nombre LIKE '%" + busqueda + "%' OR apellidoPaterno LIKE '%" + busqueda + "%' OR apellidoMaterno LIKE '%" + busqueda + "%' OR ciudad LIKE '%" + busqueda + "%' OR cp LIKE '%" + busqueda + "%' OR estado LIKE '%" + busqueda + "%';";
        
        ConexionBD connMySQL = new ConexionBD();

        Connection conn = connMySQL.open();
        
        Statement stmt = conn.createStatement();
        
        ResultSet rs =  stmt.executeQuery(sql);
        
        List<Cliente> clientes = new ArrayList<>();
        
        while(rs.next()){
            clientes.add(fill(rs));
        }
     
        rs.close();
        stmt.close();
        connMySQL.close();
        return clientes;
       
    }

}
