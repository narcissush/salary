import salary.model.entity.Employee;
import salary.model.entity.Payslip;
import salary.model.entity.User;
import salary.model.entity.WorkRecordMonthly;
import salary.model.entity.enums.Month;
import salary.model.entity.enums.Year;
import salary.model.services.EmployeeService;
import salary.model.services.PaysLipService;
import salary.model.services.UserService;
import salary.model.services.WorkRecordMonthlyService;

import java.time.LocalDate;

public class PaysLipServiceTest {
    public static void main(String[] args) throws Exception {
        User user=new User();
        Employee employee=new Employee();
        WorkRecordMonthly workRecordMonthly=new WorkRecordMonthly();

        user= UserService.findById(1);
        employee= EmployeeService.findById(1);
        workRecordMonthly= WorkRecordMonthlyService.findById(1);

        WorkRecordMonthly workRecord=new WorkRecordMonthly();
        Payslip payslip=Payslip.builder()
                .id(1)
                .user(user)
                .employee(employee)
                .workRecordMonthly(workRecord)
                .issueDate(LocalDate.of(2025,07,16))
                .build();
        PaysLipService.save(payslip);
        System.out.println(payslip);
    }
}
