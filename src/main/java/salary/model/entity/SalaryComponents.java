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

    public double getMonthlySalary() {
        return (AppState.workRecordMonthlySelected.getDaysWorked() * AppState.employmentContractSelected.getDailySalary());

    }

    public double getTotalChildAllowance() {
        return AppState.employeeSelected.getNumberOfChildren() *
                (AppState.employmentContractSelected.getChildAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());

    }

    public double getMarriageAllowance() {
        if (AppState.employeeSelected.getMarriage() == Marriage.متاهل) {
            return (AppState.employmentContractSelected.getMarriageAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());

        } else return 0;
    }

    public double getHousingAllowance() {
        double a = (AppState.employmentContractSelected.getHousingAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());
        return (int) Math.ceil(a);

    }

    public double getFoodAllowance() {
        return (AppState.employmentContractSelected.getFoodAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());

    }


    public double getOverTime() {

        String input = AppState.workRecordMonthlySelected.getOvertimeHours();
        int hours = 0;
        int minutes = 0;

        if (input !=null & input.contains(":")){
            String[] parts = input.split(":");

            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
        }
        else{
            hours = Integer.parseInt(input);
            minutes = 0;
        }

        double overtimeRate = AppState.employmentContractSelected.getDailySalary() / 8.0 * 1.4;

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

    public double getBazarKar() {
        return AppState.employmentContractSelected.getBazarKar() / 30 * AppState.workRecordMonthlySelected.getDaysWorked();
    }

    public double getFogholadeShoghl() {
        return AppState.employmentContractSelected.getFogholadeShoghl() / 30 * AppState.workRecordMonthlySelected.getDaysWorked();
    }

    public double getTotalSalaryComponents() {
        return getMonthlySalary() +
                getTotalChildAllowance() +
                getMarriageAllowance() +
                getHousingAllowance() +
                getFoodAllowance() +
                getBazarKar() +
                getFogholadeShoghl() +
                getOverTime() +
                getMissionAllowance();
    }
}
