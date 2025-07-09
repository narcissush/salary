package salary.model.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder

public class Loan implements Serializable {
    private int id;
    private Employee employee;
    private String loanType;
    private double loanAmount;           // مبلغ کل وام
    private double loanInterest;         // درصد بهره سالیانه
    private int totalInstallments;       // تعداد کل اقساط
    private LocalDate loanstartDate;         // تاریخ شروع بازپرداخت

    List<LoanItem> LoanItems; // لیست اقساط این وام

    // محاسبه مبلغ قسط ماهیانه
    public double getMonthlyInstallment() {
        double monthlyRate = (loanInterest / 100) / 12;
        if (monthlyRate == 0) {
            return loanAmount / totalInstallments;
        }
        double factor = Math.pow(1 + monthlyRate, totalInstallments);
        return (loanAmount * monthlyRate * factor) / (factor - 1);
    }

    // محاسبه کل پرداختی
    public double getTotalPaid() {
        if (LoanItems == null) {
            return 0;
        } else {
            double total = 0;
            for (LoanItem item : LoanItems) {
                total += item.getAmountPaid();
            }
            return total;
        }
    }

    // باقیمانده وام
    public double getRemainingAmount() {
        int paidInstallments = (LoanItems == null) ? 0 : LoanItems.size();
        int remainingInstallments = totalInstallments - paidInstallments;
        return remainingInstallments * getMonthlyInstallment();
    }


}
