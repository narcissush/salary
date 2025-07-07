package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

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
}
