package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.controller.ControllerEmpleado;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Persona;
import org.utl.dsm.controller.ControllerLogin;

/**
 *
 * @author carlossanchez
 */
@Path("empleado")
public class EmpleadoREST extends Application {

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        Gson gson = new Gson();
        Empleado e = null;
        String out = null;
        ControllerLogin objCL = new ControllerLogin();
        ControllerEmpleado objCE = new ControllerEmpleado();
        
        try {
            if (objCL.validarToken(token)) {
                e = gson.fromJson(datos, Empleado.class);
                objCE.insert(e);
                out = gson.toJson(e);
            } else {
                out = """
                      {"error": "Credenciales Incorrectas"}
                      """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                     {"result": "error"}
                     """;
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("getAll")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@FormParam("estatus") @DefaultValue("1") String estatus) {

        String out = "";
        try {
            ControllerEmpleado objCE = new ControllerEmpleado();
            List<Empleado> empleados = objCE.getAll(estatus);
            Gson gs = new Gson();
            out = gs.toJson(empleados);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }

    @Path("getUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {

        String out = "";
        try {
            ControllerEmpleado objCE = new ControllerEmpleado();
            List<Empleado> empleados = objCE.getUser();
            Gson gs = new Gson();
            out = gs.toJson(empleados);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        Gson gson = new Gson();
        Empleado e = new Empleado();
        ControllerLogin objCL = new ControllerLogin();
        e = gson.fromJson(datos, Empleado.class);
        String out = "";
        ControllerEmpleado objCE = new ControllerEmpleado();
        
        try {
            if (objCL.validarToken(token)) {
                e = gson.fromJson(datos, Empleado.class);
                objCE.update(e);
                out = gson.toJson(e);
            } else {
                out = """
                      {"error": "Credenciales Incorrectas"}
                      """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                     {"result": "error"}
                     """;
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        Gson gson = new Gson();
        Empleado e = new Empleado();
        ControllerLogin objCL = new ControllerLogin();
        e = gson.fromJson(datos, Empleado.class);
        String out = "";
        ControllerEmpleado objCE = new ControllerEmpleado();
        
        try {
            if (objCL.validarToken(token)) {
                e = gson.fromJson(datos, Empleado.class);
                objCE.delete(e);
                out = gson.toJson(e);
            } else {
                out = """
                      {"error": "Credenciales Incorrectas"}
                      """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                     {"result": "error"}
                     """;
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("activate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response activate(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        Gson gson = new Gson();
        Empleado e = new Empleado();
        ControllerLogin objCL = new ControllerLogin();
        e = gson.fromJson(datos, Empleado.class);
        String out = "";
        ControllerEmpleado objCE = new ControllerEmpleado();
        
        try {
            if (objCL.validarToken(token)) {
                e = gson.fromJson(datos, Empleado.class);
                objCE.activate(e);
                out = gson.toJson(e);
            } else {
                out = """
                      {"error": "Credenciales Incorrectas"}
                      """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                     {"result": "error"}
                     """;
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("buscar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("busqueda") @DefaultValue("Car") String busqueda) {

        String out = "";
        try {
            ControllerEmpleado objCE = new ControllerEmpleado();
            List<Empleado> empleados = objCE.search(busqueda);
            Gson gs = new Gson();
            out = gs.toJson(empleados);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }
}
