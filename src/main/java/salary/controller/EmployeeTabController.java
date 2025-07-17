package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import salary.FormManager;


import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeTabController implements Initializable {
    @FXML
    private TextField firstNameTxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FormManager formManager = new FormManager();



    }

    public void setEmployee() {


        try {
            System.out.println(AppState.employee);
            firstNameTxt.setText(AppState.employee.getFirstName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
