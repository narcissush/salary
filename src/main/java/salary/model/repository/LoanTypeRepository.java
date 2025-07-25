package salary.model.repository;

import salary.model.entity.LoanType;
import salary.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanTypeRepository {
    private final Connection connection;

    public LoanTypeRepository() throws Exception {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(LoanType loanType) throws Exception {
        String sql = "INSERT INTO loan_type (id, loan_type, loan_amount, loan_interest, total_installments) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            loanType.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loans_type_seq"));
            stmt.setInt(1, loanType.getId());
            stmt.setString(2, loanType.getLoanType());
            stmt.setDouble(3, loanType.getLoanAmount());
            stmt.setDouble(4, loanType.getLoanInterest());
            stmt.setInt(5, loanType.getTotalInstallments());
            stmt.executeUpdate();
        }
    }

    public List<LoanType> findAll() throws Exception {
        List<LoanType> list = new ArrayList<>();
        String sql = "SELECT * FROM loan_type";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LoanType loanType = new LoanType();
                loanType.setId(rs.getInt("id"));
                loanType.setLoanType(rs.getString("loan_type"));
                loanType.setLoanAmount(rs.getDouble("loan_amount"));
                loanType.setLoanInterest(rs.getDouble("loan_interest"));
                loanType.setTotalInstallments(rs.getInt("total_installments"));
                list.add(loanType);
            }
        }
        return list;
    }

    public LoanType findById(int id) throws Exception {
        String sql = "SELECT * FROM loan_type WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LoanType loanType = new LoanType();
                loanType.setId(rs.getInt("id"));
                loanType.setLoanType(rs.getString("loan_type"));
                loanType.setLoanAmount(rs.getDouble("loan_amount"));
                loanType.setLoanInterest(rs.getDouble("loan_interest"));
                loanType.setTotalInstallments(rs.getInt("total_installments"));
                return loanType;
            }
        }
        return null;
    }
}
