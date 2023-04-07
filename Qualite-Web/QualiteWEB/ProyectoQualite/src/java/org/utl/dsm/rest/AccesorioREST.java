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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.controller.ControllerAccesorio;
import org.utl.dsm.controller.ControllerLogin;
import org.utl.dsm.model.Accesorio;

/**
 *
 * @author carlossanchez
 */

@Path("accesorio")
public class AccesorioREST {

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        Gson gson = new Gson();
        Accesorio a = null;
        String out = null;
        ControllerAccesorio objCA = new ControllerAccesorio();
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                a = gson.fromJson(datos, Accesorio.class);
                objCA.insert(a);
                out = gson.toJson(a);
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
                    ControllerAccesorio objCA = new ControllerAccesorio();
                    List<Accesorio> accesorios = objCA.getAll(estatus);
                    Gson gs = new Gson();
                    out = gs.toJson(accesorios);
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

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("datos") @DefaultValue("") String datos,
            @FormParam("token") @DefaultValue("") String token) {

        Gson gson = new Gson();
        Accesorio a = null;
        String out = null;
        ControllerAccesorio objCA = new ControllerAccesorio();
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                a = gson.fromJson(datos, Accesorio.class);
                objCA.update(a);
                out = gson.toJson(a);
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
        Accesorio a = null;
        String out = null;
        ControllerAccesorio objCA = new ControllerAccesorio();
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                a = gson.fromJson(datos, Accesorio.class);
                objCA.delete(a);
                out = gson.toJson(a);
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
        Accesorio a = null;
        String out = null;
        ControllerAccesorio objCA = new ControllerAccesorio();
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                a = gson.fromJson(datos, Accesorio.class);
                objCA.activate(a);
                out = gson.toJson(a);
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
            ControllerAccesorio objCA = new ControllerAccesorio();
            List<Accesorio> accesorios = objCA.search(busqueda);
            Gson gs = new Gson();
            out = gs.toJson(accesorios);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }
}