import salary.model.entity.Employee;
import salary.model.entity.EmploymentContract;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;
import salary.model.services.EmploymentContractService;

import java.time.LocalDate;

public class EmploymentContractTest {
    public static void main(String[] args) throws Exception {
        Employee employee = new Employee();
        employee = EmployeeService.findById(2);
        EmploymentContract employmentContract =
                EmploymentContract.builder()
                        .id(1)
                        .employee(employee)
                        .issuancePersonnelOrderDate(Year.Y1404)
                        .startContractDate(LocalDate.of(2025, 1, 1))
                        .endContractDate(LocalDate.of(2025, 12, 31))
                        .contractType(ContractType.شش_ماهه)
                        .department(Department.امور_اداري)
                        .jobTitle(JobTitle.مهندس_نرم_افزار)
                        .position(Position.رئیس)
                        .hireDate(LocalDate.of(2025, 1, 1))
                        .terminationDate(null)
                        .dailySalary(5_500_000)
                        .bazarKar(70_000_000)
                        .fogholadeShoghl(80_000_000)
                        .housingAllowance(9_000_000)
                        .marriageAllowance(5_000_000)
                        .childAllowance(9_316_038)
                        .foodAllowance(22_000_000)
                        .build();
        EmploymentContractService.save(employmentContract);
        System.out.println(employmentContract);
    }
}
