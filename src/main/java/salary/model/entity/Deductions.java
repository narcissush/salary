package salary.model.entity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder

public class Deductions implements Serializable {
    private int id;
    private Payslip payslip;
    List<LoanItem> loanList;

    public double getTax() {
        double exemption = 240_000_000;
        double taxable = Math.max(0, payslip.getSalaryComponents().getTotalSalaryComponents() - exemption);
        double tax = 0;

        double[] limits = {240_000_000, 300_000_000,380_000_000,500_000_000,667_000_000};
        double[] rates = {0.10, 0.15, 0.20,025, 0.30};

        double remaining = taxable;
        for (int i = 0; i < rates.length; i++) {
            double bracket = i < limits.length ? limits[i] : remaining;
            double amount = Math.min(remaining, bracket);
            tax += amount * rates[i];
            remaining -= amount;
            if (remaining <= 0) break;
        }
        return tax;
    }

    public double getInsurance() {
        return payslip.getSalaryComponents().getTotalSalaryComponents() * 0.07;
    }

    public double getUnderTimeDeduction() {
        return payslip.getEmployee().getDailySalary() / 8 * payslip.getWorkRecord().getUnderTimeHours();
    }

    public double getAdvance() {
        return payslip.getWorkRecord().getAdvance();
    }

    public double getLoanRepayment() {
        double totalLoan = 0;
        for (LoanItem loanItem : loanList) {
            totalLoan += loanItem.getAmountPaid();
        }
        return totalLoan;
    }

    public double getTotalDeductions() {
        return  getTax() +
                getInsurance() +
                getUnderTimeDeduction() +
                getAdvance() +
                getLoanRepayment();

    }
}
