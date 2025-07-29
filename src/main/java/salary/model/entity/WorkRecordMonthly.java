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
    private int year;
    private int daysWorked;
    private String overtimeHours;
    private String underTimeHours;
    private String leave;

}

