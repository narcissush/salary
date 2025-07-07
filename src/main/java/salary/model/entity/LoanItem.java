package salary.model.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder


public class LoanItem implements Serializable {
    private int id;
    private Loan loan;               // وامی که این قسط به آن مربوط است
    private LocalDate paymentDate;   // تاریخ پرداخت
    private double amountPaid;       // مبلغ پرداختی
    private Payslip payslip;         // اگر با فیش حقوقی پرداخت شده
    private double totalPayment;     // مجموع کل پرداختی تا این قسط (اختیاری)
}
