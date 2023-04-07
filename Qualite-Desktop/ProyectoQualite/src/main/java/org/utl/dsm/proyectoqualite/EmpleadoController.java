package org.utl.dsm.proyectoqualite;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.LocalDateStringConverter;
import org.utl.dsm.model.Empleado;
import org.utl.dsm.model.Usuario;
import org.utl.dsm.model.Persona;
import org.utl.dsm.proyectoqualite.controller.ControllerEmpleado;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EmpleadoController implements Initializable {

    @FXML
    private Button btnActivate;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnClean;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private ComboBox<String> cbEstatus;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbRol;

    @FXML
    private ComboBox<String> cbTableEstatus;

    @FXML
    private TableColumn<Empleado, String> colApePaterno;

    @FXML
    private TableColumn<Empleado, String> colEmail;

    @FXML
    private TableColumn<Empleado, Integer> colEstatus;

    @FXML
    private TableColumn<Empleado, String> colFechaN;

    @FXML
    private TableColumn<Empleado, String> colGenero;

    @FXML
    private TableColumn<Empleado, Integer> colId;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colTelMovil;

    @FXML
    private TableView<Empleado> tblEmpleados;

    @FXML
    private TextField txtApeMaterno;

    @FXML
    private TextField txtApePaterno;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtCodigoP;

    @FXML
    private TextField txtColonia;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtFechaN;

    @FXML
    private TextField txtIdEmpleado;

    @FXML
    private TextField txtIdPersona;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    private TextField txtNoEmpleado;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtTelCasa;

    @FXML
    private TextField txtTelMovil;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    ObservableList<Empleado> listaEmpleados;
    ObservableList<String> listaGeneros;
    ObservableList<String> listaEstados;
    ObservableList<String> listaEstatus;
    ObservableList<String> listaRoles;

    ControllerEmpleado CE;

    Alert alert = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CE = new ControllerEmpleado();

        listaEstatus = FXCollections.observableArrayList();
        listaEstatus.addAll("Activo", "Inactivo");
        cbEstatus.setItems(listaEstatus);

        cbTableEstatus.setItems(listaEstatus);

        listaEstados = FXCollections.observableArrayList();
        listaEstados.addAll("Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas",
                "Chihuahua", "Coahuila", "Colima", "Ciudad de Mexico", "Estado de Mexico", "Durango",
                "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit",
                "Nuevo Leon", "Oaxaca", "Puebla", "Querétaro", "Quinatana Roo", "San Luis Potosí",
                "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas");
        cbEstado.setItems(listaEstados);

        listaRoles = FXCollections.observableArrayList();
        listaRoles.addAll("Administrador", "Empleado");
        cbRol.setItems(listaRoles);

        listaGeneros = FXCollections.observableArrayList();
        listaGeneros.addAll("Masculino", "Femenino");
        cbGenero.setItems(listaGeneros);


        btnAdd.setTooltip(new Tooltip("Registrar Empleado"));
        txtFechaN.setConverter(new LocalDateStringConverter(formatter, null));
        colId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdEmpleado()));
        colNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getNombre()));
        colApePaterno.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getApellidoPaterno()));
        colGenero.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getGenero()));
        colEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEstatus()));
        colTelMovil.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getTelmovil()));
        colEmail.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getEmail()));
        colFechaN.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getFechaNacimiento()));
        btnAdd.setOnAction(event -> {
            try {
                insert();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void fillTable() throws Exception {
        listaEmpleados = FXCollections.observableArrayList(CE.getAll("1"));
        tblEmpleados.setItems(listaEmpleados);
    }

    public void insert() throws Exception {
        try {
            Persona p = new Persona();
            Usuario u = new Usuario();
            Empleado e = new Empleado();

            p.setNombre(txtNombre.getText());
            p.setApellidoPaterno(txtApePaterno.getText());
            p.setApellidoMaterno(txtApeMaterno.getText());
            p.setFechaNacimiento(txtFechaN.getValue().format(formatter).toString());
            p.setCalle(txtCalle.getText());
            p.setColonia(txtColonia.getText());
            p.setnumero(txtNumero.getText());
            p.setCp(txtCodigoP.getText());
            p.setCiudad(txtCiudad.getText());
            p.setTelcasa(txtTelCasa.getText());
            p.setTelmovil(txtTelMovil.getText());
            p.setEmail(txtEmail.getText());
            p.setEstado(cbEstado.getSelectionModel().getSelectedItem().toString());
            if (cbGenero.getSelectionModel().getSelectedItem().toString().equals("Masculino")) {
                p.setGenero("M");
            } else if (cbGenero.getSelectionModel().getSelectedItem().toString().equals("Femenino")) {
                p.setGenero("F");
            }

            u.setNombreUsuario(txtNombreUsuario.getText());
            u.setContrasenia(txtContrasenia.getText());
            u.setRol(cbRol.getSelectionModel().getSelectedItem().toString());

            e.setUsuario(u);
            e.setPersona(p);

            CE.insert(e);
            alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Empleado agregado correctamente!");
            alert.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al llenar campos");
        }
        fillTable();
    }

    public void update() throws Exception {

        Persona p = new Persona();
        Usuario u = new Usuario();
        Empleado e = new Empleado();

        p.setNombre(txtNombre.getText());
        p.setApellidoPaterno(txtApePaterno.getText());
        p.setApellidoMaterno(txtApeMaterno.getText());
        p.setFechaNacimiento(txtFechaN.getValue().format(formatter).toString());
        p.setCalle(txtCalle.getText());
        p.setColonia(txtColonia.getText());
        p.setnumero(txtNumero.getText());
        p.setCp(txtCodigoP.getText());
        p.setCiudad(txtCiudad.getText());
        p.setTelcasa(txtTelCasa.getText());
        p.setTelmovil(txtTelMovil.getText());
        p.setEmail(txtEmail.getText());
        p.setEstado(cbEstado.getSelectionModel().getSelectedItem().toString());
        p.setGenero(cbGenero.getSelectionModel().getSelectedItem().toString());
        p.setIdPersona(Integer.parseInt(txtIdPersona.getText()));

        u.setNombreUsuario(txtNombreUsuario.getText());
        u.setContrasenia(txtContrasenia.getText());
        u.setRol(cbRol.getSelectionModel().getSelectedItem().toString());

        if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
            e.setEstatus(1);
        } else if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
            e.setEstatus(0);
        }
        e.setIdEmpleado(Integer.parseInt(txtIdEmpleado.getText()));
        e.setUsuario(u);
        e.setPersona(p);

        CE.update(e);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Empleado editado correctamente!");
        alert.show();
        fillTable();
    }

    public void delete() throws Exception {
        Persona p = new Persona();
        Usuario u = new Usuario();
        Empleado e = new Empleado();

        p.setNombre(txtNombre.getText());
        p.setApellidoPaterno(txtApePaterno.getText());
        p.setApellidoMaterno(txtApeMaterno.getText());
        p.setFechaNacimiento(txtFechaN.getValue().format(formatter).toString());
        p.setCalle(txtCalle.getText());
        p.setColonia(txtColonia.getText());
        p.setnumero(txtNumero.getText());
        p.setCp(txtCodigoP.getText());
        p.setCiudad(txtCiudad.getText());
        p.setTelcasa(txtTelCasa.getText());
        p.setTelmovil(txtTelMovil.getText());
        p.setEmail(txtEmail.getText());
        p.setEstado(cbEstado.getSelectionModel().getSelectedItem().toString());
        p.setGenero(cbGenero.getSelectionModel().getSelectedItem().toString());
        p.setIdPersona(Integer.parseInt(txtIdPersona.getText()));

        u.setNombreUsuario(txtNombreUsuario.getText());
        u.setContrasenia(txtContrasenia.getText());
        u.setRol(cbRol.getSelectionModel().getSelectedItem().toString());

        e.setIdEmpleado(Integer.parseInt(txtIdEmpleado.getText()));
        e.setUsuario(u);
        e.setPersona(p);

        CE.delete(e);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Empleado eliminado correctamente!");
        alert.show();
        fillTable();
    }

    public void activate() throws Exception {
        Persona p = new Persona();
        Usuario u = new Usuario();
        Empleado e = new Empleado();

        p.setNombre(txtNombre.getText());
        p.setApellidoPaterno(txtApePaterno.getText());
        p.setApellidoMaterno(txtApeMaterno.getText());
        p.setFechaNacimiento(txtFechaN.getValue().format(formatter).toString());
        p.setCalle(txtCalle.getText());
        p.setColonia(txtColonia.getText());
        p.setnumero(txtNumero.getText());
        p.setCp(txtCodigoP.getText());
        p.setCiudad(txtCiudad.getText());
        p.setTelcasa(txtTelCasa.getText());
        p.setTelmovil(txtTelMovil.getText());
        p.setEmail(txtEmail.getText());
        p.setEstado(cbEstado.getSelectionModel().getSelectedItem().toString());
        p.setGenero(cbGenero.getSelectionModel().getSelectedItem().toString());
        p.setIdPersona(Integer.parseInt(txtIdPersona.getText()));

        u.setNombreUsuario(txtNombreUsuario.getText());
        u.setContrasenia(txtContrasenia.getText());
        u.setRol(cbRol.getSelectionModel().getSelectedItem().toString());

        e.setIdEmpleado(Integer.parseInt(txtIdEmpleado.getText()));
        e.setUsuario(u);
        e.setPersona(p);

        CE.activate(e);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Empleado activado correctamente!");
        alert.show();
        fillTable();
    }

    public void rowClicked() {
        Empleado clickedEmpleado = tblEmpleados.getSelectionModel().getSelectedItem();

        txtNoEmpleado.setText(String.valueOf(clickedEmpleado.getNumeroUnico()));
        txtNombre.setText(String.valueOf(clickedEmpleado.getPersona().getNombre()));
        txtApePaterno.setText(String.valueOf(clickedEmpleado.getPersona().getApellidoPaterno()));
        txtApeMaterno.setText(String.valueOf(clickedEmpleado.getPersona().getApellidoMaterno()));
        txtFechaN.setValue(LocalDate.parse(String.valueOf(clickedEmpleado.getPersona().getFechaNacimiento())));
        txtCalle.setText(String.valueOf(clickedEmpleado.getPersona().getCalle()));
        txtColonia.setText(String.valueOf(clickedEmpleado.getPersona().getColonia()));
        txtNumero.setText(String.valueOf(clickedEmpleado.getPersona().getnumero()));
        txtCodigoP.setText(String.valueOf(clickedEmpleado.getPersona().getCp()));
        txtCiudad.setText(String.valueOf(clickedEmpleado.getPersona().getCiudad()));
        txtTelCasa.setText(String.valueOf(clickedEmpleado.getPersona().getTelcasa()));
        txtTelMovil.setText(String.valueOf(clickedEmpleado.getPersona().getTelmovil()));
        txtEmail.setText(String.valueOf(clickedEmpleado.getPersona().getEmail()));
        txtNombreUsuario.setText(String.valueOf(clickedEmpleado.getUsuario().getNombreUsuario()));
        txtContrasenia.setText(String.valueOf(clickedEmpleado.getUsuario().getContrasenia()));
        cbEstado.setValue(String.valueOf(clickedEmpleado.getPersona().getEstado()));
        cbGenero.setValue(String.valueOf(clickedEmpleado.getPersona().getGenero()));
        cbEstatus.setValue(String.valueOf(clickedEmpleado.getEstatus()));
        cbRol.setValue(String.valueOf(clickedEmpleado.getUsuario().getRol()));
        txtIdPersona.setText(String.valueOf(clickedEmpleado.getPersona().getIdPersona()));
        txtIdEmpleado.setText(String.valueOf(clickedEmpleado.getIdEmpleado()));
        txtIdUsuario.setText(String.valueOf(clickedEmpleado.getUsuario().getIdUsuario()));


        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(true);
    }

    public void search() throws Exception {
        String filtro = txtBuscar.getText();
        listaEmpleados= FXCollections.observableArrayList(CE.search(filtro));
        tblEmpleados.setItems(listaEmpleados);
    }

    public void viewTableEstatus() throws Exception {
        String filtro = null;

        if (cbTableEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
            filtro = "1";
        } else if (cbTableEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
            filtro = "0";
        }

        listaEmpleados = FXCollections.observableArrayList(CE.getAll(filtro));
        tblEmpleados.setItems(listaEmpleados);
    }

    public void clean(){
        txtIdEmpleado.setText("");
        txtNombre.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        txtTelMovil.setText("");
        txtTelCasa.setText("");
        txtEmail.setText("");
        txtNoEmpleado.setText("");
        txtCalle.setText("");
        txtColonia.setText("");
        txtNumero.setText("");
        txtCodigoP.setText("");
        txtCiudad.setText("");
        txtNombreUsuario.setText("");
        txtContrasenia.setText("");
        txtFechaN.setValue(LocalDate.ofEpochDay(01/12/2000));
        txtIdUsuario.setText("");
        txtIdPersona.setText("");

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(false);
    }
}
