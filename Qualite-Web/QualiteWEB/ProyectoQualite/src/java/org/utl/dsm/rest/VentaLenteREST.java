package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.utl.dsm.controller.ControllerLogin;
import org.utl.dsm.controller.ControllerVentaLente;
import org.utl.dsm.model.Armazon;
import org.utl.dsm.model.DetalleVentaPresupuestoLentes;
import org.utl.dsm.model.ExamenVista;
import org.utl.dsm.model.Material;
import org.utl.dsm.model.TipoMica;
import org.utl.dsm.model.Tratamiento;

@Path("ventaLente")
public class VentaLenteREST {
    
    @Path("guardar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarVentaLente(@FormParam("datos") @DefaultValue("") String datos){
        DetalleVentaPresupuestoLentes dvp = new DetalleVentaPresupuestoLentes();
        ControllerVentaLente objCVLC = new ControllerVentaLente();
        String out = "";
        Gson gson = new Gson();
        System.out.println(datos);
        dvp = gson.fromJson(datos, DetalleVentaPresupuestoLentes.class);
        boolean r = objCVLC.generarVentaLentes(dvp);
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
    
    @Path("getAllExamenVista")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllExamenVista(@FormParam("idCliente") @DefaultValue("1") String idCliente,
            @FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLente obCVL = new  ControllerVentaLente();
                    List<ExamenVista> examenVista = obCVL.getAllExamenVista(idCliente);
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
    
    @Path("getAllTipoMica")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTipoMica(@FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLente obCVL = new ControllerVentaLente();
                    List<TipoMica> tiposMica = obCVL.getAllTipoMica();
                    Gson gs = new Gson();
                    out = gs.toJson(tiposMica);
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
    
    @Path("getAllArmazon")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArmazon(@FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLente obCVL = new ControllerVentaLente();
                    List<Armazon> armazones = obCVL.getAllArmazon();
                    Gson gs = new Gson();
                    out = gs.toJson(armazones);
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
    
    @Path("getAllMaterial")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMaterial(@FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLente obCVL = new ControllerVentaLente();
                    List<Material> materiales = obCVL.getAllMaterial();
                    Gson gs = new Gson();
                    out = gs.toJson(materiales);
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
    
    @Path("getAllTratamiento")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTratamiento(@FormParam("token") @DefaultValue("") String token) {

        String out = "";
        ControllerLogin objCL = new ControllerLogin();

        try {
            if (objCL.validarToken(token)) {
                try {
                    ControllerVentaLente obCVL = new ControllerVentaLente();
                    List<Tratamiento> tratamientos = obCVL.getAllTratamiento();
                    Gson gs = new Gson();
                    out = gs.toJson(tratamientos);
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