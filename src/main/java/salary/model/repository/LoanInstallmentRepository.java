package salary.model.repository;

import salary.model.entity.LoanInstallment;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanInstallmentRepository implements Repository<LoanInstallment> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public LoanInstallmentRepository() throws Exception {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }
    @Override
    public void save(LoanInstallment loanInstallment) throws Exception {
        loanInstallment.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Loan_Installments_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into Loan_Installments values (?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, loanInstallment.getId());
        preparedStatement.setInt(2, loanInstallment.getLoan().getId());
        preparedStatement.setInt(3, loanInstallment.getPayslip().getId());
        preparedStatement.setDouble(4, loanInstallment.getAmountPaid());
        preparedStatement.setDate(5, (loanInstallment.getPaymentDate() != null) ? Date.valueOf(loanInstallment.getPaymentDate()) : null);

        preparedStatement.execute();
            }

    @Override
    public void edit(LoanInstallment loanInstallment) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update Loan_Installments set(Loan_id=?,Payslip_id=?,Amount_paid=?,Peyment_date=? where id=? )"
        );
        preparedStatement.setInt(1, loanInstallment.getLoan().getId());
        preparedStatement.setInt(2, loanInstallment.getPayslip().getId());
        preparedStatement.setDouble(3, loanInstallment.getAmountPaid());
        preparedStatement.setDate(4, (loanInstallment.getPaymentDate() != null) ? Date.valueOf(loanInstallment.getPaymentDate()) : null);
        preparedStatement.setInt(5, loanInstallment.getId());

        preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from Loan_Installments where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List findAll() throws Exception {
        List<LoanInstallment> loanInstallmentList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Loan_Installments");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            loanInstallmentList.add(EntityMapper.loanItemMapper(resultSet));
        }
        return loanInstallmentList;
    }

    @Override
    public LoanInstallment findById(int id) throws Exception {
        LoanInstallment loanInstallment = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Loan_Installments where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            loanInstallment = EntityMapper.loanItemMapper(resultSet);
        }
        return loanInstallment;
    }



    @Override
    public void close() throws Exception {

    }
}
