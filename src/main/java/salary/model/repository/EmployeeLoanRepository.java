package salary.model.repository;

import salary.model.entity.EmployeeLoan;
import salary.model.entity.LoanType;
import salary.tools.ConnectionProvider;

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
            stmt.setInt(2, loan.getEmployeeId());
            stmt.setInt(3, loan.getLoanType().getId());
            stmt.setDate(4, Date.valueOf(loan.getLoanStartDate()));
            stmt.setDate(5, Date.valueOf(loan.getLoanFinishDate()));
            stmt.executeUpdate();
        }
    }

    public List<EmployeeLoan> findByEmployeeId(int employeeId) throws Exception {
        List<EmployeeLoan> list = new ArrayList<>();
        String sql = "SELECT el.*, lt.loan_type, lt.loan_amount, lt.loan_interest, lt.total_installments " +
                "FROM Employee_Loan el JOIN loan_type lt ON el.loan_type_id = lt.id WHERE employee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LoanType loanType = new LoanType();
                loanType.setId(rs.getInt("loan_type_id"));
                loanType.setLoanType(rs.getString("loan_type"));
                loanType.setLoanAmount(rs.getDouble("loan_amount"));
                loanType.setLoanInterest(rs.getDouble("loan_interest"));
                loanType.setTotalInstallments(rs.getInt("total_installments"));

                EmployeeLoan loan = new EmployeeLoan();
                loan.setId(rs.getInt("id"));
                loan.getEmployee(rs.getInt("employee_id"));
                loan.setLoanType(loanType);
                loan.setLoanStartDate(rs.getDate("loan_start_date").toLocalDate());
                loan.setLoanFinishDate(rs.getDate("loan_finish_date").toLocalDate());

                list.add(loan);
            }
        }
        return list;
    }
}
