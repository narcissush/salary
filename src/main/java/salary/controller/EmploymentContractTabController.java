package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import salary.model.entity.Allowance;
import salary.model.entity.EmploymentContract;
import salary.model.entity.enums.*;
import salary.model.services.AllowanceService;
import salary.model.services.EmploymentContractService;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmploymentContractTabController implements Initializable {
    @FXML
    private Button newContractBtn, saveContractBtn, editContractBtn, deleteContractBtn;
    @FXML
    private Button nextContractBtn, previousContractBtn;
    @FXML
    private TextField contractIdTxt, dailySalaryTxt, monthlySalaryTxt, housingAllowanceTxt, foodAllowanceTxt, marriageAllowanceTxt, childAllowanceTxt, bazarKarTxt, fogholadeShoghlTxt;
    @FXML
    private TextField totalSalartTxt;
    @FXML
    private ComboBox<ContractType> contractTypeCmb;
    @FXML
    private ComboBox<JobTitle> jobTitleCmb;
    @FXML
    private ComboBox<Position> positionCmb;
    @FXML
    private ComboBox<Department> departmentCmb;
    @FXML
    private DatePicker issuanceDatePicker, hireDatePicker;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveContractBtn.setDisable(true);
        try {
            Allowance allowance = AllowanceService.findByYear(Year.Y1404);
            if (allowance == null) {
                System.err.println("AllowanceService.findByYear returned null!");
            } else {
                AppState.allowanceSelected = allowance;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        contractTypeCmb.getItems().addAll(ContractType.values());
        jobTitleCmb.getItems().addAll(JobTitle.values());
        positionCmb.getItems().addAll(Position.values());
        departmentCmb.getItems().addAll(Department.values());

        newContractBtn.setOnAction(event -> {
            if (AppState.employeeSelected != null) {
                saveContractBtn.setDisable(false);
                resertForm();
                System.out.println("1");

            } else {
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "پرسنل را انتخاب نمایید!", ButtonType.OK);
                info.showAndWait();
            }
        });

        saveContractBtn.setOnAction(event -> {
            try {
                EmploymentContract employmentContract =
                        EmploymentContract.builder()
                                .employee(AppState.employeeSelected)
                                .issuanceDate(issuanceDatePicker.getValue())
                                .hireDate(hireDatePicker.getValue())
                                .terminationDate(null)
                                .contractType(contractTypeCmb.getSelectionModel().getSelectedItem())
                                .department(departmentCmb.getSelectionModel().getSelectedItem())
                                .jobTitle(jobTitleCmb.getSelectionModel().getSelectedItem())
                                .position(positionCmb.getSelectionModel().getSelectedItem())
                                .dailySalary(parseFarsiDouble(dailySalaryTxt.getText()))
                                .bazarKar(parseFarsiDouble(bazarKarTxt.getText()))
                                .fogholadeShoghl(parseFarsiDouble(fogholadeShoghlTxt.getText()))
                                .housingAllowance(parseFarsiDouble(housingAllowanceTxt.getText()))
                                .marriageAllowance(parseFarsiDouble(marriageAllowanceTxt.getText()))
                                .childAllowance(parseFarsiDouble(childAllowanceTxt.getText()))
                                .foodAllowance(parseFarsiDouble(foodAllowanceTxt.getText()))
                                .build();
                EmploymentContractService.save(employmentContract);
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "حکم صادر گردید!", ButtonType.OK);
                info.showAndWait();
                AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(employmentContract.getId());
                saveContractBtn.setDisable(true);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        editContractBtn.setOnAction(event -> {
            try {
                EmploymentContract employmentContract =
                        EmploymentContract.builder()
                                .id(Integer.parseInt(contractIdTxt.getText()))
                                .employee(AppState.employeeSelected)
                                .issuanceDate(issuanceDatePicker.getValue())
                                .hireDate(hireDatePicker.getValue())
                                .terminationDate(null)
                                .contractType(contractTypeCmb.getSelectionModel().getSelectedItem())
                                .department(departmentCmb.getSelectionModel().getSelectedItem())
                                .jobTitle(jobTitleCmb.getSelectionModel().getSelectedItem())
                                .position(positionCmb.getSelectionModel().getSelectedItem())
                                .dailySalary(parseFarsiDouble(dailySalaryTxt.getText()))
                                .bazarKar(parseFarsiDouble(bazarKarTxt.getText()))
                                .fogholadeShoghl(parseFarsiDouble(fogholadeShoghlTxt.getText()))
                                .housingAllowance(parseFarsiDouble(housingAllowanceTxt.getText()))
                                .marriageAllowance(parseFarsiDouble(marriageAllowanceTxt.getText()))
                                .childAllowance(parseFarsiDouble(childAllowanceTxt.getText()))
                                .foodAllowance(parseFarsiDouble(foodAllowanceTxt.getText()))
                                .build();
                EmploymentContractService.edit(employmentContract);
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "حکم ویرایش گردید!", ButtonType.OK);
                info.showAndWait();
                AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(employmentContract.getId());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });
        nextContractBtn.setOnAction(e -> {
            if (AppState.employmentContractListSelected != null &&
                    AppState.currentContractIndex < AppState.employmentContractListSelected.size() - 1) {

                AppState.currentContractIndex++;
                AppState.employmentContractSelected = AppState.employmentContractListSelected.get(AppState.currentContractIndex);
                setEmployeeContractInForm();
            }
        });

        previousContractBtn.setOnAction(e -> {
            if (AppState.employmentContractSelected != null &&
                    AppState.currentContractIndex > 0) {
                AppState.currentContractIndex--;
                AppState.employmentContractSelected = AppState.employmentContractListSelected.get(AppState.currentContractIndex);
                setEmployeeContractInForm();
            }
        });
    }

    public void resertForm() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("fa", "IR"));
        symbols.setGroupingSeparator('٬');
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00", symbols);

        contractIdTxt.clear();
        contractTypeCmb.getSelectionModel().clearSelection();
        jobTitleCmb.getSelectionModel().clearSelection();
        positionCmb.getSelectionModel().clearSelection();
        departmentCmb.getSelectionModel().clearSelection();
        issuanceDatePicker.setValue(LocalDate.now());
        hireDatePicker.setValue(null);
        dailySalaryTxt.setText(decimalFormat.format(0.00));
        monthlySalaryTxt.setText(decimalFormat.format(0.00));
        housingAllowanceTxt.setText(decimalFormat.format(AppState.allowanceSelected.getHousingAllowance()));
        foodAllowanceTxt.setText(decimalFormat.format(AppState.allowanceSelected.getFoodAllowance()));
        marriageAllowanceTxt.setText(decimalFormat.format(AppState.allowanceSelected.getMarriageAllowance()));
        childAllowanceTxt.setText(decimalFormat.format(AppState.allowanceSelected.getChildAllowance() * AppState.employeeSelected.getNumberOfChildren()));
        bazarKarTxt.setText(decimalFormat.format(0.00));
        fogholadeShoghlTxt.setText(decimalFormat.format(0.00));
        totalSalartTxt.setText(decimalFormat.format(0.00));
    }

    public void setEmployeeContractInForm() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("fa", "IR"));
        symbols.setGroupingSeparator('٬');
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00", symbols);



        double monthlySalary = AppState.employmentContractSelected.getDailySalary() * 30;
        double housingAllowance = AppState.employmentContractSelected.getHousingAllowance();
        double foodAllowance = AppState.employmentContractSelected.getFoodAllowance();
        double marriageAllowance = AppState.employmentContractSelected.getMarriageAllowance();
        double childAllowance = AppState.employmentContractSelected.getChildAllowance();
        double bazarKar = AppState.employmentContractSelected.getBazarKar();
        double fogholadeShoghl = AppState.employmentContractSelected.getFogholadeShoghl();
        double totalSalary = monthlySalary
                + housingAllowance
                + foodAllowance
                + marriageAllowance
                + childAllowance
                + bazarKar
                + fogholadeShoghl;
        try {
            contractIdTxt.setText(String.valueOf(AppState.employmentContractSelected.getId()));
            contractTypeCmb.getSelectionModel().select(AppState.employmentContractSelected.getContractType());
            jobTitleCmb.getSelectionModel().select(AppState.employmentContractSelected.getJobTitle());
            positionCmb.getSelectionModel().select(AppState.employmentContractSelected.getPosition());
            departmentCmb.getSelectionModel().select(AppState.employmentContractSelected.getDepartment());
            issuanceDatePicker.setValue(AppState.employmentContractSelected.getIssuanceDate());
            hireDatePicker.setValue(AppState.employmentContractSelected.getHireDate());

            dailySalaryTxt.setText(decimalFormat.format(AppState.employmentContractSelected.getDailySalary()));
            monthlySalaryTxt.setText(decimalFormat.format(monthlySalary));
            housingAllowanceTxt.setText(decimalFormat.format(housingAllowance));
            foodAllowanceTxt.setText(decimalFormat.format(foodAllowance));
            marriageAllowanceTxt.setText(decimalFormat.format(marriageAllowance));
            childAllowanceTxt.setText(decimalFormat.format(childAllowance));
            bazarKarTxt.setText(decimalFormat.format(bazarKar));
            fogholadeShoghlTxt.setText(decimalFormat.format(fogholadeShoghl));
            totalSalartTxt.setText(decimalFormat.format(totalSalary));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private double parseFarsiDouble(String text) {
        if (text == null || text.isEmpty()) return 0.0;
        text = text.replace("٬", "").replace("٫", ".").replace(" ", "");
        return Double.parseDouble(text);
    }
}




