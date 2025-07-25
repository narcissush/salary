package salary.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import salary.model.entity.Employee;
import salary.model.entity.EmploymentContract;
import salary.model.entity.Loan;
import salary.model.entity.LoanInstallment;
import salary.model.entity.enums.Gender;
import salary.model.entity.enums.LoanType;
import salary.model.services.EmploymentContractService;
import salary.model.services.LoanInstallmentService;
import salary.model.services.LoanService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmploymentLoanTabController implements Initializable {
    @FXML
    private Button newLoanBtn, editLoanBtn, deleteLoanBtn;
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
        loanTypeCmb.getItems().addAll(LoanType.values());
        loanTable.getSelectionModel().selectedItemProperty().addListener((obs, oldLoan, selectedLoan) -> {
            if (selectedLoan != null) {
                try {
                    List<LoanInstallment> installments = LoanInstallmentService.findByLoanId(selectedLoan.getId());
                    fillLoanInstallmentTable(installments);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "خطا در بارگذاری اقساط وام: " + e.getMessage()).show();
                }
            } else {
                fillLoanInstallmentTable(null); // جدول خالی شود
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
