package org.utl.dsm.proyectoqualite.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.utl.dsm.model.Cliente;
import org.utl.dsm.model.Empleado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerEmpleado {
    Gson gson = null;
    HttpResponse<JsonNode> apiResponse = null;

    public int insert(Empleado e) throws UnirestException {
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(e);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/juegos/insert")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int update(Empleado e){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(e);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/juegos/update")
                    .field("datos", datos).asJson();
            System.out.println(apiResponse.getBody().toString());
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int delete(Empleado e){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(e);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/juegos/delete")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int activate(Empleado e){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(e);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/juegos/activate")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public List<Empleado> getAll(String filtro) throws Exception{

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Empleado> empleados = new ArrayList<>();
            String datos = "";
            gson = new Gson();
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/juegos/getAll")
                    .field("estatus", filtro).asJson();
            datos = apiResponse.getBody().toString();
            empleados = Arrays.asList(mapper.readValue(datos, Empleado[].class));
            empleados.forEach(empleado -> System.out.println(empleado.getIdEmpleado()));
            return empleados;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Empleado> search(String filtro) throws Exception{

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Empleado> empleados = new ArrayList<>();
            String datos = "";
            gson = new Gson();
            apiResponse = Unirest.get("http://localhost:8080/ProyectoQualite/api/juegos/buscar")
                    .queryString("busqueda", filtro).asJson();
            datos = apiResponse.getBody().toString();
            empleados = Arrays.asList(mapper.readValue(datos, Empleado[].class));
            empleados.forEach(empleado -> System.out.println(empleado.getIdEmpleado()));
            return empleados;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
