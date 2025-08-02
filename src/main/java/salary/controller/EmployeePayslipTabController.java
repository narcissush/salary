package salary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
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
    private TableColumn<SalaryItem, String> amountSalaryComponentsCol,amountDeductionsCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        titleSalaryComponentsCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        amountSalaryComponentsCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());

        titleDeductionsCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        amountDeductionsCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());



        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 1400; i <= 1420; i++) {
            years.add(i);
        }
        yearCmb.setItems(years);
        int currentYear = DataConvert.MiladiToShamsi(LocalDate.now()).getYear();
        yearCmb.getSelectionModel().select(Integer.valueOf(currentYear));
        monthCmb.getItems().addAll(Month.values());

        calculatorBtn.setOnAction(event -> {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("fa", "IR"));
            symbols.setGroupingSeparator('٬');
            DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);


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

            totalSalaryComponentsLbl.setText(decimalFormat.format(salaryComponents));
            totalDeductionsLbl.setText(decimalFormat.format(deductions));
            totalSalaryLbl.setText(decimalFormat.format(totalSalary));
        });
    }

    public Integer fillSalaryComponentsTable() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("fa", "IR"));
        symbols.setGroupingSeparator('٬');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);

        SalaryComponents components=new SalaryComponents();
        ObservableList<SalaryItem> items = FXCollections.observableArrayList(
                new SalaryItem("حقوق ماهانه", decimalFormat.format(components.getMonthlySalary())),
                new SalaryItem("حق مسکن", decimalFormat.format(components.getHousingAllowance())),
                new SalaryItem("بن خواربار", decimalFormat.format(components.getFoodAllowance())),
                new SalaryItem("حق اولاد", decimalFormat.format(components.getTotalChildAllowance())),
                new SalaryItem("حق تاهل", decimalFormat.format(components.getMarriageAllowance())),
                new SalaryItem("بازارکار", decimalFormat.format(components.getBazarKar())),
                new SalaryItem("فوق العاده شغل", decimalFormat.format(components.getFogholadeShoghl())),
                new SalaryItem("اضافه‌کار", decimalFormat.format(components.getOverTime())),
                new SalaryItem("ماموریت", decimalFormat.format(components.getMissionAllowance()))
        );
        salaryComponentsTable.setItems(items);
        return (int) components.getTotalSalaryComponents();
    }

    public Integer fillDeductionsTable() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("fa", "IR"));
        symbols.setGroupingSeparator('٬');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);

        Deductions deductions=new Deductions();
        ObservableList<SalaryItem> items = FXCollections.observableArrayList(
                new SalaryItem("مالیات", decimalFormat.format(deductions.getTax())),
                new SalaryItem("بیمه", decimalFormat.format(deductions.getInsurance())),
                new SalaryItem("وام", decimalFormat.format(deductions.getLoanRepayment())),
                new SalaryItem("کسرکار", decimalFormat.format(deductions.getUnderTime()))
                );
        deductionsTable.setItems(items);
        return (int) deductions.getTotalDeductions();
    }

}


