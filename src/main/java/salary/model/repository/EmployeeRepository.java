package salary.model.repository;

import salary.model.entity.Employee;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements Repository<Employee>, AutoCloseable {
    private Connection connection;
    private PreparedStatement ps;

    public EmployeeRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public void save(Employee employee) throws Exception {
        String sql = "INSERT INTO Employees (" +
                "id, first_Name, last_Name, national_Id, father_name, " +
                "certificate_Number, birth_Date, birth_Place, gender, " +
                "education, major, marriage, number_Of_Children, " +
                "Phone_number, insurance_Number, bank_Account_Number" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int newId = ConnectionProvider.getConnectionProvider().getNextId(connection, "employees_seq");
        employee.setId(newId);

        ps = connection.prepareStatement(sql);
        ps.setInt(1, newId);
        ps.setString(2, employee.getFirstName());
        ps.setString(3, employee.getLastName());
        ps.setString(4, employee.getNationalId());
        ps.setString(5, employee.getFatherName());
        ps.setString(6, employee.getCertificateNumber());
        ps.setDate(7, Date.valueOf(employee.getBirthDate()));
        ps.setString(8, employee.getBirthPlace().name());
        ps.setString(9, employee.getGender().name());
        ps.setString(10, employee.getEducation().name());
        ps.setString(11, employee.getMajor().name());
        ps.setString(12, employee.getMarriage().name());
        ps.setInt(13, employee.getNumberOfChildren());
        ps.setString(14, employee.getPhoneNumber());
        ps.setString(15, employee.getInsuranceNumber());
        ps.setString(16, employee.getBankAccountNumber());

        ps.executeUpdate();
    }


    public void edit(Employee employee) throws Exception {
        String sql = "UPDATE Employees SET " +
                "first_Name = ?, last_Name = ?, national_Id = ?, father_name = ?, " +
                "certificate_Number = ?, birth_Date = ?, birth_Place = ?, gender = ?, " +
                "education = ?, major = ?, marriage = ?, number_Of_Children = ?, " +
                "Phone_number = ?, insurance_Number = ?, bank_Account_Number = ? " +
                "WHERE id = ?";

        ps = connection.prepareStatement(sql);

        ps.setString(1, employee.getFirstName());
        ps.setString(2, employee.getLastName());
        ps.setString(3, employee.getNationalId());
        ps.setString(4, employee.getFatherName());
        ps.setString(5, employee.getCertificateNumber());
        ps.setDate(6, Date.valueOf(employee.getBirthDate()));
        ps.setString(7, employee.getBirthPlace().name());
        ps.setString(8, employee.getGender().name());
        ps.setString(9, employee.getEducation().name());
        ps.setString(10, employee.getMajor().name());
        ps.setString(11, employee.getMarriage().name());
        ps.setInt(12, employee.getNumberOfChildren());
        ps.setString(13, employee.getPhoneNumber());
        ps.setString(14, employee.getInsuranceNumber());
        ps.setString(15, employee.getBankAccountNumber());
        ps.setInt(16, employee.getId());
        ps.executeUpdate();
    }


    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Employees WHERE id=?";
        ps = connection.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public List<Employee> findAll() throws Exception {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employees ORDER BY id";
        ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(EntityMapper.employeeMapper(rs));
        }
        return list;
    }

    public Employee findById(int id) throws Exception {
        String sql = "SELECT * FROM Employees WHERE id=?";
        ps = connection.prepareStatement(sql);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return EntityMapper.employeeMapper(rs);
        } else
            return null;
    }

    public Employee findByNationalId(String nationalId) throws Exception {
        String sql = "SELECT * FROM Employees WHERE National_Id=?";
        ps = connection.prepareStatement(sql);

        ps.setString(1, nationalId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return EntityMapper.employeeMapper(rs);
        } else
            return null;
    }

    @Override
    public void close() throws Exception {
        ps.close();
        connection.close();
    }
}
