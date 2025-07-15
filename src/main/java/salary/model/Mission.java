package salary.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.EmploymentContract;
import salary.model.entity.Payslip;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@SuperBuilder


public class Mission {
    private int id;
    private Payslip payslip;
    private LocalDateTime startMission;
    private LocalDateTime endMission;

    public double calculatePayment(double dailySalary) {
        double baseHourlyRate = dailySalary / 8;
        double missionPercent = 0.6;
        double overtimeMultiplier = 1.4;

        LocalTime workStart = LocalTime.of(8, 0);
        LocalTime workEnd = LocalTime.of(16, 0);

        Duration totalDuration = Duration.between(startMission, endMission);
        long totalMinutes = totalDuration.toMinutes();

        long inWorkMinutes = 0;
        long outWorkMinutes = 0;

        for (int i = 0; i < totalMinutes; i++) {
            LocalDateTime currentTime = startMission.plusMinutes(i);
            LocalTime currentLocalTime = currentTime.toLocalTime();

            if (!currentLocalTime.isBefore(workStart) && currentLocalTime.isBefore(workEnd)) {
                inWorkMinutes++;
            } else {
                outWorkMinutes++;
            }
        }

        double inWorkHours = inWorkMinutes / 60.0;
        double outWorkHours = outWorkMinutes / 60.0;

        double inWorkPay = inWorkHours * baseHourlyRate * missionPercent;
        double outWorkPay = outWorkHours * baseHourlyRate * missionPercent * overtimeMultiplier;

        return inWorkPay + outWorkPay;
    }
}
