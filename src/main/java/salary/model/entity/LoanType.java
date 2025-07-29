package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder


public class LoanType implements Serializable {
    private int id;
    private String loanType;
    private double loanAmount;
    private int loanInterest;
    private int totalInstallments;

    public int getAmountPayMonthly() {
        double P = loanAmount;
        double annualInterestPercent = loanInterest;      // مثلاً 23
        double annualRate = annualInterestPercent / 100.0; // تبدیل به 0.23
        double r = annualRate / 12.0;                      // نرخ ماهانه
        int n = totalInstallments;

        if (r <= 0 || n <= 0) {
            return (int) Math.round(P / n);
        }

        double pow = Math.pow(1 + r, n);
        double numerator = P * r * pow;
        double denominator = pow - 1;
        double emi = numerator / denominator;

        return (int) Math.round(emi);
    }


    @Override
    public String toString() {
        return loanType; // برای نمایش در ComboBox
    }
}
