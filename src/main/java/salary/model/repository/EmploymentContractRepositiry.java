package salary.model.repository;

import salary.model.entity.EmploymentContract;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploymentContractRepositiry  implements Repository<EmploymentContract> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public EmploymentContractRepositiry() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    @Override
    public void save(EmploymentContract employmentContract) throws Exception {
        employmentContract.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "Employment_Contracts_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO Employment_Contracts (" +
                        "id, employee_id, issuance_date, hire_date, termination_date, " +
                        "contract_type, department, job_title, position, " +
                        "daily_salary, bazar_kar, fogholade_shoghl, housing_allowance, " +
                        "marriage_allowance, child_allowance, food_allowance) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


        preparedStatement.setInt(1, employmentContract.getId());
        preparedStatement.setInt(2, employmentContract.getEmployee().getId());
        preparedStatement.setDate(3,(employmentContract.getIssuanceDate() != null) ? Date.valueOf(employmentContract.getIssuanceDate()) : null);
        preparedStatement.setDate(4,(employmentContract.getHireDate() != null) ? Date.valueOf(employmentContract.getHireDate()) : null);
        preparedStatement.setDate(5,(employmentContract.getTerminationDate() != null) ? Date.valueOf(employmentContract.getTerminationDate()) : null);
        preparedStatement.setString(6,employmentContract.getContractType().name());
        preparedStatement.setString(7,employmentContract.getDepartment().name());
        preparedStatement.setString(8,employmentContract.getJobTitle().name());
        preparedStatement.setString(9,employmentContract.getPosition().name());
        preparedStatement.setDouble(10, employmentContract.getDailySalary());
        preparedStatement.setDouble(11, employmentContract.getBazarKar());
        preparedStatement.setDouble(12, employmentContract.getFogholadeShoghl());
        preparedStatement.setDouble(13, employmentContract.getHousingAllowance());
        preparedStatement.setDouble(14, employmentContract.getMarriageAllowance());
        preparedStatement.setDouble(15, employmentContract.getChildAllowance());
        preparedStatement.setDouble(16, employmentContract.getFoodAllowance());
        preparedStatement.execute();

    }

    @Override
    public void edit(EmploymentContract employmentContract) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update Employment_Contracts set   employee_id=?,issuance_Date=?, hire_date=?, termination_date=?,contract_type=?, department=?, job_title=?,position=?, daily_salary=?,bazar_kar=?, fogholade_shoghl=?,housing_allowance=?, marriage_allowance=?, child_allowance=?,food_allowance=? where id=?)"
        );
        preparedStatement.setInt(1, employmentContract.getEmployee().getId());
        preparedStatement.setDate(2,(employmentContract.getIssuanceDate() != null) ? Date.valueOf(employmentContract.getIssuanceDate()) : null);
        preparedStatement.setDate(3,(employmentContract.getHireDate() != null) ? Date.valueOf(employmentContract.getHireDate()) : null);
        preparedStatement.setDate(4,(employmentContract.getTerminationDate() != null) ? Date.valueOf(employmentContract.getTerminationDate()) : null);
        preparedStatement.setString(5,employmentContract.getContractType().name());
        preparedStatement.setString(6,employmentContract.getDepartment().name());
        preparedStatement.setString(7,employmentContract.getJobTitle().name());
        preparedStatement.setString(8,employmentContract.getPosition().name());
        preparedStatement.setDouble(9, employmentContract.getDailySalary());
        preparedStatement.setDouble(10, employmentContract.getBazarKar());
        preparedStatement.setDouble(11, employmentContract.getFogholadeShoghl());
        preparedStatement.setDouble(12, employmentContract.getHousingAllowance());
        preparedStatement.setDouble(13, employmentContract.getMarriageAllowance());
        preparedStatement.setDouble(14, employmentContract.getChildAllowance());
        preparedStatement.setDouble(15, employmentContract.getFoodAllowance());
        preparedStatement.setInt(16, employmentContract.getId());

        preparedStatement.execute();


    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from Employment_Contracts where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List findAll() throws Exception {
        List<EmploymentContract> employmentContractList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Employment_Contracts");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            employmentContractList.add(EntityMapper.EmploymentContractMapper(resultSet));
        }
        return employmentContractList;    }

    @Override
    public EmploymentContract findById(int id) throws Exception {
        EmploymentContract employmentContract = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Employment_Contracts where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            employmentContract = EntityMapper.EmploymentContractMapper(resultSet);
        }
        return employmentContract;
    }

    public EmploymentContract findByEmployeeId(int employee_id) throws Exception {
        EmploymentContract employmentContract = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Employment_Contracts where employee_id=?");
        preparedStatement.setInt(1, employee_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            employmentContract = EntityMapper.EmploymentContractMapper(resultSet);
        }
        return employmentContract;
    }

    @Override
    public void close() throws Exception {

    }
}
