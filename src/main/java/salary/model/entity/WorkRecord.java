package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.Employee;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@SuperBuilder
public class WorkRecord implements Serializable {
    private int id;
    private Payslip payslip;
    private int daysWorked;         // تعداد روزهای کارکرد در ماه
    private int overtimeHours;   // ساعات اضافه‌کاری
    private int underTimeHours;    // ساعات کسری‌کار
    private double advance;    //مساعده دریافتی
    private LocalDateTime startMission;
    private LocalDateTime endMission;
    private double Mission = missionCalculatePayment(startMission,endMission);


    public double missionCalculatePayment(LocalDateTime start, LocalDateTime end) {
        Employee employee=new Employee();
        double BASE_HOURLY_RATE = employee.getDailySalary()/8; // حقوق پایه ساعتی (مثلاً ریال)
        double MISSION_PERCENT = 0.6; // ۶۰٪ از حقوق پایه
        double OVERTIME_MULTIPLIER = 1.4;

        LocalTime WORK_START = LocalTime.of(8, 0);
        LocalTime WORK_END = LocalTime.of(16, 0);

        Duration totalDuration = Duration.between(start, end);
        long totalMinutes = totalDuration.toMinutes();

        long inWorkMinutes = 0;
        long outWorkMinutes = 0;

        for (int i = 0; i < totalMinutes; i++) {
            LocalDateTime currentTime = start.plusMinutes(i);
            LocalTime currentLocalTime = currentTime.toLocalTime();

            if (!currentLocalTime.isBefore(WORK_START) && currentLocalTime.isBefore(WORK_END)) {
                inWorkMinutes++;
            } else {
                outWorkMinutes++;
            }
        }

        double inWorkHours = inWorkMinutes / 60.0;
        double outWorkHours = outWorkMinutes / 60.0;

        // محاسبه حقوق:
        double inWorkPay = inWorkHours * BASE_HOURLY_RATE * MISSION_PERCENT;
        double outWorkPay = outWorkHours * BASE_HOURLY_RATE * MISSION_PERCENT * OVERTIME_MULTIPLIER;

        return inWorkPay + outWorkPay;


    }
}

