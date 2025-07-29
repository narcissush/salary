package salary.tools;

import salary.controller.AppState;
import salary.model.entity.*;
import salary.model.entity.enums.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EntityMapper {
    public static Employee employeeMapper(ResultSet resultSet) throws SQLException {
        return Employee
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_Name"))
                .lastName(resultSet.getString("last_Name"))
                .nationalId(resultSet.getString("national_Id"))
                .fatherName(resultSet.getString("father_name"))
                .certificateNumber(resultSet.getString("certificate_Number"))
                .birthDate(resultSet.getDate("birth_Date").toLocalDate())
                .birthPlace(City.valueOf(resultSet.getString("birth_Place")))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .education(Education.valueOf(resultSet.getString("education")))
                .major(Major.valueOf(resultSet.getString("major")))
                .marriage(Marriage.valueOf(resultSet.getString("marriage")))
                .numberOfChildren(resultSet.getInt("number_Of_Children"))
                .phoneNumber(resultSet.getString("Phone_number"))
                .insuranceNumber(resultSet.getString("insurance_Number"))
                .bankAccountNumber(resultSet.getString("bank_Account_Number"))
                .build();
    }

    public static User userMapper(ResultSet resultSet) throws SQLException {
        return User
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_Name"))
                .lastName(resultSet.getString("last_Name"))
                .nationalId(resultSet.getString("national_Id"))
                .fatherName(resultSet.getString("father_name"))
                .certificateNumber(resultSet.getString("certificate_Number"))
                .birthDate(resultSet.getDate("birth_Date").toLocalDate())
                .birthPlace(City.valueOf(resultSet.getString("birth_place")))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .education(Education.valueOf(resultSet.getString("education")))
                .major(Major.valueOf(resultSet.getString("major")))
                .marriage(Marriage.valueOf(resultSet.getString("marriage")))
                .numberOfChildren(resultSet.getInt("number_Of_Children"))
                .phoneNumber(resultSet.getString("Phone_number"))
                .username(resultSet.getString("user_name"))
                .password(resultSet.getString("pass_word"))
                .build();
    }

    public static LoanType loanTypeMapper(ResultSet resultSet) throws SQLException {
        return LoanType
                .builder()
                .id(resultSet.getInt("id"))
                .loanType(resultSet.getString("loan_type"))
                .loanAmount(resultSet.getDouble("loan_amount"))
                .loanInterest(resultSet.getInt("loan_interest"))
                .totalInstallments(resultSet.getInt("total_installments"))
                .build();
    }

    public static EmployeeLoan employeeLoanMapper(ResultSet resultSet) throws SQLException {

        LoanType loanType = new LoanType();
        loanType.setId(resultSet.getInt("lt_id"));
        loanType.setLoanType(resultSet.getString("loan_type"));
        loanType.setLoanAmount(resultSet.getDouble("loan_amount"));
        loanType.setLoanInterest(resultSet.getInt("loan_interest"));
        loanType.setTotalInstallments(resultSet.getInt("total_installments"));

        return EmployeeLoan.builder()
                .id(resultSet.getInt("id"))
                .employee(AppState.employeeSelected)
                .loanType(loanType)
                .loanStartDate(resultSet.getDate("loan_start_date").toLocalDate())
                .loanFinishDate(resultSet.getDate("loan_finish_date").toLocalDate())
                .build();
    }

    public static LoanInstallment loanInstallmentMapper(ResultSet resultSet) throws SQLException {

        LoanType loanType = new LoanType();
        loanType.setId(resultSet.getInt("id"));
        loanType.setLoanType(resultSet.getString("loan_type"));
        loanType.setLoanAmount(resultSet.getDouble("loan_amount"));
        loanType.setLoanInterest(resultSet.getInt("loan_interest"));
        loanType.setTotalInstallments(resultSet.getInt("total_installments"));

        EmployeeLoan employeeLoan = EmployeeLoan
                .builder()
                .id(resultSet.getInt("id"))
                .employee(AppState.employeeSelected)
                .loanType(loanType)
                .loanStartDate(resultSet.getDate("loan_start_date").toLocalDate())
                .loanFinishDate(resultSet.getDate("loan_finish_date").toLocalDate())
                .build();

        Payslip payslip = new Payslip();
        payslip.setId(resultSet.getInt("id"));
        if (resultSet.getDate("issue_date") != null) {
            payslip.setIssueDate(resultSet.getDate("issue_date").toLocalDate());
        }

        return LoanInstallment
                .builder()
                .id(resultSet.getInt("id"))
                .employeeLoan(employeeLoan)
                .payslip(payslip)
                .amountPaid(resultSet.getDouble("amount_paid"))
                .paymentDate(resultSet.getDate("payment_date") == null ? null : resultSet.getDate("payment_date").toLocalDate())
                .build();
    }


    public static Payslip payslipMapper(ResultSet resultSet) throws SQLException {
        return Payslip
                .builder()
                .id(resultSet.getInt("id"))
                .user(AppState.userSelected)
                .employee(AppState.employeeSelected)
                .workRecordMonthly(AppState.workRecordMonthlySelected)
                .issueDate(resultSet.getDate("issue_Date") == null ? null : resultSet.getDate("issue_Date").toLocalDate())
                .build();
    }

    public static Allowance allowanceMapper(ResultSet resultSet) throws SQLException {

        return Allowance
                .builder()
                .id(resultSet.getInt("id"))
                .year(resultSet.getInt("year"))
                .housingAllowance(resultSet.getDouble("Housing_Allowance"))
                .foodAllowance(resultSet.getDouble("food_Allowance"))
                .marriageAllowance(resultSet.getDouble("marriage_Allowance"))
                .childAllowance(resultSet.getDouble("child_Allowance"))
                .build();
    }


    public static WorkRecordMonthly workRecordMonthlyMapper(ResultSet resultSet) throws SQLException {
        return WorkRecordMonthly.builder()
                .id(resultSet.getInt("id"))
                .month(Month.valueOf(resultSet.getString("month")))
                .year(resultSet.getInt("year"))
                .employee(AppState.employeeSelected)
                .daysWorked(resultSet.getInt("days_worked"))
                .overtimeHours(resultSet.getString("over_time_Hours"))
                .underTimeHours(resultSet.getString("under_time_Hours"))
                .leave(resultSet.getString("leave")) // corrected column name
                .build();
    }

    public static EmploymentContract EmploymentContractMapper(ResultSet resultSet) throws SQLException {
        return EmploymentContract.builder()
                .id(resultSet.getInt("id"))
                .employee(AppState.employeeSelected)
                .issuanceDate(resultSet.getDate("issuance_Date") == null ? null : resultSet.getDate("issuance_Date").toLocalDate())
                .hireDate(resultSet.getDate("hire_Date") == null ? null : resultSet.getDate("hire_Date").toLocalDate())
                .terminationDate(resultSet.getDate("termination_Date") == null ? null : resultSet.getDate("termination_Date").toLocalDate())
                .contractType(ContractType.valueOf(resultSet.getString("Contract_Type")))
                .department(Department.valueOf(resultSet.getString("Department")))
                .jobTitle(JobTitle.valueOf(resultSet.getString("job_Title")))
                .position(Position.valueOf(resultSet.getString("position")))
                .dailySalary(resultSet.getDouble("daily_Salary"))
                .bazarKar(resultSet.getDouble("bazar_Kar"))
                .fogholadeShoghl(resultSet.getDouble("fogholade_Shoghl"))
                .housingAllowance(resultSet.getDouble("HOUSING_ALLOWANCE"))
                .marriageAllowance(resultSet.getDouble("MARRIAGE_ALLOWANCE"))
                .childAllowance(resultSet.getDouble("CHILD_ALLOWANCE"))
                .foodAllowance(resultSet.getDouble("FOOD_ALLOWANCE"))
                .build();
    }


}
