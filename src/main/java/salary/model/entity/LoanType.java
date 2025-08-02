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
    private String loanType;  //نام وام
    private double loanAmount;  //مبلغ وام
    private int loanInterest;  //سود وام
    private int totalInstallments;    //تعداد قسط

    public int getAmountPayMonthly() {
     return (int) ((loanAmount*loanInterest*(totalInstallments+1)/2400)+loanAmount)/60;
    }


    @Override
    public String toString() {
        return loanType; // برای نمایش در ComboBox
    }
}
