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

}