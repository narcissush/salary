package salary.model.repository;

import salary.model.entity.WorkRecordMonthly;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRecordRepository implements Repository<WorkRecordMonthly>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public WorkRecordRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    @Override
    public void save(WorkRecordMonthly workRecordMonthly) throws Exception {
        workRecordMonthly.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "work_Record_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into  work_Records (?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, workRecordMonthly.getId());
        preparedStatement.setInt(2, workRecordMonthly.getDaysWorked());
        preparedStatement.setString(3, workRecordMonthly.getOvertimeHours());
        preparedStatement.setString(4, workRecordMonthly.getUnderTimeHours());
        preparedStatement.setDouble(5, workRecordMonthly.getAdvance());

        preparedStatement.execute();
    }

    @Override
    public void edit(WorkRecordMonthly workRecordMonthly) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update work_Records set (days_worked=?,over_Time_Hours=?,under_Time_Hour=?,Advance=? where id=?)"
        );
        preparedStatement.setInt(1, workRecordMonthly.getDaysWorked());
        preparedStatement.setString(2, workRecordMonthly.getOvertimeHours());
        preparedStatement.setString(3, workRecordMonthly.getUnderTimeHours());
        preparedStatement.setDouble(4, workRecordMonthly.getAdvance());

        preparedStatement.setInt(5, workRecordMonthly.getId());

        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from work_Records where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<WorkRecordMonthly> findAll() throws Exception {
        List<WorkRecordMonthly> workRecordMonthlyList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Work_Records");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            workRecordMonthlyList.add(EntityMapper.workRecordMapper(resultSet));
        }
        return workRecordMonthlyList;
    }

    @Override
    public WorkRecordMonthly findById(int id) throws Exception {
        WorkRecordMonthly workRecordMonthly = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Work_Records where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            workRecordMonthly = EntityMapper.workRecordMapper(resultSet);
        }
        return workRecordMonthly;
    }

    @Override
    public void close() throws Exception {

    }
}
