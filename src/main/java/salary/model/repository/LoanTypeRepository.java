package salary.model.repository;

import salary.model.entity.LoanType;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

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
    public void edit(LoanType loanType) throws Exception {
        String sql = "UPDATE loan_type SET loan_type = ?, loan_amount = ?, loan_interest = ?, total_installments = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loanType.getLoanType());
            stmt.setDouble(2, loanType.getLoanAmount());
            stmt.setDouble(3, loanType.getLoanInterest());
            stmt.setInt(4, loanType.getTotalInstallments());
            stmt.setInt(5, loanType.getId()); // شرط WHERE برای شناسایی رکورد
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM loan_type WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<LoanType> findAll() throws Exception {
        List<LoanType> list = new ArrayList<>();
        String sql = "SELECT * FROM loan_type";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                list.add(EntityMapper.loanTypeMapper(rs));
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

                return EntityMapper.loanTypeMapper(rs);
            }
        }
        return null;
    }
}
