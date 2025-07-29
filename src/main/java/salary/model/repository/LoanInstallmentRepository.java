package salary.model.repository;

import salary.model.entity.LoanInstallment;
import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanType;
import salary.model.entity.Payslip;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanInstallmentRepository implements Repository<LoanInstallment> {
    private Connection connection;
    private PreparedStatement ps;

    public LoanInstallmentRepository() throws Exception {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(LoanInstallment installment) throws Exception {
        installment.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Loan_Installments_seq"));
        String sql = "INSERT INTO Loan_Installments (id, employee_loan_id, payslip_id, amount_paid, payment_date) VALUES (?, ?, ?, ?, ?)";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, installment.getId());
        ps.setInt(2, installment.getEmployeeLoan().getId());
        ps.setInt(3, installment.getPayslip().getId());
        ps.setDouble(4, installment.getAmountPaid());
        ps.setDate(5, Date.valueOf(installment.getPaymentDate()));
        ps.executeUpdate();

    }

    public void edit(LoanInstallment installment) throws Exception {
        String sql = "UPDATE Loan_Installments SET employee_loan_id = ?, payslip_id = ?, amount_paid = ?, payment_date = ? WHERE id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, installment.getEmployeeLoan().getId());
        ps.setInt(2, installment.getPayslip().getId());
        ps.setDouble(3, installment.getAmountPaid());
        ps.setDate(4, Date.valueOf(installment.getPaymentDate()));
        ps.setInt(5, installment.getId()); // شرط WHERE

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new Exception("به‌روزرسانی انجام نشد. رکوردی با این شناسه وجود ندارد.");

        }
    }

    public void delete(int installmentId) throws Exception {
        String sql = "DELETE FROM Loan_Installments WHERE id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, installmentId);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new Exception("حذفی انجام نشد. رکوردی با این شناسه وجود ندارد.");
        }

    }

    public LoanInstallment findById(int id) throws Exception {
        List<LoanInstallment> list = new ArrayList<>();
        String sql = "SELECT * FROM Loan_Installments WHERE employee_loan_id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return (EntityMapper.loanInstallmentMapper(rs));
        }

        return null;
    }

    public List<LoanInstallment> findAll() throws Exception {
        List<LoanInstallment> list = new ArrayList<>();
        String sql = "SELECT * FROM Loan_Installments";
        ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(EntityMapper.loanInstallmentMapper(rs));
        }

        return list;
    }


    public List<LoanInstallment> findByEmployeeLoanId(int employeeLoanId) throws Exception {
        List<LoanInstallment> list = new ArrayList<>();
        String sql = "SELECT * FROM Loan_Installments WHERE employee_loan_id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, employeeLoanId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(EntityMapper.loanInstallmentMapper(rs));
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        ps.close();
        connection.close();
    }
}
