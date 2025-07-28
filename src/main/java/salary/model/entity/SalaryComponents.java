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
        int a = (int) (AppState.workRecordMonthlySelected.getDaysWorked() * AppState.employmentContractSelected.getDailySalary());
        System.out.println(a);
        return a;
    }

    public double getTotalChildAllowance() {
        double a = AppState.employeeSelected.getNumberOfChildren() *
                (AppState.employmentContractSelected.getChildAllowance() / 30 * AppState.employmentContractSelected.getDailySalary());
        return (int) Math.ceil(a);
    }

    public double getMarriageAllowance() {
        if (AppState.employeeSelected.getMarriage() == Marriage.متاهل) {
            double a = (AppState.employmentContractSelected.getMarriageAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());
            return (int) Math.ceil(a);

        } else return 0.0;
    }

    public double getHousingAllowance() {
        double a = (AppState.employmentContractSelected.getHousingAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());
        return (int) Math.ceil(a);

    }

    public double getFoodAllowance() {
        double a = (AppState.employmentContractSelected.getFoodAllowance() / 30 * AppState.workRecordMonthlySelected.getDaysWorked());
        return (int) Math.ceil(a);

    }


    public double getOverTime() {

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

    public double getTotalSalaryComponents() {
        return getMonthlySalary() +
                getTotalChildAllowance() +
                getMarriageAllowance() +
                getHousingAllowance() +
                getFoodAllowance() +
                getOverTime();
        //           getMissionAllowance();
    }
}
