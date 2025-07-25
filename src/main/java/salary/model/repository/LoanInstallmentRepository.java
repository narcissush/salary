package salary.model.repository;

import salary.model.entity.LoanInstallment;
import salary.model.entity.EmployeeLoan;
import salary.tools.ConnectionProvider;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanInstallmentRepository {
    private final Connection connection;

    public LoanInstallmentRepository() throws Exception {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(LoanInstallment installment) throws Exception {
        String sql = "INSERT INTO Loan_Installments (id, employee_loan_id, payslip_id, amount_paid, payment_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            installment.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Loan_Installments_seq"));
            stmt.setInt(1, installment.getId());
            stmt.setInt(2, installment.getEmployeeLoan().getId());
            stmt.setInt(3, installment.getPayslipId());
            stmt.setDouble(4, installment.getAmountPaid());
            stmt.setDate(5, Date.valueOf(installment.getPaymentDate()));
            stmt.executeUpdate();
        }
    }

    public List<LoanInstallment> findByEmployeeLoanId(int employeeLoanId) throws Exception {
        List<LoanInstallment> list = new ArrayList<>();
        String sql = "SELECT * FROM Loan_Installments WHERE employee_loan_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeLoanId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoanInstallment installment = new LoanInstallment();
                installment.setId(rs.getInt("id"));
                EmployeeLoan empLoan = new EmployeeLoan();
                empLoan.setId(rs.getInt("employee_loan_id"));
                installment.setEmployeeLoan(empLoan);
                installment.setPayslipId(rs.getInt("payslip_id"));
                installment.setAmountPaid(rs.getDouble("amount_paid"));
                installment.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                list.add(installment);
            }
        }
        return list;
    }
}
