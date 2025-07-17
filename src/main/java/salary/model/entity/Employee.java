package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.Department;
import salary.model.entity.enums.JobTitle;
import salary.model.entity.enums.Position;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder

public class Employee extends Person implements Serializable {
    private int id;
    private String insuranceNumber;
    private String bankAccountNumber;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                "} " + super.toString();
    }
    public String getFullName() {
        return super.getFullName();
    }

}
