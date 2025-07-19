package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import salary.FormManager;
import salary.model.entity.Employee;

import static salary.FormManager.mainFormController;

import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeTabController implements Initializable {
    @FXML
    private TextField firstNameTxt, lastNameTxt, fatherNameTxt, nationalIdTxt, certificateNumberTxt;
    @FXML
    private TextField employeeIdTxt, numberOfChildTxt, bankAccountNumberTxt, insuranceNumberTxt, phoneNumberTxt;
    @FXML
    private ComboBox<Gender> genderCmb;
    @FXML
    private ComboBox<Marriage> marriageCmb;
    @FXML
    private ComboBox<Education> educationCmb;
    @FXML
    private ComboBox<Major> majorCmb;
    @FXML
    private ComboBox<City> birthPlaceCmb;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private Button NewEmployeeBtn, saveEmployeeBtn, editEmployeeBtn, deleteEmployeeBtn;

    @FXML
    private EmployeeListController employeeListIncludeController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveEmployeeBtn.setDisable(true);

        genderCmb.getItems().addAll(Gender.values());
        birthPlaceCmb.getItems().addAll(City.values());
        marriageCmb.getItems().addAll(Marriage.values());
        majorCmb.getItems().addAll(Major.values());
        educationCmb.getItems().addAll(Education.values());
        birthPlaceCmb.getItems().addAll(City.values());



        NewEmployeeBtn.setOnAction(event -> {
            saveEmployeeBtn.setDisable(false);
            resetForm();
        });

        saveEmployeeBtn.setOnAction(event -> {
            try {
                Employee employee =
                        Employee.builder()
                                //.id(Integer.parseInt(employeeIdTxt.getText()))
                                .firstName(firstNameTxt.getText())
                                .lastName(lastNameTxt.getText())
                                .nationalId(nationalIdTxt.getText())
                                .fatherName(fatherNameTxt.getText())
                                .certificateNumber(certificateNumberTxt.getText())
                                .birthDate(birthDatePicker.getValue())
                                .birthPlace(birthPlaceCmb.getSelectionModel().getSelectedItem())
                                .gender(genderCmb.getSelectionModel().getSelectedItem())
                                .education(educationCmb.getSelectionModel().getSelectedItem())
                                .major(majorCmb.getSelectionModel().getSelectedItem())
                                .marriage(marriageCmb.getSelectionModel().getSelectedItem())
                                .numberOfChildren(Integer.parseInt(numberOfChildTxt.getText()))
                                .phoneNumber(phoneNumberTxt.getText())
                                .insuranceNumber(insuranceNumberTxt.getText())
                                .bankAccountNumber(bankAccountNumberTxt.getText())
                                .build();
                EmployeeService.save(employee);
                new Alert(Alert.AlertType.INFORMATION, "پرسنل جدید اضافه شد!", ButtonType.OK).show();
                employeeIdTxt.setText(String.valueOf(EmployeeService.findByNationalId(nationalIdTxt.getText()).getId()));
                mainFormController.RefillEmployeeList();
                saveEmployeeBtn.setDisable(true);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });

        editEmployeeBtn.setOnAction(event -> {
            try {
                Employee employee =
                        Employee.builder()
                                .id(Integer.parseInt(employeeIdTxt.getText()))
                                .firstName(firstNameTxt.getText())
                                .lastName(lastNameTxt.getText())
                                .nationalId(nationalIdTxt.getText())
                                .fatherName(fatherNameTxt.getText())
                                .certificateNumber(certificateNumberTxt.getText())
                                .birthDate(birthDatePicker.getValue())
                                .birthPlace(birthPlaceCmb.getSelectionModel().getSelectedItem())
                                .gender(genderCmb.getSelectionModel().getSelectedItem())
                                .education(educationCmb.getSelectionModel().getSelectedItem())
                                .major(majorCmb.getSelectionModel().getSelectedItem())
                                .marriage(marriageCmb.getSelectionModel().getSelectedItem())
                                .numberOfChildren(Integer.parseInt(numberOfChildTxt.getText()))
                                .phoneNumber(phoneNumberTxt.getText())
                                .insuranceNumber(insuranceNumberTxt.getText())
                                .bankAccountNumber(bankAccountNumberTxt.getText())
                                .build();
                EmployeeService.edit(employee);
                new Alert(Alert.AlertType.INFORMATION, "اطلاعات پرسنل ویرایش گردید!", ButtonType.OK).show();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
            mainFormController.RefillEmployeeList();

        });

        deleteEmployeeBtn.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "آیا از حذف پرسنل اطمینان دارید؟!", ButtonType.YES, ButtonType.NO);
                if (alert.showAndWait().get() == ButtonType.YES) {
                    EmployeeService.delete(Integer.parseInt(employeeIdTxt.getText()));
                    new Alert(Alert.AlertType.INFORMATION, "پرسنل حذف شد!", ButtonType.OK).show();
                 }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
            mainFormController.RefillEmployeeList();

        });
    }

    public void resetForm() {
        firstNameTxt.clear();
        lastNameTxt.clear();
        nationalIdTxt.clear();
        fatherNameTxt.clear();
        certificateNumberTxt.clear();
        birthPlaceCmb.getSelectionModel().clearSelection();
        birthDatePicker.setValue(LocalDate.now());
        genderCmb.getSelectionModel().clearSelection();
        genderCmb.getSelectionModel().clearSelection();
        employeeIdTxt.clear();
        educationCmb.getSelectionModel().clearSelection();
        majorCmb.getSelectionModel().clearSelection();
        phoneNumberTxt.clear();
        marriageCmb.getSelectionModel().clearSelection();
        numberOfChildTxt.clear();
        insuranceNumberTxt.clear();
        bankAccountNumberTxt.clear();
    }


    public void setEmployeeInForm() {
        try {
            firstNameTxt.setText(AppState.employee.getFirstName());
            lastNameTxt.setText(AppState.employee.getLastName());
            nationalIdTxt.setText(AppState.employee.getNationalId());
            fatherNameTxt.setText(AppState.employee.getFatherName());
            certificateNumberTxt.setText(AppState.employee.getCertificateNumber());
            birthPlaceCmb.getSelectionModel().select(AppState.employee.getBirthPlace());
            birthDatePicker.setValue(AppState.employee.getBirthDate());
            genderCmb.getSelectionModel().getSelectedItem();
            genderCmb.getSelectionModel().select(AppState.employee.getGender());
            employeeIdTxt.setText(String.valueOf(AppState.employee.getId()));
            educationCmb.getSelectionModel().select(AppState.employee.getEducation());
            majorCmb.getSelectionModel().select(AppState.employee.getMajor());
            phoneNumberTxt.setText(AppState.employee.getPhoneNumber());
            marriageCmb.getSelectionModel().select(AppState.employee.getMarriage());
            numberOfChildTxt.setText(String.valueOf(AppState.employee.getNumberOfChildren()));
            insuranceNumberTxt.setText(String.valueOf(AppState.employee.getInsuranceNumber()));
            bankAccountNumberTxt.setText(String.valueOf(AppState.employee.getBankAccountNumber()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
