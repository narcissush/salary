package salary.model.repository;

import salary.model.entity.Employee;
import salary.model.entity.Loan;
import salary.model.entity.WorkRecord;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkRecordRepository implements Repository<WorkRecord>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public WorkRecordRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    @Override
    public void save(WorkRecord workRecord) throws Exception {
        workRecord.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "work_Record_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into  work_Records (?, ?, ?, ?, ?, ?,?)"
        );
        preparedStatement.setInt(1, workRecord.getId());
        preparedStatement.setInt(2, workRecord.getDaysWorked());
        preparedStatement.setInt(3, workRecord.getOvertimeHours());
        preparedStatement.setInt(4, workRecord.getUnderTimeHours());
        preparedStatement.setDouble(5, workRecord.getAdvance());
        preparedStatement.setTimestamp(6,workRecord.getStartMission() != null ? Timestamp.valueOf(workRecord.getStartMission()) :  null);
        preparedStatement.setTimestamp(7, (workRecord.getEndMission() != null) ? Timestamp.valueOf(workRecord.getEndMission()) : null);
        preparedStatement.execute();
    }

    @Override
    public void edit(WorkRecord workRecord) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update work_Records set (days_worked=?,over_Time_Hours=?,under_Time_Hour=?,Advance=?,Start_mission=?,End_mission=? where id=?)"
        );
        preparedStatement.setInt(1, workRecord.getDaysWorked());
        preparedStatement.setInt(2, workRecord.getOvertimeHours());
        preparedStatement.setInt(3, workRecord.getUnderTimeHours());
        preparedStatement.setDouble(4, workRecord.getAdvance());
        preparedStatement.setTimestamp(5,workRecord.getStartMission() != null ? Timestamp.valueOf(workRecord.getStartMission()) :  null);
        preparedStatement.setTimestamp(6, (workRecord.getEndMission() != null) ? Timestamp.valueOf(workRecord.getEndMission()) : null);
        preparedStatement.setInt(7, workRecord.getId());

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
    public List<WorkRecord> findAll() throws Exception {
        List<WorkRecord> workRecordList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Work_Records");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            workRecordList.add(EntityMapper.workRecordMapper(resultSet));
        }
        return workRecordList;
    }

    @Override
    public WorkRecord findById(int id) throws Exception {
        WorkRecord workRecord = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Work_Records where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            workRecord = EntityMapper.workRecordMapper(resultSet);
        }
        return workRecord;
    }

    @Override
    public void close() throws Exception {

    }
}
