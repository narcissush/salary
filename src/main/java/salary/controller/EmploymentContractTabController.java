package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import salary.model.entity.Allowance;
import salary.model.entity.EmploymentContract;
import salary.model.entity.enums.*;
import salary.model.services.AllowanceService;
import salary.model.services.EmployeeService;
import salary.model.services.EmploymentContractService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static salary.FormManager.mainFormController;

public class EmploymentContractTabController implements Initializable {
    @FXML
    private Button newContractBtn, saveContractBtn, editContractBtn, deleteContractBtn;
    @FXML
    private TextField contractIdTxt, dailySalaryTxt, monthlySalaryTxt, housingAllowanceTxt, foodAllowanceTxt, marriageAllowanceTxt, childAllowanceTxt, bazarKarTxt, fogholadeShoghlTxt;
    @FXML
    private TextField totalSalartTxt;
    @FXML
    private ComboBox<ContractType> contractTypeCmb;
    @FXML
    private ComboBox<JobTitle>  jobTitleCmb;
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
            saveContractBtn.setDisable(false);
            resertForm();
        });

        saveContractBtn.setOnAction(event -> {
            try {
                EmploymentContract employmentContract =
                        EmploymentContract.builder()
                                //.id(resultSet.getInt("id"))
                                .employee(AppState.employeeSelected)
                                .issuanceDate(issuanceDatePicker.getValue())
                                .hireDate(hireDatePicker.getValue())
                                .terminationDate(null)
                                .contractType(contractTypeCmb.getSelectionModel().getSelectedItem())
                                .department(departmentCmb.getSelectionModel().getSelectedItem())
                                .jobTitle(jobTitleCmb.getSelectionModel().getSelectedItem())
                                .position(positionCmb.getSelectionModel().getSelectedItem())
                                .dailySalary(Double.parseDouble(dailySalaryTxt.getText()))
                                .bazarKar(Double.parseDouble(dailySalaryTxt.getText()))
                                .fogholadeShoghl(Double.parseDouble(dailySalaryTxt.getText()))
                                .housingAllowance(Double.parseDouble(dailySalaryTxt.getText()))
                                .marriageAllowance(Double.parseDouble(dailySalaryTxt.getText()))
                                .childAllowance(Double.parseDouble(dailySalaryTxt.getText()))
                                .foodAllowance(Double.parseDouble(dailySalaryTxt.getText()))
                                .build();
                EmploymentContractService.save(employmentContract);
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "حکم صادر گردید!", ButtonType.OK);
                info.showAndWait();
                mainFormController.fillEmployeeTable(EmployeeService.findAll());
                saveContractBtn.setDisable(true);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });
    }

    private void resertForm() {
        contractIdTxt.clear();
        contractTypeCmb.getSelectionModel().clearSelection();
        jobTitleCmb.getSelectionModel().clearSelection();
        positionCmb.getSelectionModel().clearSelection();
        departmentCmb.getSelectionModel().clearSelection();

        dailySalaryTxt.clear();
        monthlySalaryTxt.clear();
        housingAllowanceTxt.setText(String.valueOf(AppState.allowanceSelected.getHousingAllowance()));
        foodAllowanceTxt.setText(String.valueOf(AppState.allowanceSelected.getFoodAllowance()));
        marriageAllowanceTxt.setText(String.valueOf(AppState.allowanceSelected.getMarriageAllowance()));
        childAllowanceTxt.setText(String.valueOf(AppState.allowanceSelected.getChildAllowance() * AppState.employeeSelected.getNumberOfChildren()));
        bazarKarTxt.clear();
        fogholadeShoghlTxt.clear();
        totalSalartTxt.clear();
    }

    public void setEmployeeContractInForm() {
        double monthlySalary = AppState.employmentContractSelected.getDailySalary() * 30;
        double housingAllowance = AppState.allowanceSelected.getHousingAllowance();
        double foodAllowance = AppState.allowanceSelected.getFoodAllowance();
        double marriageAllowance = AppState.allowanceSelected.getMarriageAllowance();
        double childAllowance = AppState.allowanceSelected.getChildAllowance();
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
            dailySalaryTxt.setText(String.valueOf(AppState.employmentContractSelected.getDailySalary()));

            monthlySalaryTxt.setText(String.valueOf(monthlySalary));
            housingAllowanceTxt.setText(String.valueOf(housingAllowance));
            foodAllowanceTxt.setText(String.valueOf(foodAllowance));
            marriageAllowanceTxt.setText(String.valueOf(marriageAllowance));
            childAllowanceTxt.setText(String.valueOf(childAllowance));
            bazarKarTxt.setText(String.valueOf(bazarKar));
            fogholadeShoghlTxt.setText(String.valueOf(fogholadeShoghl));
            totalSalartTxt.setText(String.valueOf(totalSalary));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



