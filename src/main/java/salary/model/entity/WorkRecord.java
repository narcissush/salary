package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.Mission;
import salary.model.entity.Employee;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@SuperBuilder
public class WorkRecord implements Serializable {
    private int id;
    private Payslip payslip;
    private int daysWorked;         // تعداد روزهای کارکرد در ماه
    private int overtimeHours;   // ساعات اضافه‌کاری
    private int underTimeHours;    // ساعات کسری‌کار
    private int leave;    //مرخصی
    private double advance;    //مساعده
}

