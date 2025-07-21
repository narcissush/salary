package salary.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import salary.model.entity.Employee;
import salary.model.services.EmployeeService;
import salary.model.services.EmploymentContractService;


import java.net.URL;
import java.util.ArrayList;
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
    private EmployeeTabController employeeTabController;
    @FXML
    private EmploymentContractTabController employmentContractTabController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillEmployeeTable(EmployeeService.findAll());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

        EventHandler<Event> tableChangeEvent = (mouseEvent -> {
            try {
                AppState.employeeSelected = employeeTable.getSelectionModel().getSelectedItem();
                if (AppState.employeeSelected != null) {
                    employeeTabController.setEmployeeInForm();
                }
                if (EmploymentContractService.findByEmployeeId(AppState.employeeSelected.getId())!=null) {
                    AppState.employmentContractSelected= EmploymentContractService.findByEmployeeId(AppState.employeeSelected.getId());
                    employmentContractTabController.setEmployeeContractInForm();
                }
            } catch (Exception e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
//                alert.show();
                e.printStackTrace();
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
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
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



