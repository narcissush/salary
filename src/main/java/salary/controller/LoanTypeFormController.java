package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import salary.model.entity.LoanType;
import salary.model.services.LoanTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanTypeFormController implements Initializable {
    @FXML
    private TextField loanTypeTxt,loanAmountTxt,loanIntrestTxt,totalInstallmentTxt,amountPayMonthlyTxt;
    @FXML
    private Button saveBtn,editBtn,calculateBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        saveBtn.setOnAction(event -> {
            try {
                LoanType loanType = LoanType.builder()
                        .loanType(loanTypeTxt.getText())
                        .loanAmount(Double.parseDouble(loanAmountTxt.getText()))
                        .loanInterest(Integer.parseInt(loanIntrestTxt.getText()))
                        .totalInstallments(Integer.parseInt(totalInstallmentTxt.getText()))
                        .build();
                LoanTypeService.save(loanType);
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "مشخصات وام جدید ثبت شد!", ButtonType.OK);
                info.showAndWait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        editBtn.setOnAction(event -> {
            try {
                LoanType loanType = LoanType.builder()
                        .loanType(loanTypeTxt.getText())
                        .loanAmount(Double.parseDouble(loanAmountTxt.getText()))
                        .loanInterest(Integer.parseInt(loanIntrestTxt.getText()))
                        .totalInstallments(Integer.parseInt(totalInstallmentTxt.getText()))
                        .build();
                LoanTypeService.edit(loanType);
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "مشخصات وام جدید ثبت شد!", ButtonType.OK);
                info.showAndWait();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        calculateBtn.setOnAction(event -> {
            LoanType loanType = LoanType.builder()
                    .loanType(loanTypeTxt.getText())
                    .loanAmount(Double.parseDouble(loanAmountTxt.getText()))
                    .loanInterest(Integer.parseInt(loanIntrestTxt.getText()))
                    .totalInstallments(Integer.parseInt(totalInstallmentTxt.getText()))
                    .build();
            amountPayMonthlyTxt.setText(String.valueOf(loanType.getAmountPayMonthly()));
        });

    }
}
