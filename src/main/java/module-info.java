module com.jmc.crud {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.jmc.crud to javafx.fxml;
    exports com.jmc.crud;
}