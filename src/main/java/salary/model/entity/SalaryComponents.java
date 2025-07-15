package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.model.Mission;
import salary.model.entity.enums.Married;

import java.io.Serializable;
import java.util.List;

import static salary.model.entity.EmploymentContract.*;

@Data
@NoArgsConstructor
@SuperBuilder
public class SalaryComponents implements Serializable {
    private int id;
    private Payslip payslip;
    List<Mission> missionList;


    EmploymentContract employmentContract=new EmploymentContract();

    public double getMonthlySalary(){
        return payslip.getWorkRecord().getDaysWorked() * employmentContract.getDailySalary();
    }

    public double getTotalChildAllowance() {
        return payslip.getEmployee().getNumberOfChildren() *
                (CHILD_ALLOWANCE / 30 * payslip.getWorkRecord().getDaysWorked());
    }

    public double getMarriageAllowance(){
        if (payslip.getEmployee().getMarried()== Married.متاهل){
            return (MARAGE_ALLOWANCE/30* payslip.getWorkRecord().getDaysWorked()) ;
        }else return 0.0;
    }

    public double getHousingAllowance(){
        return (HOUSING_ALLOWANCE/30 * payslip.getWorkRecord().getDaysWorked()) ;
    }

    public double getFoodAllowance(){
        return  (FOOD_ALLOWANCE/30 * payslip.getWorkRecord().getDaysWorked()) ;
    }


    public double getOverTime(){
        return employmentContract.getDailySalary()/8* 1.4 * payslip.getWorkRecord().getOvertimeHours();
    }

    public double getMissionAllowance() {
        double totalMission = 0;
        for (Mission mission : missionList) {
            if (mission.getPayslip() != null && mission.getPayslip().getId() == this.payslip.getId()) {
                totalMission += mission.calculatePayment(employmentContract.getDailySalary());
            }
        }
        return totalMission;
    }

    public double getTotalSalaryComponents(){
        return  getMonthlySalary() +
                getTotalChildAllowance() +
                getMarriageAllowance()+
                getHousingAllowance()+
                getFoodAllowance()+
                getOverTime()+
                getMissionAllowance();

    }





}
