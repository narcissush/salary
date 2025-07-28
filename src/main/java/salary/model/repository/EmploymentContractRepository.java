package salary.model.repository;

import salary.model.entity.EmployeeLoan;
import salary.model.entity.EmploymentContract;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploymentContractRepository implements Repository<EmploymentContract>, AutoCloseable {
    private Connection connection;
    private PreparedStatement ps;

    public EmploymentContractRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(EmploymentContract contract) throws Exception {

        int newId = ConnectionProvider.getConnectionProvider().getNextId(connection, "Employment_Contracts_seq");
        contract.setId(newId);

        String sql = "INSERT INTO Employment_Contracts (" +
                "id, employee_id, issuance_date, hire_date, termination_date, " +
                "contract_type, department, job_title, position, " +
                "daily_salary, bazar_kar, fogholade_shoghl, housing_allowance, " +
                "marriage_allowance, child_allowance, food_allowance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(sql);
        ps.setInt(1, newId);
        ps.setInt(2, contract.getEmployee().getId());
        ps.setDate(3, toSqlDate(contract.getIssuanceDate()));
        ps.setDate(4, toSqlDate(contract.getHireDate()));
        ps.setDate(5, toSqlDate(contract.getTerminationDate()));
        ps.setString(6, contract.getContractType().name());
        ps.setString(7, contract.getDepartment().name());
        ps.setString(8, contract.getJobTitle().name());
        ps.setString(9, contract.getPosition().name());
        ps.setDouble(10, contract.getDailySalary());
        ps.setDouble(11, contract.getBazarKar());
        ps.setDouble(12, contract.getFogholadeShoghl());
        ps.setDouble(13, contract.getHousingAllowance());
        ps.setDouble(14, contract.getMarriageAllowance());
        ps.setDouble(15, contract.getChildAllowance());
        ps.setDouble(16, contract.getFoodAllowance());

        ps.executeUpdate();
    }


public void edit(EmploymentContract contract) throws Exception {
    String sql = "UPDATE Employment_Contracts SET " +
            "employee_id=?, issuance_date=?, hire_date=?, termination_date=?, " +
            "contract_type=?, department=?, job_title=?, position=?, " +
            "daily_salary=?, bazar_kar=?, fogholade_shoghl=?, housing_allowance=?, " +
            "marriage_allowance=?, child_allowance=?, food_allowance=? " +
            "WHERE id=?";

    ps = connection.prepareStatement(sql);

    ps.setInt(1, contract.getEmployee().getId());
        ps.setDate(2, toSqlDate(contract.getIssuanceDate()));
        ps.setDate(3, toSqlDate(contract.getHireDate()));
        ps.setDate(4, toSqlDate(contract.getTerminationDate()));
        ps.setString(5, contract.getContractType().name());
        ps.setString(6, contract.getDepartment().name());
        ps.setString(7, contract.getJobTitle().name());
        ps.setString(8, contract.getPosition().name());
        ps.setDouble(9, contract.getDailySalary());
        ps.setDouble(10, contract.getBazarKar());
        ps.setDouble(11, contract.getFogholadeShoghl());
        ps.setDouble(12, contract.getHousingAllowance());
        ps.setDouble(13, contract.getMarriageAllowance());
        ps.setDouble(14, contract.getChildAllowance());
        ps.setDouble(15, contract.getFoodAllowance());
        ps.setInt(16, contract.getId());

        ps.executeUpdate();
    }

public void delete(int id) throws Exception {
    String sql = "DELETE FROM Employment_Contracts WHERE id=?";
    ps = connection.prepareStatement(sql);
    ps.setInt(1, id);
        ps.executeUpdate();

}

public List<EmploymentContract> findAll() throws Exception {
    List<EmploymentContract> list = new ArrayList<>();
    String sql = "SELECT * FROM Employment_Contracts";
    ps = connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(EntityMapper.EmploymentContractMapper(rs));
        }
    return list;
}

public EmploymentContract findById(int id) throws Exception {
    String sql = "SELECT * FROM Employment_Contracts WHERE id=?";
    ps = connection.prepareStatement(sql);
    ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return EntityMapper.EmploymentContractMapper(rs);
            }
    return null;
}

public List<EmploymentContract> findByEmployeeId(int employeeId) throws Exception {
    String sql = "SELECT * FROM Employment_Contracts WHERE employee_id=?";
    ps = connection.prepareStatement(sql);

    ps.setInt(1, employeeId);
        List<EmploymentContract> list = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(EntityMapper.EmploymentContractMapper(rs));
            }
        }
        return list.isEmpty() ? null : list;
    }


    private Date toSqlDate(java.time.LocalDate date) {
        return (date != null) ? Date.valueOf(date) : null;
    }
@Override
public void close() throws Exception {
    ps.close();
    connection.close();
}
}
