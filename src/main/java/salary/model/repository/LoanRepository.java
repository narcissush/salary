package salary.model.repository;

import salary.model.entity.Employee;
import salary.model.entity.Loan;
import salary.model.entity.Payslip;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoanRepository implements Repository<Loan> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public LoanRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }
    @Override
    public void save(Loan loan) throws Exception {
        loan.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loan_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into LOANS values (?, ?, ?, ?, ?, ?,?)"
        );
        preparedStatement.setInt(1, loan.getId());
        preparedStatement.setInt(2, loan.getEmployee().getId());
        preparedStatement.setString(3, loan.getLoanType());
        preparedStatement.setDouble(4, loan.getLoanAmount());
        preparedStatement.setDouble(5, loan.getLoanInterest());
        preparedStatement.setInt(6, loan.getTotalInstallments());
        preparedStatement.setDate(7, (loan.getLoanStartDate() != null) ? Date.valueOf(loan.getLoanStartDate()) : null);
        preparedStatement.execute();

    }

    @Override
    public void edit(Loan loan) throws Exception {
        loan.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loan_seq"));
        preparedStatement = connection.prepareStatement(
                "update  LOANS set (Employee_id=?,loan_type=?,loan_amount=?,loan_interest=?,total_installments=?,loan_Start_Date=? where id=?)"
        );
        preparedStatement.setInt(1, loan.getEmployee().getId());
        preparedStatement.setString(2, loan.getLoanType());
        preparedStatement.setDouble(3, loan.getLoanAmount());
        preparedStatement.setDouble(4, loan.getLoanInterest());
        preparedStatement.setInt(5, loan.getTotalInstallments());
        preparedStatement.setDate(6, (loan.getLoanStartDate() != null) ? Date.valueOf(loan.getLoanStartDate()) : null);
        preparedStatement.setInt(7, loan.getId());

        preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from LOANS where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Loan> findAll() throws Exception {
        List<Loan> loanList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from LOANS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            loanList.add(EntityMapper.loanMapper(resultSet));
        }
        return loanList;
    }

    @Override
    public Loan findById(int id) throws Exception {
        Loan loan = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from LOANS where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            loan = EntityMapper.loanMapper(resultSet);
        }
        return loan;
    }

    @Override
    public void close() throws Exception {

    }
}
