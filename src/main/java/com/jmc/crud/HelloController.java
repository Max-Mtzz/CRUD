package com.jmc.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class HelloController {

    @FXML private TextField txtId, txtNombre, txtAno;
    @FXML private SplitMenuButton generoMenu;
    @FXML private TableView<Pelicula> tablePeliculas;
    @FXML private TableColumn<Pelicula, Integer> colId;
    @FXML private TableColumn<Pelicula, String> colNombre;
    @FXML private TableColumn<Pelicula, String> colGenero;
    @FXML private TableColumn<Pelicula, Integer> colAno;
    @FXML private TableColumn<Pelicula, String> colEstado;

    private ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();
    private String generoSeleccionado = "";

    public void initialize() {
        // Asociar columnas con propiedades
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        incializarGeneroMenu();
        cargarPeliculas();

        tablePeliculas.setItems(listaPeliculas);

        tablePeliculas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtId.setText(String.valueOf(newVal.getId()));
                txtNombre.setText(newVal.getNombre());
                txtAno.setText(String.valueOf(newVal.getAno()));
                generoMenu.setText(newVal.getGenero());
                generoSeleccionado = newVal.getGenero();
            }
        });
    }

    private void incializarGeneroMenu() {
        String[] generos = {"Drama", "Comedia", "Terror", "Suspenso", "Ciencia ficción"};
        for (String genero : generos) {
            MenuItem item = new MenuItem(genero);
            item.setOnAction(e -> {
                generoSeleccionado = genero;
                generoMenu.setText(genero);
            });
            generoMenu.getItems().add(item);
        }
    }

    @FXML
    private void cargarPeliculas() {
        listaPeliculas.clear();
        String sql = "SELECT * FROM PELICULA ORDER BY ID_PELICULA ASC";

        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaPeliculas.add(new Pelicula(
                        rs.getInt("id_pelicula"),
                        rs.getString("nombre"),
                        rs.getString("genero"),
                        rs.getInt("año"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarPelicula() {
        String sql = "INSERT INTO PELICULA (id_pelicula, nombre, genero, año, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            stmt.setString(2, txtNombre.getText());
            stmt.setString(3, generoSeleccionado);
            stmt.setInt(4, Integer.parseInt(txtAno.getText()));
            stmt.setString(5, "Activo"); // Estado por defecto

            stmt.executeUpdate();
            cargarPeliculas();
            limpiarCampos();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarPelicula() {
        Pelicula seleccionada = tablePeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        String sql = "UPDATE PELICULA SET nombre = ?, genero = ?, año = ? WHERE id_pelicula = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, txtNombre.getText());
            stmt.setString(2, generoSeleccionado);
            stmt.setInt(3, Integer.parseInt(txtAno.getText()));
            stmt.setInt(4, Integer.parseInt(txtId.getText()));

            stmt.executeUpdate();
            cargarPeliculas();
            limpiarCampos();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarPelicula() {
        Pelicula seleccionada = tablePeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        String sql = "DELETE FROM PELICULA WHERE id_pelicula = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, seleccionada.getId());
            stmt.executeUpdate();

            cargarPeliculas();
            limpiarCampos();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cambiarEstado() {
        Pelicula seleccionada = tablePeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        String nuevoEstado = seleccionada.getEstado().equalsIgnoreCase("Activo") ? "Inactivo" : "Activo";

        String sql = "UPDATE PELICULA SET estado = ? WHERE id_pelicula = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, seleccionada.getId());
            stmt.executeUpdate();

            cargarPeliculas();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void desactivarPelicula() {
        Pelicula seleccionada = tablePeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una película para desactivar.");
            return;
        }

        if (seleccionada.getEstado().equalsIgnoreCase("Inactivo")) {
            mostrarAlerta("La película ya está inactiva.");
            return;
        }

        String sql = "UPDATE PELICULA SET estado = ? WHERE id_pelicula = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Inactivo");
            stmt.setInt(2, seleccionada.getId());
            stmt.executeUpdate();

            cargarPeliculas();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reactivarPelicula() {
        Pelicula seleccionada = tablePeliculas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Selecciona una película para reactivar.");
            return;
        }

        if (seleccionada.getEstado().equalsIgnoreCase("Activo")) {
            mostrarAlerta("La película ya está activa.");
            return;
        }

        String sql = "UPDATE PELICULA SET estado = ? WHERE id_pelicula = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Activo");
            stmt.setInt(2, seleccionada.getId());
            stmt.executeUpdate();

            cargarPeliculas();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtAno.clear();
        generoMenu.setText("Género");
        generoSeleccionado = "";
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
