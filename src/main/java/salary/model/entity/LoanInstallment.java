package salary.model.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder


public class LoanInstallment implements Serializable {
    private int id;
    private EmployeeLoan employeeLoan;
    private Payslip payslip; // یا Payslip payslip;
    private double amountPaid;
    private LocalDate paymentDate;

}
