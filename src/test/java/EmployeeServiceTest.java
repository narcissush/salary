import salary.model.entity.Employee;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;

import java.time.LocalDate;

public class EmployeeServiceTest {
    public static void main(String[] args) throws Exception {
        Employee employee = Employee.builder()
                .id(3)
                .firstName("narges")
                .lastName("hajizadeh")
                .nationalId("0080386822")
                .education(Education.كارشناسي)
                .married(Married.متاهل)
                .numberOfChildren(2)
                .gender(Gender.زن)
                .birthDate(LocalDate.of(1986,04,21))
                .insuranceNumber("0")
                .bankAccountNumber("456789")
                .build();
        //EmployeeService.save(employee);
        EmployeeService.edit(employee);


        System.out.println(employee);
    }
}
