package com.mycompany.javafx.proyectopgv_dad.controladores;

import com.mycompany.javafx.proyectopgv_dad.App;
import com.mycompany.javafx.proyectopgv_dad.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Iproy
 */
public class LoginViewController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button closeButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField contrasenaTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void login(ActionEvent event) {
        try {

            boolean credencialesCorrectas = false;
            String email = emailTextField.getText();
            if (!Utils.esCorreoGmail(email)) {
                Utils.mostrarInformacion("Formato de e-mail", "-El formato del email introducido no parece el correcto.\n-Debes usar un email que concuerde con el formato de gmail(aaaa@gmail.com).");
            } else if (contrasenaTextField.getLength() == 0) {
                Utils.mostrarInformacion("Contraseña/token no introducida", "-La contraseña/token no puede estar vacia.");
            } else {

                root = App.loadFXML("MainView");
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);

                scene.getRoot().setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                scene.getRoot().setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() - xOffset);
                        stage.setY(event.getScreenY() - yOffset);
                    }
                });

                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void openLink(ActionEvent event) {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "https://cloud.google.com/docs/authentication/token-types?hl=es-419");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
