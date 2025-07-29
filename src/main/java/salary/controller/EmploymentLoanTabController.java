package salary.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanInstallment;
import salary.model.entity.LoanType;
import salary.model.services.EmployeeLoanService;
import salary.model.services.LoanTypeService;
import salary.tools.DataConvert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmploymentLoanTabController implements Initializable {
    @FXML
    private Button selectLoanBtn, insertLoanBtn;
    @FXML
    private TextField loanIdTxt, loanAmountTxt, loanIntrestTxt, totalInstallmentTxt, amountPaidTxt;
    @FXML
    private DatePicker loanStartDatePicker, loanFinishDatePicker;
    @FXML
    private ComboBox<LoanType> loanTypeCmb;

    @FXML
    private TableView<EmployeeLoan> loanTable;
    @FXML
    private TableColumn<EmployeeLoan, String> loanIdCol;
    @FXML
    private TableColumn<EmployeeLoan, String> loanTypeCol;
    @FXML
    private TableColumn<EmployeeLoan, String> LoanStartCol;
    @FXML
    private TableColumn<EmployeeLoan, String> LoanFinishCol;
    @FXML
    private TableColumn<EmployeeLoan, String> loanAmountCol;
    @FXML
    private TableColumn<EmployeeLoan, String> loanInterestCol;
    @FXML
    private TableColumn<EmployeeLoan, String> totalInstallmentCol;

    @FXML
    private TableView<LoanInstallment> loanInstallmentTable;
    @FXML
    private TableColumn<LoanInstallment, String> amountPaidCol;
    @FXML
    private TableColumn<LoanInstallment, String> paymentDateCol;

    LoanType loanTypeSelected = new LoanType();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loanTypeCmb.setOnMousePressed(event -> {
            try {
                List<LoanType> loanTypes = LoanTypeService.findAll();
                ObservableList<LoanType> items = FXCollections.observableArrayList(loanTypes);
                loanTypeCmb.setItems(items);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        loanTypeCmb.setOnAction(event -> {
            try {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("fa", "IR"));
                symbols.setGroupingSeparator('٬');
                DecimalFormat decimalFormat = new DecimalFormat("#,###.00", symbols);

                loanTypeSelected = loanTypeCmb.getValue();

                if (loanTypeSelected != null) {
                    double P = loanTypeSelected.getLoanAmount();
                    double annualInterest = loanTypeSelected.getLoanInterest();
                    int n = loanTypeSelected.getTotalInstallments();
                    loanAmountTxt.setText(decimalFormat.format(P));
                    loanIntrestTxt.setText(decimalFormat.format(annualInterest)); // اگر عدد ده‌دهی است
                    totalInstallmentTxt.setText(String.valueOf(n));
                    amountPaidTxt.setText(decimalFormat.format(loanTypeSelected.getAmountPayMonthly()));
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        loanStartDatePicker.setOnAction(event -> {
            LocalDate startDate = loanStartDatePicker.getValue();

            if (startDate != null && loanTypeSelected != null) {
                int installments = loanTypeSelected.getTotalInstallments();
                LocalDate endDate = startDate.plusMonths(installments);
                loanFinishDatePicker.setValue(endDate);
            }
        });

        selectLoanBtn.setOnAction(event -> {
            try {
                if (loanTypeSelected == null) {
                    new Alert(Alert.AlertType.INFORMATION, "نوع وام را انتخاب کنید!", ButtonType.OK).showAndWait();
                }
                if (loanStartDatePicker.getValue() == null) {
                    new Alert(Alert.AlertType.INFORMATION, "تاریخ دریافت وام را وارد نمایید.!", ButtonType.OK).showAndWait();
                }
                double loanAmount = DataConvert.ParseDouble(loanAmountTxt.getText());
                double interest = DataConvert.ParseDouble(loanIntrestTxt.getText());
                int totalInstallments = Integer.parseInt(totalInstallmentTxt.getText());
                LocalDate startDate = loanStartDatePicker.getValue();
                LocalDate endDate = loanFinishDatePicker.getValue();
                EmployeeLoan employeeLoan = EmployeeLoan
                        .builder()
                        .employee(AppState.employeeSelected)
                        .loanType(loanTypeSelected)
                        .loanStartDate(loanStartDatePicker.getValue())
                        .loanFinishDate(loanFinishDatePicker.getValue())
                        .build();
                EmployeeLoanService.save(employeeLoan);
                new Alert(Alert.AlertType.INFORMATION, "وام جدید برای پرسنل ثبت شد!", ButtonType.OK).showAndWait();
                reset();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void fillEmployeeLoanTable(List<EmployeeLoan> employeeLoan) {
        loanTable.refresh();
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("id")); // فیلد مستقیم

        loanTypeCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getLoanType().getLoanType()));

        LoanStartCol.setCellValueFactory(new PropertyValueFactory<>("loanStartDate"));
        LoanFinishCol.setCellValueFactory(new PropertyValueFactory<>("loanFinishDate"));

        loanAmountCol.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getLoanType().getLoanAmount()).asObject().asString());

        loanInterestCol.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getLoanType().getLoanInterest()).asObject().asString());

        totalInstallmentCol.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getLoanType().getTotalInstallments()).asObject().asString());

        loanTable.setItems(FXCollections.observableArrayList(employeeLoan));
    }

    public void fillLoanInstallmentTable(List<LoanInstallment> installments) {
        amountPaidCol.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        if (installments == null || installments.isEmpty()) {
            loanInstallmentTable.setItems(FXCollections.observableArrayList());
        } else {
            loanInstallmentTable.setItems(FXCollections.observableArrayList(installments));
        }
    }

    public void reset() {
        loanAmountTxt.clear();
        loanIntrestTxt.clear();
        totalInstallmentTxt.clear();
        amountPaidTxt.clear();
        loanFinishDatePicker.setValue(null);
        loanFinishDatePicker.getEditor().clear();

        loanStartDatePicker.setValue(null);
        loanStartDatePicker.getEditor().clear();
    }

}
