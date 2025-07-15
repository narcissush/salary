import salary.model.entity.Employee;
import salary.model.entity.Loan;
import salary.model.entity.enums.LoanType;
import salary.model.services.EmployeeService;
import salary.model.services.LoanService;

import java.time.LocalDate;

public class LoanServiceTest {
    public static void main(String[] args) throws Exception {
        Employee employee = new Employee();
        employee=EmployeeService.findById(3);
        Loan loan = Loan.builder()
                .id(4)
                .employee(employee)
                .loanType(LoanType.مساعده_اقساطی)
                .loanAmount(50_000_000)
                .loanInterest(0)
                .totalInstallments(12)
                .loanStartDate(LocalDate.of(2025,03,01))
                .loanFinishDate(LocalDate.of(2026,02,01))
                .build();

        //LoanService.save(loan);
        LoanService.edit(loan);

        System.out.println(loan);

    }
}
