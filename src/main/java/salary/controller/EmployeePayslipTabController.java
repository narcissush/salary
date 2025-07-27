package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class EmployeePayslipTabController implements Initializable {


    @FXML
    private TableView<StatRow> SalaryTable;
    @FXML
    private TableColumn<StatRow, String> SalaryCol;
    @FXML
    private TableColumn<StatRow, Number> TitleCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TitleCol.setCellValueFactory(c -> c.getValue().titleProperty());
        SalaryCol.setCellValueFactory(c -> c.getValue().valueProperty());

        ObservableList<StatRow> rows = FXCollections.observableArrayList(
                new StatRow("X", computeX()),
                new StatRow("Y", computeY())
        );
        SalaryTable.setItems(rows);
    }


    public void fillTable() {

    }

    public void addStatRow(String title, double val) {
        SalaryTable.getItems().add(new StatRow(title, val));
    }
}


