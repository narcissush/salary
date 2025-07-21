package salary.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import salary.model.entity.Employee;

import static salary.FormManager.mainFormController;

import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;


import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
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
                Alert info = new Alert(Alert.AlertType.INFORMATION,
                        "اطلاعات جدید اضافه گردید!", ButtonType.OK);
                info.showAndWait();
                employeeIdTxt.setText(String.valueOf(EmployeeService.findByNationalId(nationalIdTxt.getText()).getId()));
                mainFormController.fillEmployeeTable(EmployeeService.findAll());
                saveEmployeeBtn.setDisable(true);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });



        editEmployeeBtn.setOnAction(event -> {
            try {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "آیا از ویرایش اطلاعات پرسنل اطمینان دارید؟", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = confirm.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {
                    Employee employee = Employee.builder()
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
                    Alert info = new Alert(Alert.AlertType.INFORMATION,
                            "اطلاعات پرسنل ویرایش گردید!", ButtonType.OK);
                    info.showAndWait();
                    mainFormController.fillEmployeeTable(EmployeeService.findAll());
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
        });



        deleteEmployeeBtn.setOnAction(event -> {
            try {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "آیا از حذف پرسنل اطمینان دارید؟!", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    EmployeeService.delete(Integer.parseInt(employeeIdTxt.getText()));

                    Alert info = new Alert(Alert.AlertType.INFORMATION,
                            "اطلاعات پرسنل حذف گردید!", ButtonType.OK);
                    info.showAndWait();
                    resetForm();
                    mainFormController.fillEmployeeTable(EmployeeService.findAll());
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
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
            firstNameTxt.setText(AppState.employeeSelected.getFirstName());
            lastNameTxt.setText(AppState.employeeSelected.getLastName());
            nationalIdTxt.setText(AppState.employeeSelected.getNationalId());
            fatherNameTxt.setText(AppState.employeeSelected.getFatherName());
            certificateNumberTxt.setText(AppState.employeeSelected.getCertificateNumber());
            birthPlaceCmb.getSelectionModel().select(AppState.employeeSelected.getBirthPlace());
            birthDatePicker.setValue(AppState.employeeSelected.getBirthDate());
            genderCmb.getSelectionModel().getSelectedItem();
            genderCmb.getSelectionModel().select(AppState.employeeSelected.getGender());
            employeeIdTxt.setText(String.valueOf(AppState.employeeSelected.getId()));
            educationCmb.getSelectionModel().select(AppState.employeeSelected.getEducation());
            majorCmb.getSelectionModel().select(AppState.employeeSelected.getMajor());
            phoneNumberTxt.setText(AppState.employeeSelected.getPhoneNumber());
            marriageCmb.getSelectionModel().select(AppState.employeeSelected.getMarriage());
            numberOfChildTxt.setText(String.valueOf(AppState.employeeSelected.getNumberOfChildren()));
            insuranceNumberTxt.setText(String.valueOf(AppState.employeeSelected.getInsuranceNumber()));
            bankAccountNumberTxt.setText(String.valueOf(AppState.employeeSelected.getBankAccountNumber()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
