module com.mycompany.javafx.proyectopgv_dad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.github.oshi;
    requires com.google.gson;
    
    opens com.mycompany.javafx.proyectopgv_dad_servidor.modelo;
    opens com.mycompany.javafx.proyectopgv_dad to javafx.fxml;
    exports com.mycompany.javafx.proyectopgv_dad;
    exports com.mycompany.javafx.proyectopgv_dad.controladores;
    opens com.mycompany.javafx.proyectopgv_dad.controladores;
}
