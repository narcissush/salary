import lombok.Data;
import salary.controller.AppState;
import salary.model.entity.Employee;
import salary.model.entity.enums.*;
import salary.model.services.EmployeeService;

import java.time.LocalDate;

public class EmployeeServiceTest {
    public static void main(String[] args) throws Exception {
        Employee employee = Employee.builder()

                .id(6)
                .firstName("نرگس")
                .lastName("حاجی زاده")
                .nationalId("0080386822")
                .fatherName("علی")
                .certificateNumber("17016")
                .birthDate(LocalDate.of(1986,04,21))
                .birthPlace(City.تهران)
                .gender(Gender.زن)
                .education(Education.كارشناسي)
                .major(Major.نرم_افزار_کامپیوتر)
                .marriage(Marriage.متاهل)
                .numberOfChildren(1)
                .phoneNumber("09128179120")
                .insuranceNumber("111111")
                .bankAccountNumber("222222")
                .build();
        //EmployeeService.save(employee);
        EmployeeService.edit(employee);
        AppState.employee=EmployeeService.findById(employee.getId());
        System.out.println(AppState.employee);
    }
}
