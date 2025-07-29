package salary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import salary.model.entity.Allowance;
import salary.model.entity.enums.Year;
import salary.model.services.AllowanceService;
import salary.tools.DataConvert;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AllowanceFormController implements Initializable {
    @FXML
    private TextField housingAllowanceTxt,foodAllowanceTxt,marriageAllowanceTxt,childAllowanceTxt;
    @FXML
    private ComboBox<Integer> allowanceYearCmb;
    @FXML
    private Button saveBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 1400; i <= 1420; i++) {
            years.add(i);
        }
        allowanceYearCmb.setItems(years);
        int currentYear = DataConvert.MiladiToShamsi(LocalDate.now()).getYear();
        allowanceYearCmb.getSelectionModel().select(Integer.valueOf(currentYear));

        saveBtn.setOnAction(event -> {
            Allowance allowance = Allowance.builder()

                    .year(allowanceYearCmb.getSelectionModel().getSelectedItem())
                    .housingAllowance(Double.parseDouble(housingAllowanceTxt.getText()))
                    .foodAllowance(Double.parseDouble(foodAllowanceTxt.getText()))
                    .marriageAllowance(Double.parseDouble(marriageAllowanceTxt.getText()))
                    .childAllowance(Double.parseDouble(childAllowanceTxt.getText()))
                    .build();

            try {
                AllowanceService.save(allowance);
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "مزایا ثبت شد!", ButtonType.OK);
                info.show();
                AppState.allowanceSelected = allowance;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
