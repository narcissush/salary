import salary.controller.AppState;
import salary.model.entity.Loan;
import salary.model.entity.enums.LoanType;
import salary.model.services.EmployeeService;
import salary.model.services.LoanService;

import java.time.LocalDate;

public class LoanServiceTest {
    public static void main(String[] args) throws Exception {
AppState.employeeSelected =EmployeeService.findById(1);
        Loan loan = Loan.builder()
                .id(4)
                .employee(AppState.employeeSelected)
                .loanType(LoanType.کارشناسی)
                .loanAmount(50_000_000)
                .loanInterest(10)
                .totalInstallments(5)
                .loanStartDate(LocalDate.of(2025,03,01))
                .loanFinishDate(LocalDate.of(2026,8,01))
                .build();

        LoanService.save(loan);
        //LoanService.edit(loan);

        System.out.println(LoanService.findById(loan.getId()));

    }
}
