package salary.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import salary.controller.AppState;
import salary.model.entity.enums.Marriage;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class SalaryComponents implements Serializable {

    List<Mission> missionList;

    public double getMonthlySalary(){
        return AppState.workRecordMonthly.getDaysWorked() * AppState.employmentContract.getDailySalary();
    }

    public double getTotalChildAllowance() {
        return AppState.employee.getNumberOfChildren() *
                (AppState.employmentContract.getChildAllowance() / 30 * AppState.employmentContract.getDailySalary());
    }

    public double getMarriageAllowance(){
        if (AppState.employee.getMarriage()== Marriage.متاهل){
            return (AppState.employmentContract.getMarriageAllowance()/30* AppState.workRecordMonthly.getDaysWorked()) ;
        }else return 0.0;
    }

    public double getHousingAllowance(){
        return (AppState.employmentContract.getHousingAllowance()/30 * AppState.workRecordMonthly.getDaysWorked()) ;
    }

    public double getFoodAllowance(){
        return  (AppState.employmentContract.getFoodAllowance()/30 * AppState.workRecordMonthly.getDaysWorked()) ;
    }


    public double getOverTime(){

        String input = AppState.workRecordMonthly.getOvertimeHours();
        String[] parts = input.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        double baseRate = AppState.employmentContract.getDailySalary() / 8.0;
        double overtimeRate = baseRate * 1.4;

        double overtimePay = hours * overtimeRate
                + (minutes / 60.0) * overtimeRate;

        return overtimePay;
    }

    public double getMissionAllowance() {
//        double totalMission = 0;
//        for (Mission mission : missionList) {
//            if (mission.getPayslip() != null && mission.getPayslip().getId() == this.payslip.getId()) {
//                totalMission += mission.calculatePayment(AppState.employmentContract.getDailySalary());
//            }
//        }
        return 100_000;
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
