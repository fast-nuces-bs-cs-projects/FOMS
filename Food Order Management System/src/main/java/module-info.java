module pkg.foms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens pkg.foms to javafx.fxml;
    exports pkg.foms;

    opens pkg.foms.Controller to javafx.fxml;
    exports pkg.foms.Controller;
}