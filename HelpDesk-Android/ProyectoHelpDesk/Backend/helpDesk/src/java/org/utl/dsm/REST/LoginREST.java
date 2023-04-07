/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.REST;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.utl.dsm.Controller.ControllerEmpleado;
import org.utl.dsm.Controller.ControllerLogin;
import org.utl.dsm.Model.Empleado;
import org.utl.dsm.Model.Usuario;

/**
 *
 * @author carlossanchez
 */

@Path("log")
public class LoginREST {
    
    @Path("in")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response login(@FormParam("usuario") @DefaultValue("") String usuario,
                            @FormParam("contrasenia") @DefaultValue("") String contrasenia) {

        Gson gson = new Gson();
        String out = null;
        ControllerLogin objCL = new ControllerLogin();
        Empleado e = null;

        try {
            e = objCL.login(usuario, contrasenia);
            if (e != null) {
                out = new Gson().toJson(e);
                objCL.generarUltimaConexion(e.getUsuario().getIdUsuario());

            } else {
                out = """
                  {"error" :"Usuario y/o contrasena incorrectas!"}
                  """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"exception" : "%s"}
                  """;
            out = String.format(out, ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
}
