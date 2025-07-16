package salary.tools;

import salary.controller.AppState;
import salary.model.entity.*;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper {
    public static Employee employeeMapper(ResultSet resultSet) throws SQLException {
        return Employee
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .nationalId(resultSet.getString("national_id"))
                .education(Education.valueOf(resultSet.getString("Education")))
                .married(Married.valueOf(resultSet.getString("married")))
                .numberOfChildren(resultSet.getInt("number_of_children"))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                .insuranceNumber(resultSet.getString("Insurance_number"))
                .bankAccountNumber(resultSet.getString("bank_Account_Number"))
                .build();
    }

    public static User userMapper(ResultSet resultSet) throws SQLException {
        return User
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .nationalId(resultSet.getString("national_id"))
                .education(Education.valueOf(resultSet.getString("Education")))
                .married(Married.valueOf(resultSet.getString("married")))
                .numberOfChildren(resultSet.getInt("number_of_children"))
                .gender(Gender.valueOf(resultSet.getString("GENDER")))
                .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                .username(resultSet.getString("user_name"))
                .password(resultSet.getString("password"))
                .build();
    }

    public static Loan loanMapper(ResultSet resultSet) throws SQLException {
        return Loan
                .builder()
                .id(resultSet.getInt("id"))
                .employee(AppState.employee)
                .loanType(LoanType.valueOf(resultSet.getString("loan_type")))
                .loanAmount(resultSet.getDouble("loan_Amount"))
                .loanInterest(resultSet.getDouble("loan_Interest"))
                .totalInstallments(resultSet.getInt("total_Installments"))
                .loanStartDate(resultSet.getDate("loan_start_Date") == null ? null : resultSet.getDate("loan_start_Date").toLocalDate())
                .loanFinishDate(resultSet.getDate("loan_finish_Date") == null ? null : resultSet.getDate("loan_finish_Date").toLocalDate())

                .build();
    }

    public static LoanInstallment loanItemMapper(ResultSet resultSet) throws SQLException {

        return LoanInstallment
                .builder()
                .id(resultSet.getInt("id"))
                //.loan(loan)
                .paymentDate(resultSet.getDate("payment_Date") == null ? null : resultSet.getDate("payment_Date").toLocalDate())
                .amountPaid(resultSet.getDouble("amount_Paid"))
                .payslip(AppState.payslip)
                .build();
    }

    public static Payslip payslipMapper(ResultSet resultSet) throws SQLException {

        return Payslip
                .builder()
                .id(resultSet.getInt("id"))
                .user(AppState.user)
                .employee(AppState.employee)
                .workRecordMonthly(AppState.workRecordMonthly)
                .issueDate(resultSet.getDate("issue_Date") == null ? null : resultSet.getDate("issue_Date").toLocalDate())

                .build();
    }


    public static WorkRecordMonthly workRecordMonthlyMapper(ResultSet resultSet) throws SQLException {

        return WorkRecordMonthly.builder()
                .id(resultSet.getInt("id"))
                .month(Month.valueOf(resultSet.getString("month")))
                .year(Year.valueOf(resultSet.getString("year")))
                .employee(AppState.employee)
                .daysWorked(resultSet.getInt("days_worked"))
                .overtimeHours(resultSet.getString("over_time_Hours"))
                .underTimeHours(resultSet.getString("under_time_Hours"))
                .leave(resultSet.getString("leave")) // corrected column name
                .advance(resultSet.getDouble("advance"))
                .build();
    }

    public static EmploymentContract  EmploymentContractMapper (ResultSet resultSet) throws SQLException {

        return               EmploymentContract.builder()
                            .id(resultSet.getInt("id"))
                            .employee(AppState.employee)
                            .issuancePersonnelOrderDate(Year.valueOf(resultSet.getString("issuance_Personnel_Order_Date")))
                            .startContractDate(resultSet.getDate("start_Contract_Date") == null ? null : resultSet.getDate("start_Contract_Date").toLocalDate())
                            .endContractDate(resultSet.getDate("end_Contract_Date") == null ? null : resultSet.getDate("end_Contract_Date").toLocalDate())
                            .contractType(ContractType.valueOf(resultSet.getString("Contract_Type")))
                            .department(Department.valueOf(resultSet.getString("Department")))
                            .jobTitle(JobTitle.valueOf(resultSet.getString("job_Title")))
                            .position(Position.valueOf(resultSet.getString("position")))
                            .hireDate(resultSet.getDate("hire_Date") == null ? null : resultSet.getDate("hire_Date").toLocalDate())
                            .terminationDate(resultSet.getDate("termination_Date") == null ? null : resultSet.getDate("termination_Date").toLocalDate())
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
