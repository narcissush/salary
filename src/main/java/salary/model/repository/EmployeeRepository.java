package salary.model.repository;

import salary.model.entity.Employee;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public EmployeeRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }


    @Override
    public void save(Employee employee) throws Exception {
        employee.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "employees_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO Employees (" +
                        "id, first_Name, last_Name, national_Id, father_name, " +
                        "certificate_Number, birth_Date, birth_Place, gender, " +
                        "education, major, marriage, number_Of_Children, " +
                        "Phone_number, insurance_Number, bank_Account_Number" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        preparedStatement.setInt(1, employee.getId());
        preparedStatement.setString(2, employee.getFirstName());
        preparedStatement.setString(3, employee.getLastName());
        preparedStatement.setString(4, employee.getNationalId());
        preparedStatement.setString(5, employee.getFatherName());
        preparedStatement.setString(6, employee.getCertificateNumber());
        preparedStatement.setDate(7, (employee.getBirthDate() != null) ? Date.valueOf(employee.getBirthDate()) : null);
        preparedStatement.setString(8, employee.getBirthPlace().name());
        preparedStatement.setString(9, employee.getGender().name());
        preparedStatement.setString(10, employee.getEducation().name());
        preparedStatement.setString(11, employee.getMajor().name());
        preparedStatement.setString(12, employee.getMarriage().name());
        preparedStatement.setInt(13, employee.getNumberOfChildren());
        preparedStatement.setString(14, employee.getPhoneNumber());
        preparedStatement.setString(15, employee.getInsuranceNumber());
        preparedStatement.setString(16, employee.getBankAccountNumber());

        preparedStatement.execute();

    }

    @Override
     public void edit(Employee employee) throws Exception {
        preparedStatement = connection.prepareStatement("UPDATE Employees SET " +
                "first_Name = ?, last_Name = ?, national_Id = ?, father_name = ?, " +
                "certificate_Number = ?, birth_Date = ?, birth_Place = ?, gender = ?, " +
                "education = ?, major = ?, marriage = ?, number_Of_Children = ?, " +
                "Phone_number = ?, insurance_Number = ?, bank_Account_Number = ? " +
                "WHERE id = ?");

        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getNationalId());
        preparedStatement.setString(4, employee.getFatherName());
        preparedStatement.setString(5, employee.getCertificateNumber());
        preparedStatement.setDate(6, (employee.getBirthDate() != null) ? Date.valueOf(employee.getBirthDate()) : null);
        preparedStatement.setString(7, employee.getBirthPlace().name());
        preparedStatement.setString(8, employee.getGender().name());
        preparedStatement.setString(9, employee.getEducation().name());
        preparedStatement.setString(10, employee.getMajor().name());
        preparedStatement.setString(11, employee.getMarriage().name());
        preparedStatement.setInt(12, employee.getNumberOfChildren());
        preparedStatement.setString(13, employee.getPhoneNumber());
        preparedStatement.setString(14, employee.getInsuranceNumber());
        preparedStatement.setString(15, employee.getBankAccountNumber());
        preparedStatement.setInt(16, employee.getId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from Employees where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<Employee> findAll() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Employees order by id");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            employeeList.add(EntityMapper.employeeMapper(resultSet));
        }
        return employeeList;
    }

    @Override
    public Employee findById(int id) throws Exception {
        Employee employee = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from EmployeeS where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            employee = EntityMapper.employeeMapper(resultSet);
        }
        return employee;
    }

    public Employee findByNationalId(String nationalId) throws Exception {
        Employee employee = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from EmployeeS where National_Id=?");
        preparedStatement.setString(1, nationalId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            employee = EntityMapper.employeeMapper(resultSet);
        }
        return employee;
    }

    @Override
    public void close() throws Exception {

    }
}
