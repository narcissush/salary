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
        return payslip.getWorkRecordMonthly().getDaysWorked() * employmentContract.getDailySalary();
    }

    public double getTotalChildAllowance() {
        return payslip.getEmployee().getNumberOfChildren() *
                (employmentContract.getChildAllowance() / 30 * payslip.getWorkRecordMonthly().getDaysWorked());
    }

    public double getMarriageAllowance(){
        if (payslip.getEmployee().getMarried()== Married.متاهل){
            return (employmentContract.getMarriageAllowance()/30* payslip.getWorkRecordMonthly().getDaysWorked()) ;
        }else return 0.0;
    }

    public double getHousingAllowance(){
        return (employmentContract.getHousingAllowance()/30 * payslip.getWorkRecordMonthly().getDaysWorked()) ;
    }

    public double getFoodAllowance(){
        return  (employmentContract.getFoodAllowance()/30 * payslip.getWorkRecordMonthly().getDaysWorked()) ;
    }


    public double getOverTime(){

        String input = payslip.getWorkRecordMonthly().getOvertimeHours();
        String[] parts = input.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        double baseRate = employmentContract.getDailySalary() / 8.0;
        double overtimeRate = baseRate * 1.4;

        double overtimePay = hours * overtimeRate
                + (minutes / 60.0) * overtimeRate;

        return overtimePay;
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
