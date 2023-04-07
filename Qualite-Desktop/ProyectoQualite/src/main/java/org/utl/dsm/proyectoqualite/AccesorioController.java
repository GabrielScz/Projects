package org.utl.dsm.proyectoqualite;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.utl.dsm.model.*;
import org.utl.dsm.proyectoqualite.controller.ControllerAccesorio;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AccesorioController implements Initializable {

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
    private ComboBox<String> cbTableEstatus;

    @FXML
    private TableColumn<Accesorio, String> colCodigoB;

    @FXML
    private TableColumn<Accesorio, Integer> colEstatus;

    @FXML
    private TableColumn<Accesorio, Integer> colExistencias;

    @FXML
    private TableColumn<Accesorio, Integer> colId;

    @FXML
    private TableColumn<Accesorio, String> colMarca;

    @FXML
    private TableColumn<Accesorio, String> colNombre;

    @FXML
    private TableColumn<Accesorio, Double> colPrcCompra;

    @FXML
    private TableColumn<Accesorio, Double> colPrcVenta;

    @FXML
    private TableView<Accesorio> tblAccesorios;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCodigoB;

    @FXML
    private TextField txtExistencias;

    @FXML
    private TextField txtIdAccesorio;

    @FXML
    private TextField txtIdProducto;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrcCompra;

    @FXML
    private TextField txtPrcVenta;

    ObservableList<Accesorio> listaAccesorios;
    ObservableList<String> listaEstatus;
    ControllerAccesorio CA;
    Alert alert = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CA = new ControllerAccesorio();

        listaEstatus = FXCollections.observableArrayList();
        listaEstatus.addAll("Activo", "Inactivo");
        cbEstatus.setItems(listaEstatus);

        cbTableEstatus.setItems(listaEstatus);

        colId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdAccesorio()));
        colNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getNombre()));
        colMarca.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getMarca()));
        colPrcCompra.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getPrecioCompra()));
        colPrcVenta.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getPrecioVenta()));
        colExistencias.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getExistencias()));
        colCodigoB.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getCodigoBarras()));
        colEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProducto().getEstatus()));

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
        listaAccesorios = FXCollections.observableArrayList(CA.getAll("1"));
        tblAccesorios.setItems(listaAccesorios);
    }

    public void insert() throws Exception {
        try {
            Producto p = new Producto();
            Accesorio a = new Accesorio();

            p.setNombre(txtNombre.getText());
            p.setMarca(txtMarca.getText());
            p.setPrecioCompra(Double.parseDouble(txtPrcCompra.getText()));
            p.setPrecioVenta(Double.parseDouble(txtPrcVenta.getText()));
            p.setExistencias(Integer.parseInt(txtExistencias.getText()));
            p.setCodigoBarras(txtCodigoB.getText());
            if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
                p.setEstatus(1);
            } else if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
                p.setEstatus(0);
            }

            a.setProducto(p);

            CA.insert(a);
            alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Accesorio agregado correctamente!");
            alert.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error al llenar campos");
        }
        fillTable();
    }

    public void update() throws Exception {
        Producto p = new Producto();
        Accesorio a = new Accesorio();

        p.setNombre(txtNombre.getText());
        p.setMarca(txtMarca.getText());
        p.setPrecioCompra(Double.parseDouble(txtPrcCompra.getText()));
        p.setPrecioVenta(Double.parseDouble(txtPrcVenta.getText()));
        p.setExistencias(Integer.parseInt(txtExistencias.getText()));
        p.setCodigoBarras(txtCodigoB.getText());
        p.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
        if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
            p.setEstatus(1);
        } else if (cbEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
            p.setEstatus(0);
        }

        a.setIdAccesorio(Integer.parseInt(txtIdAccesorio.getText()));
        a.setProducto(p);

        CA.update(a);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Accesorio editado correctamente!");
        alert.show();
        fillTable();
    }

    public void delete() throws Exception {
        Producto p = new Producto();
        Accesorio a = new Accesorio();

        p.setNombre(txtNombre.getText());
        p.setMarca(txtMarca.getText());
        p.setPrecioCompra(Double.parseDouble(txtPrcCompra.getText()));
        p.setPrecioVenta(Double.parseDouble(txtPrcVenta.getText()));
        p.setExistencias(Integer.parseInt(txtExistencias.getText()));
        p.setCodigoBarras(txtCodigoB.getText());
        p.setIdProducto(Integer.parseInt(txtIdProducto.getText()));

        a.setIdAccesorio(Integer.parseInt(txtIdAccesorio.getText()));
        a.setProducto(p);

        CA.delete(a);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Accesorio eliminado correctamente!");
        alert.show();
        fillTable();
    }

    public void activate() throws Exception {
        Producto p = new Producto();
        Accesorio a = new Accesorio();

        p.setNombre(txtNombre.getText());
        p.setMarca(txtMarca.getText());
        p.setPrecioCompra(Double.parseDouble(txtPrcCompra.getText()));
        p.setPrecioVenta(Double.parseDouble(txtPrcVenta.getText()));
        p.setExistencias(Integer.parseInt(txtExistencias.getText()));
        p.setCodigoBarras(txtCodigoB.getText());
        p.setIdProducto(Integer.parseInt(txtIdProducto.getText()));

        a.setIdAccesorio(Integer.parseInt(txtIdAccesorio.getText()));
        a.setProducto(p);

        CA.activate(a);
        alert = new Alert(Alert.AlertType.CONFIRMATION, "¡Accesorio activado correctamente!");
        alert.show();
        fillTable();
    }

    public void rowClicked() {
        Accesorio clickedAccesorio = tblAccesorios.getSelectionModel().getSelectedItem();

        txtNombre.setText(String.valueOf(clickedAccesorio.getProducto().getNombre()));
        txtMarca.setText(String.valueOf(clickedAccesorio.getProducto().getMarca()));
        txtPrcCompra.setText(String.valueOf(clickedAccesorio.getProducto().getPrecioCompra()));
        txtPrcVenta.setText(String.valueOf(clickedAccesorio.getProducto().getPrecioVenta()));
        txtExistencias.setText(String.valueOf(clickedAccesorio.getProducto().getExistencias()));
        txtCodigoB.setText(String.valueOf(clickedAccesorio.getProducto().getCodigoBarras()));
        txtIdAccesorio.setText(String.valueOf(clickedAccesorio.getIdAccesorio()));
        txtIdProducto.setText(String.valueOf(clickedAccesorio.getProducto().getIdProducto()));


        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(true);
    }

    public void search() throws Exception {
        String filtro = txtBuscar.getText();
        listaAccesorios= FXCollections.observableArrayList(CA.search(filtro));
        tblAccesorios.setItems(listaAccesorios);
    }

    public void viewTableEstatus() throws Exception {
        String filtro = null;

        if (cbTableEstatus.getSelectionModel().getSelectedItem().toString().equals("Activo")) {
            filtro = "1";
        } else if (cbTableEstatus.getSelectionModel().getSelectedItem().toString().equals("Inactivo")) {
            filtro = "0";
        }

        listaAccesorios = FXCollections.observableArrayList(CA.getAll(filtro));
        tblAccesorios.setItems(listaAccesorios);
    }

    public void clean(){
        txtIdProducto.setText("");
        txtIdAccesorio.setText("");
        txtNombre.setText("");
        txtMarca.setText("");
        txtExistencias.setText("");
        txtCodigoB.setText("");
        txtPrcVenta.setText("");
        txtPrcCompra.setText("");

        btnDelete.setDisable(false);
        btnEdit.setDisable(false);
        btnAdd.setDisable(false);
    }

    public void validateFields(){


    }
}
