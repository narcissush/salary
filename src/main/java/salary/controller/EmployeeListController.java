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
import salary.controller.MainFormController;

import salary.model.entity.Employee;
import salary.model.services.EmployeeService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static salary.FormManager.employeeTabController;

public class EmployeeListController implements Initializable {
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TextField searchIdTxt, searchNameFamilyTxt;
    @FXML
    private TableColumn<Employee, String> employeeIdCol;
    @FXML
    private TableColumn<Employee, String> employeeNameFamilyCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            fillEmployeeTable(EmployeeService.findAll());
            AppState.employee = EmployeeService.findById(1);
            if (AppState.employee != null) employeeTabController.setEmployee(AppState.employee);

        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

        EventHandler<Event> tableChangeEvent = (mouseEvent -> {
           AppState.employee = employeeTable.getSelectionModel().getSelectedItem();
            employeeTabController.setEmployee(AppState.employee);
        });
        employeeTable.setOnMouseReleased(tableChangeEvent);
        employeeTable.setOnKeyReleased(tableChangeEvent);

        searchIdTxt.textProperty().addListener((observable, oldValue, newValue) -> {
             try {
                 if (!searchIdTxt.getText().isEmpty()) {
                     Employee employee = EmployeeService.findById(Integer.parseInt(searchIdTxt.getText()));
                     List<Employee> list = new ArrayList<>();
                     list.add(employee);
                     fillEmployeeTable(list);
                 }
                 else {
                     fillEmployeeTable(EmployeeService.findAll());
                 }
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

    }



    private void fillEmployeeTable(List<Employee> employeeList) {
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        employeeNameFamilyCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullName())
        );
        employeeTable.setItems(FXCollections.observableArrayList(employeeList));
    }
}
