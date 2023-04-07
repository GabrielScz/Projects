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
import org.utl.dsm.controller.ControllerLenteContacto;
import org.utl.dsm.controller.ControllerLogin;
import org.utl.dsm.controller.ControllerVentaLenteContacto;
import org.utl.dsm.model.LenteContacto;

/**
 *
 * @author carlossanchez
 */

@Path("lenteContacto")
public class LenteContactoREST extends Application {
    
    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        String out = null;
        Gson gson = new Gson();
        LenteContacto lc = null;
        ControllerLogin objCL = new ControllerLogin();
        ControllerLenteContacto obCLC = new ControllerLenteContacto();

        try {
            if (objCL.validarToken(token)) {
                lc = gson.fromJson(datos, LenteContacto.class);
                obCLC.insert(lc);
                out = gson.toJson(lc);
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
    
    
    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        String out = null;
        Gson gson = new Gson();
        LenteContacto lc = null;
        ControllerLogin objCL = new ControllerLogin();
        ControllerLenteContacto obCLC = new ControllerLenteContacto();

        try {
            if (objCL.validarToken(token)) {
                lc = gson.fromJson(datos, LenteContacto.class);
                obCLC.update(lc);
                out = gson.toJson(lc);
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

        String out = null;
        Gson gson = new Gson();
        LenteContacto lc = null;
        ControllerLogin objCL = new ControllerLogin();
        ControllerLenteContacto obCLC = new ControllerLenteContacto();
        
        try {
            if (objCL.validarToken(token)) {
                lc = gson.fromJson(datos, LenteContacto.class);
                obCLC.activate(lc);
                out = gson.toJson(lc);
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

        String out = null;
        Gson gson = new Gson();
        LenteContacto lc = null;
        ControllerLogin objCL = new ControllerLogin();
        ControllerLenteContacto obCLC = new ControllerLenteContacto();

        try {
            if (objCL.validarToken(token)) {
                lc = gson.fromJson(datos, LenteContacto.class);
                obCLC.delete(lc);
                out = gson.toJson(lc);
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
    public Response getAll(@FormParam("estatus") @DefaultValue("1") String estatus,
            @FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerLenteContacto obCLC = new ControllerLenteContacto();
                    List<LenteContacto> lentesContacto = obCLC.getAll(estatus);
                    Gson gs = new Gson();
                    out = gs.toJson(lentesContacto);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    out = "{\"error\":\"" + ex.toString() + "\"}";
                }
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
            ControllerLenteContacto obCLC = new ControllerLenteContacto();
            List<LenteContacto> lentesContacto = obCLC.search(busqueda);
            Gson gs = new Gson();
            out = gs.toJson(lentesContacto);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }
}
