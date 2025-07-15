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
                "INSERT INTO Employees (id, first_Name, last_Name, national_Id, education, married, number_Of_Children, gender, birth_Date, insurance_Number, bank_Account_Number, department, job_Title, position, hire_Date, termination_Date, daily_Salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        preparedStatement.setInt(1, employee.getId());
        preparedStatement.setString(2, employee.getFirstName());
        preparedStatement.setString(3, employee.getLastName());
        preparedStatement.setString(4, employee.getNationalId());
        preparedStatement.setString(5, employee.getEducation().name());
        preparedStatement.setString(6, employee.getMarried().name());
        preparedStatement.setInt(7, employee.getNumberOfChildren());
        preparedStatement.setString(8, employee.getGender().name());
        preparedStatement.setDate(9, (employee.getBirthDate() != null) ? Date.valueOf(employee.getBirthDate()) : null);
        preparedStatement.setString(10, employee.getInsuranceNumber());
        preparedStatement.setString(11, employee.getBankAccountNumber());
        preparedStatement.setString(12, employee.getDepartment().name());
        preparedStatement.setString(13, employee.getJobTitle().name());
        preparedStatement.setString(14, employee.getPosition().name());
        preparedStatement.setDate(15, (employee.getHireDate() != null) ? Date.valueOf(employee.getHireDate()) : null);
        preparedStatement.setDate(16, (employee.getTerminationDate() != null) ? Date.valueOf(employee.getTerminationDate()) : null);
        preparedStatement.setDouble(17, employee.getDailySalary());

        preparedStatement.execute();

    }

    @Override
    public void edit(Employee employee) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update employees set first_Name=?,last_Name=?,national_Id=?,education=?,married=?,number_Of_Children=?,gender=?,birth_Date=?,insurance_Number=?,bank_Account_Number=?,department=?,job_Title=?,position=?,hire_Date=?,termination_Date=?,daily_Salary=? where id=?"
        );
        preparedStatement.setString(1, employee.getFirstName());
        preparedStatement.setString(2, employee.getLastName());
        preparedStatement.setString(3, employee.getNationalId());
        preparedStatement.setString(4, employee.getEducation().name());
        preparedStatement.setString(5, employee.getMarried().name());
        preparedStatement.setInt(6, employee.getNumberOfChildren());
        preparedStatement.setString(7, employee.getGender().name());
        preparedStatement.setDate(8, (employee.getBirthDate() != null) ? Date.valueOf(employee.getBirthDate()) : null);
        preparedStatement.setString(9, employee.getInsuranceNumber());
        preparedStatement.setString(10, employee.getBankAccountNumber());
        preparedStatement.setString(11, employee.getDepartment().name());
        preparedStatement.setString(12, employee.getJobTitle().name());
        preparedStatement.setString(13, employee.getPosition().name());
        preparedStatement.setDate(14, (employee.getHireDate() != null) ? Date.valueOf(employee.getBirthDate()) : null);
        preparedStatement.setDate(15, (employee.getTerminationDate() != null) ? Date.valueOf(employee.getBirthDate()) : null);
        preparedStatement.setDouble(16, employee.getDailySalary());
        preparedStatement.setInt(17, employee.getId());
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
        preparedStatement = connection.prepareStatement("select * from Employees order by last_name");
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

    @Override
    public void close() throws Exception {

    }
}
