package salary.model.repository;

import salary.model.entity.Loan;
import salary.model.entity.LoanItem;
import salary.model.entity.Payslip;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoanItemsRepository implements Repository<LoanItem> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public LoanItemsRepository() throws Exception {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }
    @Override
    public void save(LoanItem loanItem) throws Exception {
        loanItem.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loan_items_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into LOAN_ITEMS values (?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, loanItem.getId());
        preparedStatement.setInt(2, loanItem.getLoan().getId());
        preparedStatement.setInt(3, loanItem.getPayslip().getId());
        preparedStatement.setDouble(4, loanItem.getAmountPaid());
        preparedStatement.setDate(5, (loanItem.getPaymentDate() != null) ? Date.valueOf(loanItem.getPaymentDate()) : null);

        preparedStatement.execute();
            }

    @Override
    public void edit(LoanItem loanItem) throws Exception {
        loanItem.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "loan_items_seq"));
        preparedStatement = connection.prepareStatement(
                "update LOAN_ITEMS set(Loan_id=?,Payslip_id=?,Amount_paid=?,Peyment_date=? where id=? )"
        );
        preparedStatement.setInt(1, loanItem.getLoan().getId());
        preparedStatement.setInt(2, loanItem.getPayslip().getId());
        preparedStatement.setDouble(3, loanItem.getAmountPaid());
        preparedStatement.setDate(4, (loanItem.getPaymentDate() != null) ? Date.valueOf(loanItem.getPaymentDate()) : null);
        preparedStatement.setInt(5, loanItem.getId());

        preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from LOAN_ITEMS where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List findAll() throws Exception {
        List<LoanItem> loanItemList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from LOAN_ITEMS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            loanItemList.add(EntityMapper.loanItemMapper(resultSet));
        }
        return loanItemList;
    }

    @Override
    public LoanItem findById(int id) throws Exception {
        LoanItem loanItem = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from LOAN_ITEMS where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            loanItem = EntityMapper.loanItemMapper(resultSet);
        }
        return loanItem;
    }



    @Override
    public void close() throws Exception {

    }
}
