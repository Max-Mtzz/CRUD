module com.jmc.crud {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.jmc.crud to javafx.fxml;
    exports com.jmc.crud;
}