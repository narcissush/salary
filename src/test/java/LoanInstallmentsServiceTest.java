import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanInstallment;
import salary.model.entity.Payslip;
import salary.model.services.EmployeeLoanService;
import salary.model.services.LoanInstallmentService;
import salary.model.services.PaysLipService;

import java.time.LocalDate;

public class LoanInstallmentsServiceTest {
    public static void main(String[] args)  throws Exception {
        EmployeeLoan employeeloan = new EmployeeLoan();
        Payslip payslip=new Payslip();

        employeeloan= EmployeeLoanService.findById(4);
        payslip= PaysLipService.findById(1);

        LoanInstallment loanInstallment = LoanInstallment.builder()
                .id(4)
                .employeeLoan(employeeloan)
                .payslip(payslip)
                .amountPaid(2_000_000)
                .paymentDate(LocalDate.of(2025, 4, 1))
                .build();

        LoanInstallmentService.save(loanInstallment);
        //LoanInstallmentService.edit(loanInstallments);

        System.out.println(loanInstallment);

    }
}
