package com.jmc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static final String UBICACION_WALLET = "C:\\Users\\CA2-MAYO\\Documents\\CRUD\\src\\Wallet";
    private static final String JDBC_URL = "jdbc:oracle:thin:@scmks95p3fiyytjc_high"; // Usa el alias de tu tnsnames.ora
    private static final String USER = "ADMIN";
    private static final String PASS = "elpato55T$jardin";

    static {
        System.setProperty("oracle.net.tns_admin", UBICACION_WALLET);
    }

    public static Connection conectar() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

}
