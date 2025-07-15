import salary.model.entity.Employee;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;

import java.time.LocalDate;

public class EmployeeServiceTest {
    public static void main(String[] args) throws Exception {
        Employee employee = Employee.builder()
                .id(10)
                .firstName("narges")
                .lastName("hajizadeh")
                .nationalId("0080386822")
                .education(Education.كارشناسي)
                .married(Married.متاهل)
                .numberOfChildren(2)
                .gender(Gender.زن)
                .birthDate(LocalDate.of(1986,04,21))
                .insuranceNumber("123456")
                .bankAccountNumber("456789")
                .department(Department.امور_اداري)
                .jobTitle(JobTitle.مهندس_نرم_افزار)
                .position(Position.رئیس)
                .hireDate(LocalDate.of(2020,05,05))
                .terminationDate(null)
                .dailySalary(1800000)

                .build();
        //EmployeeService.save(employee);
        EmployeeService.edit(employee);


        System.out.println(employee);
    }
}
