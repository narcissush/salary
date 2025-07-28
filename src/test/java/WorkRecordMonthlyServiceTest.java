import salary.controller.AppState;
import salary.model.entity.WorkRecordMonthly;
import salary.model.entity.enums.Month;
import salary.model.entity.enums.Year;
import salary.model.services.EmployeeService;
import salary.model.services.WorkRecordMonthlyService;

import java.sql.Time;
import java.time.LocalTime;

public class WorkRecordMonthlyServiceTest {
    public static void main(String[] args) throws Exception {
//        AppState.employeeSelected = EmployeeService.findById(1);
        WorkRecordMonthly workRecordMonthly =
                WorkRecordMonthly.builder()
                        .id(1)
                        .employee(AppState.employeeSelected)
                        .month(Month.تیر)
                        .year(Year.Y1404)
                        .daysWorked(31)
                        .overtimeHours("05:20")
                        .underTimeHours("02:10")
                        .leave("12:20")
                        .build();
        WorkRecordMonthlyService.save(workRecordMonthly);
        System.out.println(WorkRecordMonthlyService.findById(workRecordMonthly.getId()));


        LocalTime time = LocalTime.of(12, 20);

        float hour = 1000;

        float payment = (hour/60) *  (time.getHour()*60 + time.getMinute());
    }
}
