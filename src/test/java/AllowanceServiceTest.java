import salary.controller.AppState;
import salary.model.entity.Allowance;
import salary.model.entity.Loan;
import salary.model.entity.enums.LoanType;
import salary.model.entity.enums.Year;
import salary.model.services.AllowanceService;
import salary.model.services.EmployeeService;
import salary.model.services.LoanService;

import java.time.LocalDate;

public class AllowanceServiceTest {
    public static void main(String[] args) throws Exception {

        Allowance allowance = Allowance.builder()
                .id(2)
                .year(Year.Y1404)
                .housingAllowance(100.00)
                .foodAllowance(100.00)
                .marriageAllowance(100.00)
                .childAllowance(100.00)
                .build();

        AllowanceService.save(allowance);
        //LoanService.edit(loan);

        System.out.println(AllowanceService.findByYear(allowance.getYear()));

    }
}
