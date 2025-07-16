import salary.model.entity.Loan;
import salary.model.entity.LoanInstallment;
import salary.model.entity.Payslip;
import salary.model.services.LoanInstallmentService;
import salary.model.services.LoanService;

import java.time.LocalDate;


public class LoanInstallmentsServiceTest {
    public static void main(String[] args)  throws Exception {
        Loan loan = new Loan();
        Payslip payslip=new Payslip();

        loan= LoanService.findById(4);
        //payslip= PaysLipService.findById(3);

        LoanInstallment loanInstallment = LoanInstallment.builder()
                .id(4)
                .loan(loan)
                .payslip(payslip)
                .amountPaid(2_000_000)
                .paymentDate(LocalDate.of(2025, 4, 1))
                .build();

        LoanInstallmentService.save(loanInstallment);
        //LoanInstallmentService.edit(loanInstallments);

        System.out.println(loanInstallment);

    }
}
