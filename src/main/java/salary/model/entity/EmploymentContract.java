package salary.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.*;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@SuperBuilder

public class EmploymentContract implements Serializable {

    private int id;
    private Employee employee;
    private Year issuancePersonnelOrderDate;
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

    private double housingAllowance = 9_000_000;
    private double marriageAllowance = 5_000_000;
    private double childAllowance = 9_316_038;
    private double foodAllowance = 22_000_000;


}
