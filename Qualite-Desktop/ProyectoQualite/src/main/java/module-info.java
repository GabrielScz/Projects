module org.utl.dsm.proyectoqualite {
    requires javafx.controls;
    requires javafx.fxml;
    requires OpticaQualiteModel;
    requires gson;
    requires java.sql;
    requires unirest.java;
    requires javax.ws.rs.api;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires mysql.connector.java;



    opens org.utl.dsm.proyectoqualite to javafx.fxml;
    exports org.utl.dsm.proyectoqualite;

}