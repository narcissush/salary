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
        double exemption = 240_000_000; // معافیت ماهانه
        double taxable = Math.max(0, payslip.getSalaryComponents().getTotalSalaryComponents() - exemption);
        double tax = 0;

        double[] limits = {160_000_000, 200_000_000, 200_000_000}; // بازه‌ها
        double[] rates = {0.10, 0.15, 0.20, 0.30};

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
        return payslip.getSalaryComponents().getTotalSalaryComponents() * 0.23;
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
