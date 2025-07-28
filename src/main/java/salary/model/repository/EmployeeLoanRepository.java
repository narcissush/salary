package salary.model.repository;

import salary.controller.AppState;
import salary.model.entity.Employee;
import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanType;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeLoanRepository implements Repository<EmployeeLoan>, AutoCloseable {
    private Connection connection;
    private PreparedStatement ps;

    public EmployeeLoanRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }


    public void save(EmployeeLoan loan) throws Exception {
        loan.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Employee_Loan_seq"));

        String sql = "INSERT INTO Employee_Loan (id, employee_id, loan_type_id, loan_start_date, loan_finish_date) VALUES (?, ?, ?, ?, ?)";
        ps = connection.prepareStatement(sql);
            ps.setInt(1, loan.getId());
            ps.setInt(2, AppState.employeeSelected.getId());
            ps.setInt(3, loan.getLoanType().getId());
            ps.setDate(4, Date.valueOf(loan.getLoanStartDate()));
            ps.setDate(5, Date.valueOf(loan.getLoanFinishDate()));
            ps.executeUpdate();
        }

    public void edit(EmployeeLoan loan) throws Exception {
        String sql = "UPDATE Employee_Loan SET employee_id = ?, loan_type_id = ?, loan_start_date = ?, loan_finish_date = ? WHERE id = ?";
        ps = connection.prepareStatement(sql);

        ps.setInt(1, AppState.employeeSelected.getId());
            ps.setInt(2, loan.getLoanType().getId());
            ps.setDate(3, Date.valueOf(loan.getLoanStartDate()));
            ps.setDate(4, Date.valueOf(loan.getLoanFinishDate()));
            ps.setInt(5, loan.getId());
            ps.executeUpdate();
        }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Employee_Loan WHERE id = ?";
        ps = connection.prepareStatement(sql);

        ps.setInt(1, id);
            ps.executeUpdate();

    }

    public EmployeeLoan findById(int id) throws Exception {
        String sql = "SELECT * FROM Employee_Loan WHERE id=?";
        ps = connection.prepareStatement(sql);

        ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return EntityMapper.employeeLoanMapper(rs);
                }
            }


        return null;
    }

    public List<EmployeeLoan> findAll() throws Exception {
        List<EmployeeLoan> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee_Loan";
        ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(EntityMapper.employeeLoanMapper(rs));
            }

        return list;
    }

    public List<EmployeeLoan> findByEmployeeId(int employeeId) throws Exception {
        List<EmployeeLoan> list = new ArrayList<>();
        String sql =
                "SELECT el.id, el.employee_id, el.loan_start_date, el.loan_finish_date, " +
                        "lt.id AS lt_id, lt.loan_type, lt.loan_amount, lt.loan_interest, lt.total_installments " +
                        "FROM Employee_Loan el " +
                        "JOIN Loan_Type lt ON el.loan_type_id = lt.id " +
                        "WHERE el.employee_id = ?";

        ps = connection.prepareStatement(sql);

        ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(EntityMapper.employeeLoanMapper(rs));
            }

        return list;
    }

    @Override
    public void close() throws Exception {
        ps.close();
        connection.close();
    }
}
