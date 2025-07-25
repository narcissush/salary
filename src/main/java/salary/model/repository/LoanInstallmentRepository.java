package salary.model.repository;

import salary.model.entity.LoanInstallment;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanInstallmentRepository{
    private final Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public LoanInstallmentRepository() throws Exception {
        this.connection = ConnectionProvider.getConnectionProvider().getconnection();
    }


    public void save(LoanInstallment loanInstallment) throws Exception {
        try {
            loanInstallment.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Loan_Installments_seq"));
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Loan_Installments VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, loanInstallment.getId());
            preparedStatement.setInt(2, loanInstallment.getLoan().getId());
            preparedStatement.setInt(3, loanInstallment.getPayslip().getId());
            preparedStatement.setDouble(4, loanInstallment.getAmountPaid());
            preparedStatement.setDate(5, loanInstallment.getPaymentDate() != null ? Date.valueOf(loanInstallment.getPaymentDate()) : null);
            preparedStatement.executeUpdate();
        } finally {
            closeResources();
        }
    }

    public void edit(LoanInstallment loanInstallment) throws Exception {
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE Loan_Installments SET Loan_id=?, Payslip_id=?, Amount_paid=?, Peyment_date=? WHERE id=?"
            );
            preparedStatement.setInt(1, loanInstallment.getLoan().getId());
            preparedStatement.setInt(2, loanInstallment.getPayslip().getId());
            preparedStatement.setDouble(3, loanInstallment.getAmountPaid());
            preparedStatement.setDate(4, loanInstallment.getPaymentDate() != null ? Date.valueOf(loanInstallment.getPaymentDate()) : null);
            preparedStatement.setInt(5, loanInstallment.getId());
            preparedStatement.executeUpdate();
        } finally {
            closeResources();
        }
    }

    public void delete(int id) throws Exception {
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Loan_Installments WHERE id=?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            closeResources();
        }
    }

    public List<LoanInstallment> findAll() throws Exception {
        List<LoanInstallment> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Loan_Installments");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(EntityMapper.loanItemMapper(resultSet));
            }
        } finally {
            closeResources();
        }
        return list;
    }

    public LoanInstallment findById(int id) throws Exception {
        LoanInstallment item = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Loan_Installments WHERE id=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                item = EntityMapper.loanItemMapper(resultSet);
            }
        } finally {
            closeResources();
        }
        return item;
    }

    public List<LoanInstallment> findByLoanId(int loanId) throws Exception {
        List<LoanInstallment> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Loan_Installments WHERE Loan_id=?");
            preparedStatement.setInt(1, loanId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(EntityMapper.loanItemMapper(resultSet));
            }
        } finally {
            closeResources();
        }
        return list;
    }

    private void closeResources() {
        try {
            if (resultSet != null) resultSet.close();
        } catch (Exception ignored) {}

        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (Exception ignored) {}
    }
}
