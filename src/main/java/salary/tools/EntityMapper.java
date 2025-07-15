package salary.tools;

import salary.model.entity.*;
import salary.model.entity.enums.*;

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
        Employee employee = new Employee();
        return Loan
                .builder()
                .id(resultSet.getInt("id"))
                .employee(employee)
                .loanType(LoanType.valueOf(resultSet.getString("loan_type")))
                .loanAmount(resultSet.getDouble("loan_Amount"))
                .loanInterest(resultSet.getDouble("loan_Interest"))
                .totalInstallments(resultSet.getInt("total_Installments"))
                .loanStartDate(resultSet.getDate("loan_start_Date") == null ? null : resultSet.getDate("loan_start_Date").toLocalDate())
                .loanFinishDate(resultSet.getDate("loan_finish_Date") == null ? null : resultSet.getDate("loan_finish_Date").toLocalDate())

                .build();
    }

    public static LoanInstallment loanItemMapper(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();
        Payslip payslip = new Payslip();
        return LoanInstallment
                .builder()
                .id(resultSet.getInt("id"))
                .loan(loan)
                .paymentDate(resultSet.getDate("payment_Date") == null ? null : resultSet.getDate("payment_Date").toLocalDate())
                .amountPaid(resultSet.getDouble("amount_Paid"))
                .payslip(payslip)
                .build();
    }

    public static Payslip payslipMapper(ResultSet resultSet) throws SQLException {
        User user = new User();
        Employee employee = new Employee();
        WorkRecordMonthly workRecordMonthly = new WorkRecordMonthly();
        SalaryComponents salaryComponents = new SalaryComponents();
        Deductions deductions = new Deductions();
        return Payslip
                .builder()
                .id(resultSet.getInt("id"))
                .user(user)
                .employee(employee)
                .workRecordMonthly(workRecordMonthly)
                .salaryComponents(salaryComponents)
                .deductions(deductions)
                .issueDate(resultSet.getDate("issue_Date") == null ? null : resultSet.getDate("issue_Date").toLocalDate())
                .month(resultSet.getString("month"))
                .year(resultSet.getString("year"))
                .build();

    }

    public static SalaryComponents salaryComponentsMapper(ResultSet resultSet) throws SQLException {
        Payslip payslip = new Payslip();
        return SalaryComponents
                .builder()
                .id(resultSet.getInt("id"))
                .payslip(payslip)
                .build();

    }

    public static Deductions deductionsMapper(ResultSet resultSet) throws SQLException {
        Payslip payslip = new Payslip();
        return Deductions
                .builder()
                .id(resultSet.getInt("id"))
                .payslip(payslip)
                .build();

    }

    public static WorkRecordMonthly workRecordMapper(ResultSet resultSet) throws SQLException {
        Payslip payslip = new Payslip();
        return WorkRecordMonthly.builder()
                .id(resultSet.getInt("id"))
                .payslip(payslip)
                .daysWorked(resultSet.getInt("days_worked"))
                .overtimeHours(resultSet.getString("over_time_Hours"))
                .underTimeHours(resultSet.getString("under_time_Hours")) // corrected column name
                .advance(resultSet.getDouble("advance"))

                .build();

    }

    public static EmploymentContract  EmploymentContractMapper (ResultSet resultSet) throws SQLException {
            Employee employee = new Employee();
            return

                    EmploymentContract.builder()
                            .id(resultSet.getInt("id"))
                            .employee(employee)
                            .issuancePersonnelOrderDate(Year.valueOf(resultSet.getString("issuance_Personnel_OrderDate")))
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
                            .marriageAllowance(resultSet.getDouble("MARAGE_ALLOWANCE"))
                            .childAllowance(resultSet.getDouble("CHILD_ALLOWANCE"))
                            .foodAllowance(resultSet.getDouble("FOOD_ALLOWANCE"))
                            .build();
    }



}
