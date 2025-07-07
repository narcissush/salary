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

    private Employee employee;                  // اطلاعات کارمند
    private WorkRecord workRecord;              // رکورد کاری همان ماه
    private SalaryComponents salaryComponents;  // مزایا و حقوق
    private Deductions deductions;              // کسورات

    private LocalDate issueDate;                // تاریخ صدور فیش
    private String period;                      // مثلاً "تیر 1403" یا "2025-07"



    // حقوق نهایی قابل پرداخت
    public double totalSalary(){
        return salaryComponents.getTotalSalaryComponents()-deductions.getTotalDeductions();
    }

}
