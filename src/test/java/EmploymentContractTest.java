import salary.controller.AppState;
import salary.model.entity.EmploymentContract;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;
import salary.model.services.EmploymentContractService;

public class EmploymentContractTest {
    public static void main(String[] args) throws Exception {


        EmploymentContract employmentContract =
                EmploymentContract.builder()
                        .id(2)
//                        .employee(EmployeeService.findById(2))
                        .issuanceDate(null)
                        .hireDate(null)
                        .terminationDate(null)
                        .contractType(ContractType.رسمی)
                        .department(Department.امور_اداري)
                        .jobTitle(JobTitle.مهندس_نرم_افزار)
                        .position(Position.رئیس)
                        .dailySalary(5_500_000)
                        .bazarKar(70_000_000)
                        .fogholadeShoghl(80_000_000)
                        .housingAllowance(9_000_000)
                        .marriageAllowance(5_000_000)
                        .childAllowance(9_316_038)
                        .foodAllowance(22_000_000)
                        .build();
        EmploymentContractService.save(employmentContract);

        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(1);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(2);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(1);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(2);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(1);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(2);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(1);

        System.out.println(AppState.employmentContractListSelected);
        AppState.employmentContractListSelected = EmploymentContractService.findByEmployeeId(2);

        System.out.println(AppState.employmentContractListSelected);
        //System.out.println(EmploymentContractService.findAll());
        //System.out.println(employmentContract);
    }
}
