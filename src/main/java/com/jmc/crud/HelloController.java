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

    private void cargarPeliculas() {

    }
}