package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public class EmployeeLoan  implements Serializable {
    private int id;
    private Employee employee;
    private LoanType loanType;
    private LocalDate loanStartDate;
    private LocalDate loanFinishDate;

    public double totalLoanAmount(){

    }

}
