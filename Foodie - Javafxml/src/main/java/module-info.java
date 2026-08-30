module com.foodie.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    exports com.foodie.app;
    opens com.foodie.app.controller to javafx.fxml;
    opens com.foodie.app.model to com.google.gson;
    opens com.foodie.app.service to com.google.gson;
    exports com.foodie.app.model;
    exports com.foodie.app.service;
    exports com.foodie.app.api;
}


