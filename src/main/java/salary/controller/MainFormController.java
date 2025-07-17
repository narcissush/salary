package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import salary.model.entity.Employee;
import salary.model.services.EmployeeService;

import java.net.URL;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    @FXML
    private EmployeeTabController employeeTabController;

    @FXML
    private Tab employeeTab,employeeContractTab,employeeLoanTab,employeeSalaryTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AppState.employee= EmployeeService.findById(1);
            if (AppState.employee != null) employeeTabController.setEmployee(AppState.employee);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

    }


}
