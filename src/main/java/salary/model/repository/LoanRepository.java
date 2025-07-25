package salary.model.repository;

import salary.model.entity.Loan;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    private final Connection connection;

    public LoanRepository() throws SQLException {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(Loan loan) throws Exception {
        loan.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loans_seq"));
        String sql = "INSERT INTO LOANS VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, loan.getId());
        ps.setInt(2, loan.getEmployee().getId());
        ps.setString(3, loan.getLoanType().name());
        ps.setDouble(4, loan.getLoanAmount());
        ps.setDouble(5, loan.getLoanInterest());
        ps.setInt(6, loan.getTotalInstallments());
        ps.setDate(7, loan.getLoanStartDate() != null ? Date.valueOf(loan.getLoanStartDate()) : null);
        ps.setDate(8, loan.getLoanFinishDate() != null ? Date.valueOf(loan.getLoanFinishDate()) : null);
        ps.executeUpdate();
        ps.close();
    }

    public void edit(Loan loan) throws Exception {
        String sql = "UPDATE LOANS SET employee_id=?, loan_type=?, loan_amount=?, loan_interest=?, total_installments=?, loan_start_date=?, loan_finish_date=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, loan.getEmployee().getId());
        ps.setString(2, loan.getLoanType().name());
        ps.setDouble(3, loan.getLoanAmount());
        ps.setDouble(4, loan.getLoanInterest());
        ps.setInt(5, loan.getTotalInstallments());
        ps.setDate(6, loan.getLoanStartDate() != null ? Date.valueOf(loan.getLoanStartDate()) : null);
        ps.setDate(7, loan.getLoanFinishDate() != null ? Date.valueOf(loan.getLoanFinishDate()) : null);
        ps.setInt(8, loan.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM LOANS WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public List<Loan> findAll() throws Exception {
        String sql = "SELECT * FROM LOANS";
        List<Loan> loanList = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            loanList.add(EntityMapper.loanMapper(rs));
        }
        rs.close();
        ps.close();
        return loanList;
    }

    public Loan findById(int id) throws Exception {
        String sql = "SELECT * FROM LOANS WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Loan loan = null;
        if (rs.next()) {
            loan = EntityMapper.loanMapper(rs);
        }
        rs.close();
        ps.close();
        return loan;
    }

    public List<Loan> findByEmployeeId(int employeeId) throws Exception {
        String sql = "SELECT * FROM LOANS WHERE employee_id=?";
        List<Loan> loanList = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, employeeId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            loanList.add(EntityMapper.loanMapper(rs));
        }
        rs.close();
        ps.close();
        return loanList.isEmpty() ? null : loanList;
    }

    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
