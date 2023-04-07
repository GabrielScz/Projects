package org.utl.dsm.rest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.utl.dsm.controller.ControllerLogin;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Application;

@Path("log")
public class LoginREST extends Application {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("in")

    public Response login(@FormParam("datos") @DefaultValue("") String datos) {

        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(datos, Usuario.class);
        String out = null;
        ControllerLogin objCL = new ControllerLogin();
        Empleado e = null;

        try {
            e = objCL.login(usuario.getNombreUsuario(), usuario.getContrasenia());
            if (e != null) {
                e.getUsuario().setLastToken();
                out = new Gson().toJson(e);
                objCL.generarToken(e.getUsuario().getIdUsuario(), e.getUsuario().getLastToken());

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("out")
    public Response logout(@FormParam("empleado") @DefaultValue("") String e) {

        String out = null;
        Empleado empleado = null;
        ControllerLogin objCL = new ControllerLogin();
        Gson gson = new Gson();

        try {
            empleado = gson.fromJson(e, Empleado.class);
            objCL = new ControllerLogin();

            if (objCL.eliminarToken(empleado)) {
                out = """
                      {"ok":"Eliminacion de Token correcta"}
                      """;
            } else {
                out = """
                      {"error":"Eliminacion de Token incorrecta"}
                      """;
            }
        } catch (JsonParseException jpe) {
            out = """
                  {"error":"Formato de datos no valido"}
                  """;
            jpe.printStackTrace();

        } catch (Exception ex) {
            out = """
                  {"error":"Error en las credenciales"}
                  """;
            ex.printStackTrace();
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
