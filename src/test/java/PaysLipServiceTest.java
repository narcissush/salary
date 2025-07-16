import salary.model.entity.Employee;
import salary.model.entity.Payslip;
import salary.model.entity.User;
import salary.model.entity.WorkRecordMonthly;
import salary.model.entity.enums.Month;
import salary.model.entity.enums.Year;
import salary.model.services.PaysLipService;

import java.time.LocalDate;

public class PaysLipServiceTest {
    public static void main(String[] args) throws Exception {
        User user=new User();
        Employee employee=new Employee();
        WorkRecordMonthly workRecord=new WorkRecordMonthly();
        Payslip payslip=Payslip.builder()
                .id(1)
                .user(user)
                .employee(employee)
                .workRecordMonthly(workRecord)
                .issueDate(LocalDate.of(2025,07,16))
                .month(Month.تیر)
                .year(Year.Y1404)
                .build();
        PaysLipService.save(payslip);
        System.out.println(payslip);
    }
}
