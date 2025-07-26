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

public class EmployeeLoanRepository {
    private final Connection connection;

    public EmployeeLoanRepository() throws Exception {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(EmployeeLoan loan) throws Exception {
        String sql = "INSERT INTO Employee_Loan (id, employee_id, loan_type_id, loan_start_date, loan_finish_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            loan.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Employee_Loan_seq"));
            stmt.setInt(1, loan.getId());
            stmt.setInt(2, AppState.employeeSelected.getId());
            stmt.setInt(3, loan.getLoanType().getId());
            stmt.setDate(4, Date.valueOf(loan.getLoanStartDate()));
            stmt.setDate(5, Date.valueOf(loan.getLoanFinishDate()));
            stmt.executeUpdate();
        }
    }

    public void edit(EmployeeLoan loan) throws Exception {
        String sql = "UPDATE Employee_Loan SET employee_id = ?, loan_type_id = ?, loan_start_date = ?, loan_finish_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, AppState.employeeSelected.getId());
            stmt.setInt(2, loan.getLoanType().getId());
            stmt.setDate(3, Date.valueOf(loan.getLoanStartDate()));
            stmt.setDate(4, Date.valueOf(loan.getLoanFinishDate()));
            stmt.setInt(5, loan.getId()); // شناسه رکورد برای به‌روزرسانی
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Employee_Loan WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public EmployeeLoan findById(int id) throws Exception {
        String sql = "SELECT * FROM Employee_Loan WHERE id=?";
        try (
                Connection connection = ConnectionProvider.getConnectionProvider().getconnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return EntityMapper.employeeLoanMapper(rs);
                }
            }
        }

        return null;
    }

    public List<EmployeeLoan> findAll() throws Exception {
        List<EmployeeLoan> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee_Loan";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(EntityMapper.employeeLoanMapper(rs));
            }
        }
        return list;
    }

    public List<EmployeeLoan> findByEmployeeId(int employeeId) throws Exception {
        List<EmployeeLoan> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee_Loan  WHERE employee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(EntityMapper.employeeLoanMapper(rs));
            }
        }
        return list;
    }
}
