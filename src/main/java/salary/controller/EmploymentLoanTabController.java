package salary.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import salary.model.entity.LoanInstallment;
import salary.model.entity.LoanType;
import salary.model.services.LoanTypeService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EmploymentLoanTabController implements Initializable {
    @FXML
    private Button saveLoanBtn, editLoanBtn, deleteLoanBtn;
    @FXML
    private TextField loanIdTxt,loanAmountTxt,loanIntrestTxt,totalInstallmentTxt,amountPaidTxt;
    @FXML
    private DatePicker startLoanDatePicker;
    @FXML
    private ComboBox<LoanType> loanTypeCmb;

    @FXML
    private TableView<Loan> loanTable;
    @FXML
    private TableColumn<Loan, String> loanIdCol;
    @FXML
    private TableColumn<Loan, String> loanTypeCol;
    @FXML
    private TableColumn<Loan, String> startLoanDateCol;
    @FXML
    private TableColumn<Loan, String> loanAmountCol;
    @FXML
    private TableColumn<Loan, String> loanInterestCol;
    @FXML
    private TableColumn<Loan, String> totalInstallmentCol;

    @FXML
    private TableView<LoanInstallment> loanInstallmentTable;
    @FXML
    private TableColumn<LoanInstallment, String> amountPaidCol;
    @FXML
    private TableColumn<LoanInstallment, String> paymentDateCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<LoanType> loanTypes = LoanTypeService.findAll();
            loanTypeCmb.setItems(FXCollections.observableArrayList(loanTypes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loanTypeCmb.setOnAction(event -> {
            LoanType selected = loanTypeCmb.getValue();
            if (selected != null) {
                loanAmountTxt.setText(String.valueOf((int) selected.getLoanAmount()));
                loanIntrestTxt.setText(String.valueOf(selected.getLoanInterest()));
                totalInstallmentTxt.setText(String.valueOf(selected.getTotalInstallments()));

                double totalWithInterest = selected.getLoanAmount() + (selected.getLoanAmount() * selected.getLoanInterest());
                double monthlyInstallment = totalWithInterest / selected.getTotalInstallments();
                amountPaidTxt.setText(String.valueOf((int) monthlyInstallment));
            }
        });

        saveLoanBtn.setOnAction(event -> {
            try {
                // استخراج و بررسی مقادیر ورودی
                LoanType selectedLoanType = loanTypeCmb.getValue();
                if (selectedLoanType == null) {
                    throw new Exception("نوع وام را انتخاب کنید.");
                }

                double loanAmount = Double.parseDouble(loanAmountTxt.getText());
                double interest = Double.parseDouble(loanIntrestTxt.getText());
                int totalInstallments = Integer.parseInt(totalInstallmentTxt.getText());
                LocalDate startDate = startLoanDatePicker.getValue();

                if (startDate == null) {
                    throw new Exception("تاریخ شروع وام را وارد کنید.");
                }

                // ساخت وام
//                Loan loan = Loan.builder()
//                        .employee(AppState.employeeSelected)
//                        .loanType(selectedLoanType)
//                        .loanAmount(loanAmount)
//                        .loanInterest(interest)
//                        .totalInstallments(totalInstallments)
//                        .loanStartDate(startDate)
//                        .loanFinishDate(startDate.plusMonths(totalInstallments))
//                        .build();

                //LoanService.save(loan);
                new Alert(Alert.AlertType.INFORMATION, "وام جدید ثبت شد!", ButtonType.OK).showAndWait();

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "مقادیر عددی را به‌درستی وارد کنید.").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });


    }

    public void fillLoanTable(List<Loan> loans) {
        loanTable.refresh();
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        loanTypeCol.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        startLoanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanStartDate"));
        loanAmountCol.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        loanInterestCol.setCellValueFactory(new PropertyValueFactory<>("loanInterest"));
        totalInstallmentCol.setCellValueFactory(new PropertyValueFactory<>("totalInstallments"));
        loanTable.setItems(FXCollections.observableArrayList(loans));
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
