package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.controller.AppState;
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
    private LocalDate issueDate;


    public double getTotalSalary(){
        SalaryComponents salaryComponents=new SalaryComponents();
        Deductions deductions=new Deductions();
        return salaryComponents.getTotalSalaryComponents() - deductions.getTotalDeductions();

    }

}
