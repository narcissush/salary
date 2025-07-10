package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder

public class Payslip implements Serializable {
    private int id;
    private  User user;
    private Employee employee;
    private WorkRecord workRecord;
    private SalaryComponents salaryComponents;
    private Deductions deductions;
    private LocalDate issueDate;
    private String period;


    public double totalSalary(){
        return salaryComponents.getTotalSalaryComponents()-deductions.getTotalDeductions();
    }

}
