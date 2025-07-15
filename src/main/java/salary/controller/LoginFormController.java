package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import salary.FormManager;
import salary.model.entity.User;
import javafx.scene.control.*;
import salary.model.services.UserService;


import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    @FXML
    private TextField usernameTxt,passwordTxt;
    @FXML
    private Button loginBtn,createUserBtn,forgetPasswordBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginBtn.setOnAction(event -> {
            try {
                User user = UserService.findByUserAndPassword(usernameTxt.getText(), passwordTxt.getText());
                AppState.user = user;
                FormManager formManager = new FormManager();
                formManager.showMainFormController();

                Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                currentStage.close();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });


    }
}
