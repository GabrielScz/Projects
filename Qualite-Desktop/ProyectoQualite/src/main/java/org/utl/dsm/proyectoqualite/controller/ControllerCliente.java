package org.utl.dsm.proyectoqualite.controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.utl.dsm.model.Cliente;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerCliente {

    Gson gson = null;
    HttpResponse<JsonNode> apiResponse = null;

    public int insert(Cliente c) throws UnirestException {
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(c);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/cliente/insert")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(Cliente c){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(c);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/cliente/update")
                    .field("datos", datos).asJson();
            System.out.println(apiResponse.getBody().toString());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(Cliente c){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(c);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/cliente/delete")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int activate(Cliente c){
        try {
            String datos = "";
            gson = new Gson();
            datos = gson.toJson(c);
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/cliente/activate")
                    .field("datos", datos).asJson();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Cliente> getAll(String filtro) throws Exception{

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Cliente> clientes = new ArrayList<>();
            String datos = "";
            gson = new Gson();
            apiResponse = Unirest.post("http://localhost:8080/ProyectoQualite/api/cliente/getAll")
                    .field("estatus", filtro).asJson();
            datos = apiResponse.getBody().toString();
            clientes = Arrays.asList(mapper.readValue(datos, Cliente[].class));
            clientes.forEach(cliente -> System.out.println(cliente.getIdCliente()));
            return clientes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> search(String filtro) throws Exception{

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Cliente> clientes = new ArrayList<>();
            String datos = "";
            gson = new Gson();
            apiResponse = Unirest.get("http://localhost:8080/ProyectoQualite/api/cliente/buscar")
                    .queryString("busqueda", filtro).asJson();
            datos = apiResponse.getBody().toString();
            clientes = Arrays.asList(mapper.readValue(datos, Cliente[].class));
            clientes.forEach(cliente -> System.out.println(cliente.getIdCliente()));
            return clientes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
