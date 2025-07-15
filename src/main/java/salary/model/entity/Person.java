package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.Education;
import salary.model.entity.enums.Gender;
import salary.model.entity.enums.Married;
import salary.tools.DataConvert;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@SuperBuilder

public abstract class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String nationalId;
    private Education education;
    private Married married;
    private int numberOfChildren;
    private Gender gender;
    private LocalDate birthDate;


    public String getFullName(){
        return firstName + " " + lastName;
    }
    public String getFaBirthdate(){
        return DataConvert.MiladiToShamsi(birthDate).toString();
    }
    public int getAge(){
      return   Period.between(birthDate, LocalDate.now()).getYears();
    }
}


