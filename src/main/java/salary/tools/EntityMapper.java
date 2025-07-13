package salary.tools;

import salary.model.entity.*;
import salary.model.entity.enums.Gender;
import salary.model.entity.enums.JobTitle;
import salary.model.entity.enums.Position;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EntityMapper {
    public static Employee employeeMapper(ResultSet resultSet) throws SQLException {
        return Employee
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .nationalId(resultSet.getString("national_id"))
                .education(resultSet.getString("education"))
                .married(resultSet.getBoolean("married"))
                .numberOfChildren(resultSet.getInt("number_of_children"))
                .gender(Gender.valueOf(resultSet.getString("GENDER")))
                .birthDate(resultSet.getDate("BIRTH_DATE") == null ? null : resultSet.getDate("BIRTH_DATE").toLocalDate())
                .insuranceNumber(resultSet.getString("Insurance_number"))
                .bankAccountNumber(resultSet.getString("bank_Account_Number"))
                .jobTitle(JobTitle.valueOf(resultSet.getString("Job_Title")))
                .position(Position.valueOf(resultSet.getString("Position")))
                .hireDate(resultSet.getDate("Hire_Date") == null ? null : resultSet.getDate("Hire_Date").toLocalDate())
                .terminationDate(resultSet.getDate("Termination_Date") == null ? null : resultSet.getDate("Termination_DATE").toLocalDate())
                .dailySalary(resultSet.getDouble("daily_salary"))
                .build();
    }

    public static User userMapper(ResultSet resultSet) throws SQLException {
        return User
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .nationalId(resultSet.getString("national_id"))
                .education(resultSet.getString("education"))
                .married(resultSet.getBoolean("married"))
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
                .loanType(resultSet.getString("loan_Type"))
                .loanAmount(resultSet.getDouble("loan_Amount"))
                .loanInterest(resultSet.getDouble("loan_Interest"))
                .totalInstallments(resultSet.getInt("total_Installments"))
                .loanStartDate(resultSet.getDate("loanstart_Date") == null ? null : resultSet.getDate("loanstart_Date").toLocalDate())
                .build();
    }

    public static LoanItem loanItemMapper(ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();
        Payslip payslip = new Payslip();
        return LoanItem
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
        WorkRecord workRecord = new WorkRecord();
        SalaryComponents salaryComponents = new SalaryComponents();
        Deductions deductions = new Deductions();
        return Payslip
                .builder()
                .id(resultSet.getInt("id"))
                .user(user)
                .employee(employee)
                .workRecord(workRecord)
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

    public static WorkRecord workRecordMapper(ResultSet resultSet) throws SQLException {
        Payslip payslip = new Payslip();
        return WorkRecord.builder()
                .id(resultSet.getInt("id"))
                .payslip(payslip)
                .daysWorked(resultSet.getInt("days_worked"))
                .overtimeHours(resultSet.getInt("over_time_Hours"))
                .underTimeHours(resultSet.getInt("under_time_Hours")) // corrected column name
                .advance(resultSet.getDouble("advance"))
                .startMission(resultSet.getTimestamp("start_Mission").toLocalDateTime())
                .endMission(resultSet.getTimestamp("end_Mission").toLocalDateTime())
                .build();

    }


}
