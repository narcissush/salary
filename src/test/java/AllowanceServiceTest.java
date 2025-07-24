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
                .id(1)
                .year(Year.Y1404)
                .housingAllowance(9000000.00)
                .foodAllowance(22000000.00)
                .marriageAllowance(5000000.00)
                .childAllowance(8700000.00)
                .build();

       // AllowanceService.save(allowance);
        AllowanceService.edit(allowance);

        System.out.println(AllowanceService.findByYear(allowance.getYear()));

    }
}
