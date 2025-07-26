import salary.controller.AppState;
import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanType;
import salary.model.services.EmployeeLoanService;
import salary.model.services.EmployeeService;

import java.time.LocalDate;

public class EmployeeLoanServiceTest {
    public static void main(String[] args) throws Exception {
AppState.employeeSelected = EmployeeService.findById(1);
        LoanType loanType=new LoanType();
        EmployeeLoan employeeloan = EmployeeLoan.builder()
                .id(1)
                .employee(AppState.employeeSelected)
                .loanType(loanType)
                .loanStartDate(LocalDate.of(2025,03,01))
                .loanFinishDate(LocalDate.of(2026,8,01))
                .build();

        EmployeeLoanService.save(employeeloan);
        EmployeeLoanService.edit(employeeloan);

        System.out.println(EmployeeLoanService.findById(employeeloan.getId()));

    }
}
