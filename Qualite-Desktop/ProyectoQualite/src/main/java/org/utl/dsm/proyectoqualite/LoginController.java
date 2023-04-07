package org.utl.dsm.proyectoqualite;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    Alert alert = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnLogin.setOnAction(event -> {
            try {
                validarLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnExit.setOnAction(event -> {
            System.exit(0);
        });

    }

    public void validarLogin() throws IOException {

        String user = txtUser.getText();
        String password = txtPassword.getText();
        HttpResponse<JsonNode> apiResponse = null;
        Alert alert = null;
        if(user.equals("admin") & password.equals("1234")){
            cargarMenu();
            alert = new Alert(Alert.AlertType.INFORMATION, "¡Bienvenido: " + user + "!");
            alert.show();
        }else {
            alert = new Alert(Alert.AlertType.ERROR,"Error");
            alert.show();
        }
    }

    public void cargarMenu() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(this.getClass().getResource("menuPrincipal.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MenuPrincipal");
        stage.show();
        stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();

    }


}
