package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.entity.enums.*;
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
    private String fatherName;
    private String certificateNumber;
    private LocalDate birthDate;
    private City birthPlace;
    private Gender gender;
    private Education education;
    private Major major;
    private Marriage marriage;
    private int numberOfChildren;
    private String phoneNumber;


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


