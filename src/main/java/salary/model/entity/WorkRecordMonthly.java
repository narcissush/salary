package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.Month;
import salary.model.entity.enums.Year;

import java.io.Serializable;


@Data
@NoArgsConstructor
@SuperBuilder
public class WorkRecordMonthly implements Serializable {
    private int id;
    private Employee employee;
    private Month month;
    private Year year;
    private int daysWorked;         // تعداد روزهای کارکرد در ماه
    private String overtimeHours;   // ساعات اضافه‌کاری
    private String underTimeHours;    // ساعات کسری‌کار
    private String leave;    //مرخصی
    private double advance;    //مساعده
}

