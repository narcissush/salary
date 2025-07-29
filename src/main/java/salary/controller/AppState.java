package salary.controller;

import salary.model.entity.*;

import java.util.List;

public class AppState {
    public static User userSelected;
    public static Allowance allowanceSelected;
    public static Employee employeeSelected;
    public static WorkRecordMonthly workRecordMonthlySelected;
    public static Double totalLoanAmount=0.0;

    public static List<EmploymentContract> employmentContractListSelected;
    public static EmploymentContract employmentContractSelected;
    public static  int currentContractIndex ;


}
