package org.utl.dsm.proyectoqualite;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.LocalDateStringConverter;
import org.utl.dsm.model.Cliente;
import org.utl.dsm.model.Persona;
import org.utl.dsm.proyectoqualite.controller.ControllerCliente;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {

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
    private ComboBox<String> cbEstatus;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbEstado;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private ComboBox<String> cbTableEstatus;

    @FXML
    private TableColumn<Cliente, String> colApePaterno;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, Integer> colEstatus;

    @FXML
    private TableColumn<Cliente, String> colFechaN;

    @FXML
    private TableColumn<Cliente, String> colGenero;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colTelMovil;

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
    private TextField txtEmail;

    @FXML
    private TextField txtExtatus;

    @FXML
    private DatePicker txtFechaN;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtIdCliente;

    @FXML
    private TextField txtIdPersona;

    @FXML
    private TextField txtNoCliente;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelCasa;

    @FXML
    private TextField txtTelMovil;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    ObservableList<Cliente> listaClientes;
    ObservableList<String> listaGeneros;
    ObservableList<String> listaEstados;
    ObservableList<String> listaEstatus;

    ControllerCliente CC;
    Alert alert = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CC = new ControllerCliente();

        listaEstatus = FXCollections.observableArrayList();
        listaEstatus.addAll("Activo", "Inactivo");
        cbEstatus.setItems(listaEstatus);

        cbTableEstatus.setItems(listaEstatus);

        listaGeneros = FXCollections.observableArrayList();
        listaGeneros.addAll("Masculino", "Femenino");
        cbGenero.setItems(listaGeneros);

        listaEstados = FXCollections.observableArrayList();
        listaEstados.addAll("Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas",
                "Chihuahua", "Coahuila", "Colima", "Ciudad de Mexico", "Estado de Mexico", "Durango",
                "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit",
                "Nuevo Leon", "Oaxaca", "Puebla", "Querétaro", "Quinatana Roo", "San Luis Potosí",
                "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas");
        cbEstado.setItems(listaEstados);

        btnAdd.setTooltip(new Tooltip("Registrar Cliente"));
        txtFechaN.setConverter(new LocalDateStringConverter(formatter, null));
        colId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdCliente()));
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
        listaClientes = FXCollections.observableArrayList(CC.getAll("1"));
        tblClientes.setItems(listaClientes);
    }

    public void insert() throws Exception {
        try {
            Persona p = new Persona();
            Cliente c = new Cliente();

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

            if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
                c.setEstatus(1);
            } else if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
                c.setEstatus(0);
            }
            c.setPersona(p);

            CC.insert(c);
            alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Cliente agregado correctamente!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al llenar campos");
        }
        fillTable();
    }

    public void update() throws Exception {
        Persona p = new Persona();
        Cliente c = new Cliente();

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

        if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
            c.setEstatus(1);
        } else if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
            c.setEstatus(0);
        }
        c.setPersona(p);
        c.setEstatus(Integer.parseInt(cbEstatus.getValue()));
        c.setIdCliente(Integer.parseInt(txtIdCliente.getText()));

        CC.update(c);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Cliente editado correctamente!");
        alert.show();
        fillTable();
    }

    public void delete() throws Exception {
        Persona p = new Persona();
        Cliente c = new Cliente();

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

        c.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
        c.setPersona(p);

        CC.delete(c);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Cliente eliminado correctamente!");
        alert.show();
        fillTable();
    }

    public void activate() throws Exception {
        Persona p = new Persona();
        Cliente c = new Cliente();

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

        c.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
        c.setPersona(p);

        CC.activate(c);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Cliente activado correctamente!");
        alert.show();
        fillTable();
    }

    public void rowClicked() {
        Cliente clickedCliente = tblClientes.getSelectionModel().getSelectedItem();

        txtNoCliente.setText(String.valueOf(clickedCliente.getNumeroUnico()));
        txtNombre.setText(String.valueOf(clickedCliente.getPersona().getNombre()));
        txtApePaterno.setText(String.valueOf(clickedCliente.getPersona().getApellidoPaterno()));
        txtApeMaterno.setText(String.valueOf(clickedCliente.getPersona().getApellidoMaterno()));
        txtFechaN.setValue(LocalDate.parse(String.valueOf(clickedCliente.getPersona().getFechaNacimiento())));
        txtCalle.setText(String.valueOf(clickedCliente.getPersona().getCalle()));
        txtColonia.setText(String.valueOf(clickedCliente.getPersona().getColonia()));
        txtNumero.setText(String.valueOf(clickedCliente.getPersona().getnumero()));
        txtCodigoP.setText(String.valueOf(clickedCliente.getPersona().getCp()));
        txtCiudad.setText(String.valueOf(clickedCliente.getPersona().getCiudad()));
        txtTelCasa.setText(String.valueOf(clickedCliente.getPersona().getTelcasa()));
        txtTelMovil.setText(String.valueOf(clickedCliente.getPersona().getTelmovil()));
        txtEmail.setText(String.valueOf(clickedCliente.getPersona().getEmail()));
        cbEstado.setValue(String.valueOf(clickedCliente.getPersona().getEstado()));
        cbGenero.setValue(String.valueOf(clickedCliente.getPersona().getGenero()));
        cbEstatus.setValue(String.valueOf(clickedCliente.getEstatus()));
        txtIdPersona.setText(String.valueOf(clickedCliente.getPersona().getIdPersona()));
        txtIdCliente.setText(String.valueOf(clickedCliente.getIdCliente()));


        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(true);
    }

    public void search() throws Exception {
        String filtro = txtBuscar.getText();
        listaClientes= FXCollections.observableArrayList(CC.search(filtro));
        tblClientes.setItems(listaClientes);
    }

    public void viewTableEstatus() throws Exception {
        String filtro = null;

        if (cbTableEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
            filtro = "1";
        } else if (cbTableEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
            filtro = "0";
        }

        listaClientes = FXCollections.observableArrayList(CC.getAll(filtro));
        tblClientes.setItems(listaClientes);
    }

    public void clean(){
        txtIdCliente.setText("");
        txtNombre.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        txtTelMovil.setText("");
        txtTelCasa.setText("");
        txtEmail.setText("");
        txtNoCliente.setText("");
        txtCalle.setText("");
        txtColonia.setText("");
        txtNumero.setText("");
        txtCodigoP.setText("");
        txtCiudad.setText("");
        txtFechaN.setValue(LocalDate.ofEpochDay(01/12/2000));

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(false);
    }
}
