package salary.model.entity;

import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.controller.AppState;
import salary.model.services.EmployeeLoanService;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder

public class Deductions implements Serializable {
    List<LoanInstallment> loanList;
    SalaryComponents salaryComponents = new SalaryComponents();

    public double getTax() {
        SalaryComponents components = new SalaryComponents();
        double totalSalary = components.getTotalSalaryComponents();

        double tax;
        if (240_000_000< totalSalary && totalSalary <= 300_000_000) {
            tax = (totalSalary - 240_000_000) * 0.10;


        } else if (300_000_000 < totalSalary && totalSalary <= 380_000_000) {
            tax = 6_000_000 +
                    ((totalSalary - 300_000_000) *0.15);

        } else if (380_000_000 < totalSalary && totalSalary <= 500_000_000) {
            tax = 6_000_000 +
                    12_000_000 +
                    ((totalSalary - 380_000_000) *0.20);

        } else if (500_000_000 < totalSalary && totalSalary <= 660_000_000) {
            tax = 6_000_000 +
                    12_000_000 +
                    24_000_000 +
                    ((totalSalary - 500_000_000) *0.25);

        } else if (660_000_000 < totalSalary) {
            tax = 6_000_000 +
                    12_000_000 +
                    24_000_000 +
                    40_000_000 +
                    ((totalSalary - 660_000_000) *0.30);
        } else return 0;

        return tax;
    }

    public double getInsurance() {
        return salaryComponents.getTotalSalaryComponents() * 0.07;
    }

    public double getUnderTime() {

        EmploymentContract employmentContract = new EmploymentContract();
        String input = AppState.workRecordMonthlySelected.getUnderTimeHours();
        int hours = 0;
        int minutes = 0;
        if (input != null & input.contains(":")) {
            String[] parts = input.split(":");

            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
        } else {
            hours = Integer.parseInt(input);
            minutes = 0;
        }

        double underTimeRate = employmentContract.getDailySalary() / 8.0;
        double underTimePay = hours * underTimeRate
                + (minutes / 60.0) * underTimeRate;
        return underTimePay;

    }


    public double getLoanRepayment() {
        return AppState.totalLoanAmount;
    }


    public double getTotalDeductions() {
        return getTax() +
                getInsurance() +
                getUnderTime() +
                getLoanRepayment();
    }
}
