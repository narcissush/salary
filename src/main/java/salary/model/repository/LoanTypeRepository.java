package salary.model.repository;

import salary.model.entity.LoanType;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanTypeRepository implements Repository<LoanType> {
    private Connection connection;
    private PreparedStatement ps;


    public LoanTypeRepository() throws Exception {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(LoanType loanType) throws Exception {
        loanType.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loans_type_seq"));
        String sql = "INSERT INTO loan_type (id, loan_type, loan_amount, loan_interest, total_installments) VALUES (?, ?, ?, ?, ?)";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, loanType.getId());
        ps.setString(2, loanType.getLoanType());
        ps.setDouble(3, loanType.getLoanAmount());
        ps.setInt(4, loanType.getLoanInterest());
        ps.setInt(5, loanType.getTotalInstallments());
        ps.executeUpdate();
    }
    public void edit(LoanType loanType) throws Exception {
        String sql = "UPDATE loan_type SET loan_type = ?, loan_amount = ?, loan_interest = ?, total_installments = ? WHERE id = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, loanType.getLoanType());
        ps.setDouble(2, loanType.getLoanAmount());
        ps.setInt(3, loanType.getLoanInterest());
        ps.setInt(4, loanType.getTotalInstallments());
        ps.setInt(5, loanType.getId());
        ps.executeUpdate();
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM loan_type WHERE id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        }

    public List<LoanType> findAll() throws Exception {
        List<LoanType> list = new ArrayList<>();
        String sql = "SELECT * FROM loan_type";
        ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(EntityMapper.loanTypeMapper(rs));
            }
        return list;
    }

    public LoanType findById(int id) throws Exception {
        String sql = "SELECT * FROM loan_type WHERE id = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return EntityMapper.loanTypeMapper(rs);
            }
        return null;
    }

    public LoanType findByType(String type) throws Exception {
        String sql = "SELECT * FROM loan_type WHERE Loan_type = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return EntityMapper.loanTypeMapper(rs);
            }
        return null;
    }

    @Override
    public void close() throws Exception {
        ps.close();
        connection.close();
    }
}
