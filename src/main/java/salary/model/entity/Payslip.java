package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.Month;
import salary.model.entity.enums.Year;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder

public class Payslip implements Serializable {
    private int id;
    private  User user;
    private Employee employee;
    private WorkRecordMonthly workRecordMonthly;
    private SalaryComponents salaryComponents;
    private Deductions deductions;
    private LocalDate issueDate;
    private Month month;
    private Year year;


    public double totalSalary(){
        return salaryComponents.getTotalSalaryComponents()-deductions.getTotalDeductions();
    }

}
