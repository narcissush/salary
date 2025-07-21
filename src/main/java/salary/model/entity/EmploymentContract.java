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
    private LocalDate issuanceDate;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private ContractType contractType;
    private Department department;
    private JobTitle jobTitle;
    private Position position;

    private double dailySalary;

    private double bazarKar;
    private double fogholadeShoghl;

    private double housingAllowance;
    private double marriageAllowance;
    private double childAllowance;
    private double foodAllowance;

    private String notes;


}
