package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

import static salary.model.entity.EmployeeAllowances.*;

@Data
@NoArgsConstructor
@SuperBuilder
public class SalaryComponents implements Serializable {
    private int id;
    private Payslip payslip;

    public double getMonthlySalary(){
        return payslip.getWorkRecord().getDaysWorked()* payslip.getEmployee().getDailySalary();
    }

    public double getChildAllowancePerChild(){
       return payslip.getEmployee().getNumberOfChildren() * (CHILD_ALLOWANCE/30 * payslip.getWorkRecord().getDaysWorked()) ;
    }
    public double getMarriageAllowance(){
        if (payslip.getEmployee().isMarried()){
            return (MARAGE_ALLOWANCE/30* payslip.getWorkRecord().getDaysWorked()) ;
        }else return 0.0;
    }

    public double getHousingAllowance(){
        return (HOUSING_ALLOWANCE/30 * payslip.getWorkRecord().getDaysWorked()) ;
    }

    public double getFoodAllowance(){
        return  (FOOD_ALLOWANCE/30 * payslip.getWorkRecord().getDaysWorked()) ;
    }

    public double getTransportAllowance(){
        return (TRANSPORT_ALLOWANCE/30 * payslip.getWorkRecord().getDaysWorked()) ;
    }

    public double getOverTime(){
        return payslip.getEmployee().getDailySalary()/8* 1.4 * payslip.getWorkRecord().getOvertimeHours();
    }

    public double getTotalSalaryComponents(){
        return  getMonthlySalary() +
                getChildAllowancePerChild() +
                getMarriageAllowance()+
                getHousingAllowance()+
                getFoodAllowance()+
                getTransportAllowance()+
                getOverTime();

    }





}
