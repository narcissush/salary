package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import salary.model.entity.Employee;

import java.net.URL;
import java.util.ResourceBundle;



public class MainFormController implements Initializable {

    @FXML private AnchorPane employeeTabInclude;
    @FXML private EmployeeTabController employeeTabIncludeController;

    @FXML
    private Tab employeeTab, employeeContractTab, employeeLoanTab, employeeSalaryTab;



    @Override
    public void initialize(URL location, ResourceBundle resources) {



        }
    public void EmployeeSelected(Employee employee) {
        AppState.employee = employee;
        employeeTabIncludeController.setEmployee();
    }
    }



