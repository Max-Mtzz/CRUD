package com.jmc.crud;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection con = conexion.conectar()) {
            System.out.println("¡Conexión exitosa a Oracle!");
        } catch (Exception e) {
            System.out.println("Error de conexión:");
            e.printStackTrace();
        }
    }
}
