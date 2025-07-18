package com.jmc.crud;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.jmc.crud.conexion;
import com.jmc.crud.Pelicula;
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

    private ObservableList<Pelicula> listaPeliculas = FXCollections.observableArrayList();
    private String generoSeleccionado = "";

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));

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
        String sql = "SELECT * FROM PELICULA";

        try (Connection conn = conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaPeliculas.add(new Pelicula(
                        rs.getInt("id_pelicula"),
                        rs.getString("nombre"),
                        rs.getString("genero"),
                        rs.getInt("año")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void agregarPelicula() {
        String sql = "INSERT INTO PELICULA (id_pelicula, nombre, genero, año) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            stmt.setString(2, txtNombre.getText());
            stmt.setString(3, generoSeleccionado);
            stmt.setInt(4, Integer.parseInt(txtAno.getText()));

            stmt.executeUpdate();
            cargarPeliculas();
            limpiarCampos();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarPelicula() {

    }
}