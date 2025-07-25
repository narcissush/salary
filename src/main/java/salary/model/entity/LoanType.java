package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
public class LoanType implements Serializable {
    private int id;
    private String loanType;
    private double loanAmount;
    private double loanInterest;
    private int totalInstallments;

    // constructor, getters and setters

    @Override
    public String toString() {
        return loanType; // برای نمایش در ComboBox
    }
}
