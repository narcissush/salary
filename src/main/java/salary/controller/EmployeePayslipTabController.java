package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;


public class EmployeePayslipTabController implements Initializable {


    @FXML
    private GridPane salaryComponentsGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        salaryComponentsGridPane.addRow(0, new Label("ID:"), new Label("n"));
//        salaryComponentsGridPane.addRow(1, new Label("Name:"), new Label("n"));
//        salaryComponentsGridPane.addRow(2, new Label("Hours:"), new Label("n"));
//        salaryComponentsGridPane.addRow(3, new Label("Rate:"), new Label("n"));
//        salaryComponentsGridPane.addRow(4, new Label("Salary:"), new Label("n"));
    }


    public void fillTable() {

    }

}


