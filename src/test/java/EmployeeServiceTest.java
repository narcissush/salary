import salary.controller.AppState;
import salary.model.entity.Employee;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;

import java.time.LocalDate;

public class EmployeeServiceTest {
    public static void main(String[] args) throws Exception {
        Employee employee = Employee.builder()
                .id(3)
                .firstName("mohsen")
                .lastName("roshanaei")
                .nationalId("0322410762")
                .education(Education.كارشناسي)
                .married(Married.متاهل)
                .numberOfChildren(2)
                .gender(Gender.مرد)
                .birthDate(LocalDate.of(1984,04,23))
                .insuranceNumber("1489635")
                .bankAccountNumber("1111111")
                .build();
        EmployeeService.save(employee);
        //EmployeeService.edit(employee);
        AppState.employee=EmployeeService.findById(employee.getId());
        System.out.println(AppState.employee);
    }
}
