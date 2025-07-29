package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.Year;
import salary.model.services.AllowanceService;

@Data
@NoArgsConstructor
@SuperBuilder
public class Allowance {
    private int id;
    private int year;
    private Double housingAllowance;
    private Double marriageAllowance;
    private Double childAllowance;
    private Double foodAllowance;


}
