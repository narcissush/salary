package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.JobTitle;
import salary.model.entity.enums.Position;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder

public class Employee extends Person implements Serializable {
    private int id;
    private String insuranceNumber;
    private String bankAccountNumber;
    private JobTitle jobTitle;
    private Position position;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private double dailySalary;
}
