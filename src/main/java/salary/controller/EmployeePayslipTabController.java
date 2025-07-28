package salary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import salary.model.entity.SalaryComponents;
import salary.model.entity.SalaryItem;
import salary.model.entity.WorkRecordMonthly;
import salary.model.entity.enums.Month;
import salary.model.entity.enums.Year;


public class EmployeePayslipTabController implements Initializable {
    @FXML
    private Button savePaysLipBtn,editPaysLipBtn,exportPaysLipBtn,calculatorBtn;
    @FXML
    private TextField daysWorkedTxt,overtimeHoursTxt,underTimeHoursTxt,leaveTxt;
    @FXML
    private ComboBox<Year> yearCmb;
    @FXML
    private ComboBox<Month> monthCmb;

    @FXML
    private TableView<SalaryItem> salaryTable;

    @FXML
    private TableColumn<SalaryItem, String> titleCol;

    @FXML
    private TableColumn<SalaryItem, Number> amountCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        yearCmb.getItems().addAll(Year.values());
        monthCmb.getItems().addAll(Month.values());

        calculatorBtn.setOnAction(event -> {
            WorkRecordMonthly workRecordMonthly=
                    WorkRecordMonthly.builder()
                            .year(yearCmb.getSelectionModel().getSelectedItem())
                            .month(monthCmb.getSelectionModel().getSelectedItem())
                            .daysWorked(Integer.parseInt(daysWorkedTxt.getText()))
                            .overtimeHours(overtimeHoursTxt.getText())
                            .underTimeHours(underTimeHoursTxt.getText())
                            .leave(leaveTxt.getText())
                            .build();
            AppState.workRecordMonthlySelected=workRecordMonthly;
            fillTable();


        });
    }

    public void fillTable() {

        SalaryComponents components=new SalaryComponents();

        ObservableList<SalaryItem> items = FXCollections.observableArrayList(
                new SalaryItem("حقوق ماهانه", (double) components.getMonthlySalary()),
                new SalaryItem("حق اولاد",(double) components.getTotalChildAllowance()),
                new SalaryItem("حق مسکن", (double)components.getHousingAllowance()),
                new SalaryItem("حق اولاد",(double) components.getTotalChildAllowance()),
                new SalaryItem("بن خواربار",(double) components.getFoodAllowance()),
                new SalaryItem("اضافه‌کار",(double) components.getOverTime()),
                new SalaryItem("جمع کل", (double)components.getTotalSalaryComponents())
        );

        salaryTable.setItems(items);
    }

}


