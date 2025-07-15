package salary.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.Department;
import salary.model.entity.enums.JobTitle;
import salary.model.entity.enums.Position;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@SuperBuilder

public class EmploymentContract implements Serializable {

    private int id;
    private Employee employee;
    private LocalDate startContractDate;
    private LocalDate endContractDate;
    private ContractType contractType;
    private Department department;
    private JobTitle jobTitle;
    private Position position;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private double dailySalary;

    private double bazarKar;
    private double fogholadeShoghl;

    public static final double HOUSING_ALLOWANCE = 9_000_000;
    public static final double MARAGE_ALLOWANCE = 5_000_000;
    public static final double CHILD_ALLOWANCE = 9_316_038;
    public static final double FOOD_ALLOWANCE = 22_000_000;

}
