package salary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.layout.GridPane;
import salary.model.entity.Deductions;
import salary.model.entity.SalaryComponents;
import salary.model.entity.SalaryItem;
import salary.model.entity.WorkRecordMonthly;
import salary.model.entity.enums.Month;
import salary.tools.DataConvert;


public class EmployeePayslipTabController implements Initializable {
    @FXML
    private Button savePaysLipBtn,editPaysLipBtn,exportPaysLipBtn,calculatorBtn;
    @FXML
    private TextField daysWorkedTxt,overtimeHoursTxt,underTimeHoursTxt,leaveTxt;
    @FXML
    private Label totalSalaryComponentsLbl,totalDeductionsLbl,totalSalaryLbl;
    @FXML
    private ComboBox<Integer> yearCmb;
    @FXML
    private ComboBox<Month> monthCmb;
    @FXML
    private GridPane LoanListGridPane,totalSalaryGridPane;

    @FXML
    private TableView<SalaryItem> salaryComponentsTable,deductionsTable;

    @FXML
    private TableColumn<SalaryItem, String> titleSalaryComponentsCol,titleDeductionsCol;

    @FXML
    private TableColumn<SalaryItem, Number> amountSalaryComponentsCol,amountDeductionsCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titleSalaryComponentsCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        amountSalaryComponentsCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());

        titleDeductionsCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        amountDeductionsCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());

        amountSalaryComponentsCol.setCellFactory(column -> new TableCell<SalaryItem, Number>() {
            @Override
            protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(value.intValue()));
                }
            }
        });

        amountDeductionsCol.setCellFactory(column -> new TableCell<SalaryItem, Number>() {
            @Override
            protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(value.intValue()));
                }
            }
        });


        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 1400; i <= 1420; i++) {
            years.add(i);
        }
        yearCmb.setItems(years);
        int currentYear = DataConvert.MiladiToShamsi(LocalDate.now()).getYear();
        yearCmb.getSelectionModel().select(Integer.valueOf(currentYear));
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

           int salaryComponents=fillSalaryComponentsTable();
           int deductions=fillDeductionsTable();
           int totalSalary=salaryComponents-deductions;

            totalSalaryComponentsLbl.setText(String.valueOf(salaryComponents));
            totalDeductionsLbl.setText(String.valueOf(deductions));
            totalSalaryLbl.setText(String.valueOf(totalSalary));
        });
    }

    public Integer fillSalaryComponentsTable() {

        SalaryComponents components=new SalaryComponents();
        ObservableList<SalaryItem> items = FXCollections.observableArrayList(
                new SalaryItem("حقوق ماهانه", (int) components.getMonthlySalary()),
                new SalaryItem("حق مسکن", (int) components.getHousingAllowance()),
                new SalaryItem("بن خواربار", (int) components.getFoodAllowance()),
                new SalaryItem("حق اولاد", (int) components.getTotalChildAllowance()),
                new SalaryItem("حق تاهل", (int) components.getMarriageAllowance()),
                new SalaryItem("اضافه‌کار", (int) components.getOverTime()),
                new SalaryItem("ماموریت", (int) components.getMissionAllowance())
        );
        salaryComponentsTable.setItems(items);
        return (int) components.getTotalSalaryComponents();
    }
    public Integer fillDeductionsTable() {
        Deductions deductions=new Deductions();
        ObservableList<SalaryItem> items = FXCollections.observableArrayList(
                new SalaryItem("مالیات", (int) deductions.getTax()),
                new SalaryItem("بیمه", (int) deductions.getInsurance()),
                new SalaryItem("وام", (int) deductions.getLoanRepayment()),
                new SalaryItem("کسرکار", (int) deductions.getUnderTime())
                );
        deductionsTable.setItems(items);
        return (int) deductions.getTotalDeductions();
    }

}


