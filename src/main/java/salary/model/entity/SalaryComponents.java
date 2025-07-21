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
        return AppState.workRecordMonthlySelected.getDaysWorked() * AppState.employmentContractSelected.getDailySalary();
    }

    public double getTotalChildAllowance() {
        return AppState.employeeSelected.getNumberOfChildren() *
                (AppState.employmentContractSelected.getChildAllowance() / 30 * AppState.employmentContractSelected.getDailySalary());
    }

    public double getMarriageAllowance(){
        if (AppState.employeeSelected.getMarriage()== Marriage.متاهل){
            return (AppState.employmentContractSelected.getMarriageAllowance()/30* AppState.workRecordMonthlySelected.getDaysWorked()) ;
        }else return 0.0;
    }

    public double getHousingAllowance(){
        return (AppState.employmentContractSelected.getHousingAllowance()/30 * AppState.workRecordMonthlySelected.getDaysWorked()) ;
    }

    public double getFoodAllowance(){
        return  (AppState.employmentContractSelected.getFoodAllowance()/30 * AppState.workRecordMonthlySelected.getDaysWorked()) ;
    }


    public double getOverTime(){

        String input = AppState.workRecordMonthlySelected.getOvertimeHours();
        String[] parts = input.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        double baseRate = AppState.employmentContractSelected.getDailySalary() / 8.0;
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
