package salary.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import salary.FormManager;
import salary.model.entity.Employee;
import salary.model.entity.EmployeeLoan;
import salary.model.entity.EmploymentContract;
import salary.model.services.EmployeeService;
import salary.model.services.EmploymentContractService;
import salary.model.services.EmployeeLoanService;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TextField searchIdTxt, searchNameFamilyTxt;
    @FXML
    private TableColumn<Employee, String> employeeIdCol;
    @FXML
    private TableColumn<Employee, String> employeeNameFamilyCol;
    @FXML
    private MenuItem allowanceMenuItem,loanMenuItem;
    @FXML
    private EmployeeTabController employeeTabController;
    @FXML
    private EmploymentContractTabController employmentContractTabController;
    @FXML
    private EmploymentLoanTabController employmentLoanTabController;
    @FXML
    private EmployeePayslipTabController employeePayslipTabController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
         fillEmployeeTable(EmployeeService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
        allowanceMenuItem.setOnAction(event -> {
            try {
                FormManager formManager = new FormManager();
                formManager.showAllowanceFormController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        loanMenuItem.setOnAction(event -> {
            try {
                FormManager formManager = new FormManager();
                formManager.showLoanTypeFormController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EventHandler<Event> tableChangeEvent = (mouseEvent -> {

                try {
                    if (employeeTable.getSelectionModel().getSelectedItem() != null) {
                        AppState.employeeSelected = employeeTable.getSelectionModel().getSelectedItem();
                        employeeTabController.setEmployeeInForm();
                        List<EmploymentContract> contracts = EmploymentContractService.findByEmployeeId(AppState.employeeSelected.getId());
                        if (contracts != null && !contracts.isEmpty()) {
                            AppState.employmentContractListSelected = contracts;
                            AppState.currentContractIndex = contracts.size() - 1;
                            AppState.employmentContractSelected = contracts.get(AppState.currentContractIndex);
                            employmentContractTabController.setEmployeeContractInForm();
                        } else {
                            AppState.employmentContractListSelected = null;
                            AppState.currentContractIndex = 0;
                            AppState.employmentContractSelected = null;
                        }
                        List<EmployeeLoan> loans = EmployeeLoanService.findByEmployeeId(AppState.employeeSelected.getId());
                        if (loans != null && !loans.isEmpty()) {
                            employmentLoanTabController.fillEmployeeLoanTable(loans);
                            AppState.totalLoanAmount= 0.0;
                            for (EmployeeLoan loan : loans) {
                                AppState.totalLoanAmount += loan.getLoanType().getAmountPayMonthly();
                            }
                        }
                        else {
                            employmentLoanTabController.fillEmployeeLoanTable(Collections.emptyList());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        });
        employeeTable.setOnMouseReleased(tableChangeEvent);
        employeeTable.setOnKeyReleased(tableChangeEvent);


        searchIdTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Employee> list = new ArrayList<>();
            try {
                if (!searchIdTxt.getText().isEmpty()) {
                  list.add(EmployeeService.findById(Integer.parseInt(searchIdTxt.getText())));
                    fillEmployeeTable(list);
                } else {
                    fillEmployeeTable(EmployeeService.findAll());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void fillEmployeeTable(List<Employee> employeeList) {
        employeeTable.refresh();
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameFamilyCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullName())
        );
        employeeTable.setItems(FXCollections.observableArrayList(employeeList));
    }


}



