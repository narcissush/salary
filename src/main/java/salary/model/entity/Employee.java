package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder

public class Employee extends Person implements Serializable {
    private int id;
    private String insuranceNumber;
    private String bankAccountNumber;
    private String jobTitle;
    private String position;
    private String group;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private double dailySalary;
}
