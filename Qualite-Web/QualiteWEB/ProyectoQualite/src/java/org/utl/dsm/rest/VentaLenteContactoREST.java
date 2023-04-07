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
import org.utl.dsm.model.DetalleVentaPresupuesto;
import org.utl.dsm.model.ExamenVista;
import org.utl.dsm.model.LenteContacto;

@Path("ventaLenteContacto")
public class VentaLenteContactoREST extends Application {
    
    @Path("guardar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarVentaLC(@FormParam("datos") @DefaultValue("") String datos){
        DetalleVentaPresupuesto dvp = new DetalleVentaPresupuesto();
        ControllerVentaLenteContacto objCVLC = new ControllerVentaLenteContacto();
        String out = "";
        Gson gson = new Gson();
        
        dvp = gson.fromJson(datos, DetalleVentaPresupuesto.class);
        boolean r = objCVLC.generarLenteContaco(dvp);
        System.out.println(dvp.getVenta());
        System.out.println(dvp.getListaVentaPresupuesto());
        if(r){
            out = """
                  {"result":"correcto"}
                  """;
        } else{
            out = """
                  {"error":"error"}
                  """;
        }
        
        return Response.status(Response.Status.OK).entity(out).build(); 
    }
    
    @Path("getAll")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@FormParam("idCliente") @DefaultValue("1") String idCliente,
            @FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLenteContacto obCVL = new ControllerVentaLenteContacto();
                    List<ExamenVista> examenVista = obCVL.getAll(idCliente);
                    Gson gs = new Gson();
                    out = gs.toJson(examenVista);
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
    
    @Path("getAllLC")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLC(@FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLenteContacto obCVL = new ControllerVentaLenteContacto();
                    List<LenteContacto> lentesContacto = obCVL.getAllLC();
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
}