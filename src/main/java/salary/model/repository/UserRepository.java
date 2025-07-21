package salary.model.repository;

import salary.controller.AppState;
import salary.model.entity.User;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public UserRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }


    @Override
    public void save(User user) throws Exception {
        user.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "users_seq"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO users (" +
                        "id, first_Name, last_Name, national_Id, father_name, " +
                        "certificate_Number, birth_Date, birth_Place, gender, " +
                        "education, major, marriage, number_Of_Children, " +
                        "Phone_number, user_name, pass_word" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getNationalId());
        preparedStatement.setString(5, user.getFatherName());
        preparedStatement.setString(6, user.getCertificateNumber());
        preparedStatement.setDate(7, (user.getBirthDate() != null) ? Date.valueOf(user.getBirthDate()) : null);
        preparedStatement.setString(8, user.getBirthPlace().name());
        preparedStatement.setString(9, user.getGender().name());
        preparedStatement.setString(10, user.getEducation().name());
        preparedStatement.setString(11, user.getMajor().name());
        preparedStatement.setString(12, user.getMarriage().name());
        preparedStatement.setInt(13, user.getNumberOfChildren());
        preparedStatement.setString(14, user.getPhoneNumber());
        preparedStatement.setString(15, user.getUsername());
        preparedStatement.setString(16, user.getPassword());

        preparedStatement.execute();
    }

    @Override
    public void edit(User user) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE Users SET " +
                        "first_Name = ?, last_Name = ?, national_Id = ?, father_name = ?, " +
                        "certificate_Number = ?, birth_Date = ?, birth_Place = ?, gender = ?, " +
                        "education = ?, major = ?, marriage = ?, number_Of_Children = ?, " +
                        "Phone_number = ?, user_name = ?, pass_word = ? " +
                        "WHERE id = ?");
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getNationalId());
        preparedStatement.setString(4, user.getFatherName());
        preparedStatement.setString(5, user.getCertificateNumber());
        preparedStatement.setDate(6, (user.getBirthDate() != null) ? Date.valueOf(user.getBirthDate()) : null);
        preparedStatement.setString(7, user.getBirthPlace().name());
        preparedStatement.setString(8, user.getGender().name());
        preparedStatement.setString(9, user.getEducation().name());
        preparedStatement.setString(10, user.getMajor().name());
        preparedStatement.setString(11, user.getMarriage().name());
        preparedStatement.setInt(12, user.getNumberOfChildren());
        preparedStatement.setString(13, user.getPhoneNumber());
        preparedStatement.setString(14, user.getUsername());
        preparedStatement.setString(15, user.getPassword());
        preparedStatement.setInt(16, user.getId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "delete from users where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> userList = new ArrayList<>();
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from users order by id");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            userList.add(EntityMapper.userMapper(resultSet));
        }
        return userList;
    }

    @Override
    public User findById(int id) throws Exception {
        User user = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from users where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = EntityMapper.userMapper(resultSet);
        }
        return user;
    }

    public User findByUserAndPassword(String username,String password) throws Exception {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from USERS where user_name=? and password=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            AppState.userSelected = EntityMapper.userMapper(resultSet);
        }
        return AppState.userSelected;
    }

    @Override
    public void close() throws Exception {

    }
}

