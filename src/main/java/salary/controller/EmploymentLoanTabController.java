package salary.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanInstallment;
import salary.model.entity.LoanType;
import salary.model.services.EmployeeLoanService;
import salary.model.services.EmployeeService;
import salary.model.services.LoanTypeService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EmploymentLoanTabController implements Initializable {
    @FXML
    private Button selectLoanBtn, insertLoanBtn;
    @FXML
    private TextField loanIdTxt, loanAmountTxt, loanIntrestTxt, totalInstallmentTxt, amountPaidTxt;
    @FXML
    private DatePicker startLoanDatePicker, endLoanDatePicker;
    @FXML
    private ComboBox<LoanType> loanTypeCmb;

    @FXML
    private TableView<EmployeeLoan> loanTable;
    @FXML
    private TableColumn<EmployeeLoan, String> loanIdCol;
    @FXML
    private TableColumn<EmployeeLoan, String> loanTypeCol;
    @FXML
    private TableColumn<EmployeeLoan, String> startLoanDateCol;
    @FXML
    private TableColumn<EmployeeLoan, String> endLoanDateCol;
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
        try {
            List<LoanType> loanTypes = LoanTypeService.findAll();
            loanTypeCmb.setItems(FXCollections.observableArrayList(loanTypes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loanTypeCmb.setOnAction(event -> {
            loanTypeSelected = loanTypeCmb.getValue();
            if (loanTypeSelected != null) {
                loanAmountTxt.setText(String.valueOf((int) loanTypeSelected.getLoanAmount()));
                loanIntrestTxt.setText(String.valueOf(loanTypeSelected.getLoanInterest()));
                totalInstallmentTxt.setText(String.valueOf(loanTypeSelected.getTotalInstallments()));

                double totalWithInterest = loanTypeSelected.getLoanAmount() + (loanTypeSelected.getLoanAmount() * loanTypeSelected.getLoanInterest());
                double monthlyInstallment = totalWithInterest / loanTypeSelected.getTotalInstallments();
                amountPaidTxt.setText(String.valueOf((int) monthlyInstallment));
            }
        });

        startLoanDatePicker.setOnAction(event -> {
            LocalDate startDate = startLoanDatePicker.getValue();

            if (startDate != null && loanTypeSelected != null) {
                int installments = loanTypeSelected.getTotalInstallments();
                LocalDate endDate = startDate.plusMonths(installments);
                endLoanDatePicker.setValue(endDate);
            }
        });

        selectLoanBtn.setOnAction(event -> {
            try {
                if (loanTypeSelected == null) {
                    throw new Exception("نوع وام را انتخاب کنید.");
                }
                if (startLoanDatePicker.getValue() == null) {
                    throw new Exception("تاریخ شروع وام را وارد کنید.");
                }

                double loanAmount = Double.parseDouble(loanAmountTxt.getText());
                double interest = Double.parseDouble(loanIntrestTxt.getText());
                int totalInstallments = Integer.parseInt(totalInstallmentTxt.getText());
                LocalDate startDate = startLoanDatePicker.getValue();
                LocalDate endDate = endLoanDatePicker.getValue();
                EmployeeLoan employeeLoan = EmployeeLoan
                        .builder()
                        .employee(AppState.employeeSelected)
                        .loanType(loanTypeSelected)
                        .loanStartDate(startLoanDatePicker.getValue())
                        .loanFinishDate(endLoanDatePicker.getValue())
                        .build();
                EmployeeLoanService.save(employeeLoan);
                new Alert(Alert.AlertType.INFORMATION, "وام جدید برای پرسنل ثبت شد!", ButtonType.OK).showAndWait();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });


    }

    public void fillEmployeeLoanTable(List<EmployeeLoan> employeeLoan) {
        loanTable.refresh();
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        loanTypeCol.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        startLoanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanStartDate"));
        endLoanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanFinishDate"));
        loanAmountCol.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        loanInterestCol.setCellValueFactory(new PropertyValueFactory<>("loanInterest"));
        totalInstallmentCol.setCellValueFactory(new PropertyValueFactory<>("totalInstallments"));
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

}
