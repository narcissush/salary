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
    private Employee employee;           // پرسنلی که وام را گرفته
    private String loanType;             // نوع وام (مثلاً مسکن، ازدواج)
    private double loanAmount;           // مبلغ کل وام
    private double loanInterest;         // درصد بهره سالیانه
    private int totalInstallments;       // تعداد کل اقساط
    private LocalDate startDate;         // تاریخ شروع بازپرداخت

    private List<LoanItem> installments; // لیست اقساط این وام

    // محاسبه مبلغ قسط ماهیانه به روش ساده (می‌تونه به روش استاندارد هم ارتقا پیدا کنه)
    public double getMonthlyInstallment() {
        double monthlyRate = (loanInterest / 100) / 12;
        int n = totalInstallments;
        double r = monthlyRate;
        if (r == 0) return loanAmount / n;

        return (loanAmount * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
    }

    // محاسبه کل پرداختی
    public double getTotalPaid() {
//        if (installments == null) return 0;
//        return installments.stream().mapToDouble(LoanItem::getAmountPaid).sum();

        if (installments == null) {
            return 0;
        } else {
            double total = 0;
            for (LoanItem item : installments) {
                total += item.getAmountPaid();
            }
            return total;
        }
    }

    // باقیمانده وام
    public double getRemainingAmount() {
        int paidInstallments = (installments == null) ? 0 : installments.size();
        int remainingInstallments = totalInstallments - paidInstallments;
        return remainingInstallments * getMonthlyInstallment();
    }


}
