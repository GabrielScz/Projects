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
import java.sql.SQLException;
import java.util.List;
import org.utl.dsm.controller.ControllerLogin;
import org.utl.dsm.controller.ControllerVenta;
import org.utl.dsm.model.DetalleVentaProducto;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Producto;

/**
 *
 * @author carlossanchez
 */
@Path("venta")
public class VentaREST {

    @Path("getAll")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@FormParam("estatus") @DefaultValue("1") String estatus,
            @FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token))
                try {
                ControllerVenta objCV = new ControllerVenta();
                List<Producto> productos = objCV.getAll(estatus);
                Gson gs = new Gson();
                out = gs.toJson(productos);
            } catch (Exception ex) {
                System.out.println(ex.toString());
                out = "{\"error\":\"" + ex.toString() + "\"}";
            }
        } catch (SQLException ex) {
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
            ControllerVenta objCV = new ControllerVenta();
            List<Producto> productos = objCV.search(busqueda);
            Gson gs = new Gson();
            out = gs.toJson(productos);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }

    @Path("guardar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datos") @DefaultValue("") String datos) {

        boolean r = false;
        Gson gson = new Gson();
        DetalleVentaProducto dvp = new DetalleVentaProducto();
        ControllerVenta objCV = new ControllerVenta();
        dvp = gson.fromJson(datos, DetalleVentaProducto.class);
        r = objCV.generarVenta(dvp);
        String out = "";

        if (r) {
            out = """
                  {"result":"correcto"}
                  """;
        } else {
            out = """
                  {"error":"ps error"}
                  """;
        }

        return Response.status(Response.Status.OK).entity(out).build();

    }

}
