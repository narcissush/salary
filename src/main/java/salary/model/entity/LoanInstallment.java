package salary.model.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder


public class LoanInstallment implements Serializable {
    private int id;
    private Loan loan;               // وامی که این قسط به آن مربوط است
    private Payslip payslip;         // اگر با فیش حقوقی پرداخت شده
    private double amountPaid;       // مبلغ پرداختی
    private LocalDate paymentDate;   // تاریخ پرداخت
}
