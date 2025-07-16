package salary.model.repository;

import salary.model.entity.*;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayslipRepository implements Repository<Payslip> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public PayslipRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    @Override
    public void save(Payslip payslip) throws Exception {
        payslip.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "payslip_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into Payslips values (?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, payslip.getId());
        preparedStatement.setInt(2, payslip.getUser().getId());
        preparedStatement.setInt(3, payslip.getEmployee().getId());
        preparedStatement.setInt(4, payslip.getWorkRecordMonthly().getId());
        preparedStatement.setDate(5, (payslip.getIssueDate() != null) ? Date.valueOf(payslip.getIssueDate()) : null);

        preparedStatement.execute();
    }

    @Override
    public void edit(Payslip payslip) throws Exception {
        payslip.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "payslip_seq"));
        preparedStatement = connection.prepareStatement(
                "update Payslips set (user_id=?,employee_id=?,work_record_id=?,issue_date=?,month=?,year=? where id=?)"
        );
        preparedStatement.setInt(1, payslip.getUser().getId());
        preparedStatement.setInt(2, payslip.getEmployee().getId());
        preparedStatement.setInt(3, payslip.getWorkRecordMonthly().getId());
        preparedStatement.setDate(4, (payslip.getIssueDate() != null) ? Date.valueOf(payslip.getIssueDate()) : null);
        preparedStatement.setInt(5, payslip.getId());
        preparedStatement.execute();

    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from Payslips where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Payslip> findAll() throws Exception {
        List<Payslip> payslipList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Payslips order by last_name");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            payslipList.add(EntityMapper.payslipMapper(resultSet));
        }
        return payslipList;
    }

    @Override
    public Payslip findById(int id) throws Exception {
        Payslip payslip = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Payslips where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            payslip = EntityMapper.payslipMapper(resultSet);
        }
        return payslip;    }

    @Override
    public void close() throws Exception {

    }
}
