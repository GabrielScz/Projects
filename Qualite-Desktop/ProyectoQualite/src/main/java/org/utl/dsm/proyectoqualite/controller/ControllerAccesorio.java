package org.utl.dsm.proyectoqualite.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.utl.dsm.model.Accesorio;
import org.utl.dsm.model.Cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerAccesorio {
    Gson gson = null;
    HttpResponse<JsonNode> apiResponse = null;

    public int insert(Accesorio a) throws UnirestException {
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(a);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/accesorio/insert")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int update(Accesorio a){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(a);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/accesorio/update")
                    .field("datos", datos).asJson();
            System.out.println(apiResponse.getBody().toString());
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int delete(Accesorio a){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(a);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/accesorio/delete")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int activate(Accesorio a){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(a);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/accesorio/activate")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public List<Accesorio> getAll(String filtro) throws Exception{

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Accesorio> accesorios = new ArrayList<>();
            String datos = "";
            gson = new Gson();
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/accesorio/getAll")
                    .field("estatus", filtro).asJson();
            datos = apiResponse.getBody().toString();
            accesorios = Arrays.asList(mapper.readValue(datos, Accesorio[].class));
            accesorios.forEach(accesorio -> System.out.println(accesorio.getIdAccesorio()));
            return accesorios;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Accesorio> search(String filtro) throws Exception{

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Accesorio> accesorios = new ArrayList<>();
            String datos = "";
            gson = new Gson();
            apiResponse = Unirest.get("http://localhost:8080/ProyectoQualite/api/accesorio/buscar")
                    .queryString("busqueda", filtro).asJson();
            datos = apiResponse.getBody().toString();
            accesorios = Arrays.asList(mapper.readValue(datos, Accesorio[].class));
            accesorios.forEach(accesorio -> System.out.println(accesorio.getIdAccesorio()));
            return accesorios;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
