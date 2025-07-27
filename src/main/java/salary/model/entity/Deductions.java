package salary.model.entity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.controller.AppState;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder

public class Deductions implements Serializable {
        List<LoanInstallment> loanList;
        SalaryComponents salaryComponents=new SalaryComponents();

    public double getTax() {
        double exemption = 240_000_000;
        double taxable = Math.max(0, salaryComponents.getTotalSalaryComponents() - exemption);
        double tax = 0;

        double[] limits = {240_000_000, 300_000_000, 380_000_000, 500_000_000, 667_000_000};
        double[] rates = {0.10, 0.15, 0.20, 0.25, 0.30};

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
        return salaryComponents.getTotalSalaryComponents() * 0.07;
    }

    public double getUnderTime() {

        EmploymentContract employmentContract = new EmploymentContract();
        String input = AppState.workRecordMonthlySelected.getUnderTimeHours();
        String[] parts = input.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        double underTimeRate = employmentContract.getDailySalary() / 8.0;

        double underTimePay = hours * underTimeRate
                + (minutes / 60.0) * underTimeRate;

        return underTimePay;

    }



    public double getLoanRepayment() {
        double totalLoan = 0;
        for (LoanInstallment loanInstallment : loanList) {
            totalLoan += loanInstallment.getAmountPaid();
        }
        return totalLoan;
    }


    public double getTotalDeductions() {
        return getTax() +
                getInsurance() +
                getUnderTime() +
                getLoanRepayment();
    }
}
