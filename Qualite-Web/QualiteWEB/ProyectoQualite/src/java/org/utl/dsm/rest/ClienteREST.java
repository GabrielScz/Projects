/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.utl.dsm.controller.ControllerCliente;
import org.utl.dsm.controller.ControllerLogin;
import org.utl.dsm.model.Cliente;
import org.utl.dsm.model.Persona;

/**
 *
 * @author carlossanchez
 */

@Path("cliente")
public class ClienteREST extends Application{
    
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token){
        
        Gson gson = new Gson();
        Cliente c = null;
        String out = null;
        ControllerCliente objCC = new ControllerCliente();
        ControllerLogin objCL = new ControllerLogin();
        
        try {
            if (objCL.validarToken(token)) {
                c = gson.fromJson(datos, Cliente.class);
                objCC.insert(c);
                out = gson.toJson(c);
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
    public Response getAll(@FormParam("estatus") @DefaultValue("1") String estatus){
        
        String out = "";
        try {
            ControllerCliente objCC = new ControllerCliente();
            List<Cliente> clientes = objCC.getAll(estatus);
            Gson gs = new Gson();
            out = gs.toJson(clientes);
                    } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\""+ex.toString()+"\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
        
        
    }
    
    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token){
        
        Gson gson = new Gson();
        Cliente c = null;
        String out = null;
        ControllerCliente objCC = new ControllerCliente();
        ControllerLogin objCL = new ControllerLogin();
        
        try {
            if (objCL.validarToken(token)) {
                c = gson.fromJson(datos, Cliente.class);
                objCC.update(c);
                out = gson.toJson(c);
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
            @FormParam("token") @DefaultValue("") String token){
        
        Gson gson = new Gson();
        Cliente c = null;
        String out = null;
        ControllerCliente objCC = new ControllerCliente();
        ControllerLogin objCL = new ControllerLogin();
        
        try {
            if (objCL.validarToken(token)) {
                c = gson.fromJson(datos, Cliente.class);
                objCC.delete(c);
                out = gson.toJson(c);
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
            @FormParam("token") @DefaultValue("") String token){
        
        Gson gson = new Gson();
        Cliente c = null;
        String out = null;
        ControllerCliente objCC = new ControllerCliente();
        ControllerLogin objCL = new ControllerLogin();
        
        try {
            if (objCL.validarToken(token)) {
                c = gson.fromJson(datos, Cliente.class);
                objCC.activate(c);
                out = gson.toJson(c);
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
    public Response search(@QueryParam("busqueda") @DefaultValue("Car") String busqueda){
        
        String out = "";
        try {
            ControllerCliente objCC = new ControllerCliente();
            List<Cliente> clientes = objCC.search(busqueda);
            Gson gs = new Gson();
            out = gs.toJson(clientes);
                    } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\""+ex.toString()+"\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
        
        
    }
    
}
