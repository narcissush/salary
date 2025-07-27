package salary.model.repository;

import salary.model.entity.WorkRecordMonthly;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRecordMonthlyRepository implements Repository<WorkRecordMonthly>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public WorkRecordMonthlyRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    @Override
    public void save(WorkRecordMonthly workRecordMonthly) throws Exception {
        workRecordMonthly.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "work_Record_monthly_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into  work_Record_monthly values  (?, ?, ?, ?,?,?, ?,?)"
        );
        preparedStatement.setInt(1, workRecordMonthly.getId());
        preparedStatement.setInt(2,workRecordMonthly.getEmployee().getId());
        preparedStatement.setString(3, workRecordMonthly.getMonth().name());
        preparedStatement.setString(4, workRecordMonthly.getYear().name());
        preparedStatement.setInt(5, workRecordMonthly.getDaysWorked());
        preparedStatement.setString(6, workRecordMonthly.getOvertimeHours());
        preparedStatement.setString(7, workRecordMonthly.getUnderTimeHours());
        preparedStatement.setString(8, workRecordMonthly.getLeave());
        preparedStatement.execute();
    }

    @Override
    public void edit(WorkRecordMonthly workRecordMonthly) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update work_Record_monthly set (employee_id=?,month=?,year=?,days_worked=?,over_Time_Hours=?,under_Time_Hour=?,Leave=? where id=?)"
        );
        preparedStatement.setInt(1,workRecordMonthly.getEmployee().getId());
        preparedStatement.setString(2, workRecordMonthly.getMonth().name());
        preparedStatement.setString(3, workRecordMonthly.getYear().name());
        preparedStatement.setInt(4, workRecordMonthly.getDaysWorked());
        preparedStatement.setString(5, workRecordMonthly.getOvertimeHours());
        preparedStatement.setString(6, workRecordMonthly.getUnderTimeHours());
        preparedStatement.setString(7, workRecordMonthly.getLeave());
        preparedStatement.setInt(8, workRecordMonthly.getId());

        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from work_Record_monthly where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<WorkRecordMonthly> findAll() throws Exception {
        List<WorkRecordMonthly> workRecordMonthlyList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from work_Record_monthly");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            workRecordMonthlyList.add(EntityMapper.workRecordMonthlyMapper(resultSet));
        }
        return workRecordMonthlyList;
    }

    @Override
    public WorkRecordMonthly findById(int id) throws Exception {
        WorkRecordMonthly workRecordMonthly = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from work_Record_monthly where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            workRecordMonthly = EntityMapper.workRecordMonthlyMapper(resultSet);
        }
        return workRecordMonthly;
    }

    @Override
    public void close() throws Exception {

    }
}
