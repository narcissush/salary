package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Data
@NoArgsConstructor
@SuperBuilder
public class WorkRecordMonthly implements Serializable {
    private int id;
    private Payslip payslip;
    private int daysWorked;         // تعداد روزهای کارکرد در ماه
    private String overtimeHours;   // ساعات اضافه‌کاری
    private String underTimeHours;    // ساعات کسری‌کار
    private int leave;    //مرخصی
    private double advance;    //مساعده
}

