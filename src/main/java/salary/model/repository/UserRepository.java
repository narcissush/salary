package salary.model.repository;

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
        user.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "user_seq"));

        preparedStatement = connection.prepareStatement(
                "insert into users values (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getNationalId());
        preparedStatement.setString(5, user.getEducation());
        preparedStatement.setString(6, user.isMarried() ? "Y" : "N");
        preparedStatement.setInt(7, user.getNumberOfChildren());
        preparedStatement.setString(8, user.getGender().name());
        preparedStatement.setDate(9, (user.getBirthDate() != null) ? Date.valueOf(user.getBirthDate()) : null);
        preparedStatement.setString(10, user.getUsername());
        preparedStatement.setString(11, user.getPassword());

        preparedStatement.execute();
    }

    @Override
    public void edit(User user) throws Exception {
        preparedStatement = connection.prepareStatement(
                "update users set first_Name=?,last_Name=?,national_Id=?,education=?,married=?,number_Of_Children=?,gender=?,birth_Date=?,user_name=?,password=? where id=?"
        );
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getNationalId());
        preparedStatement.setString(4, user.getEducation());
        preparedStatement.setString(5, user.isMarried() ? "Y" : "N");
        preparedStatement.setInt(6, user.getNumberOfChildren());
        preparedStatement.setString(7, user.getGender().name());
        preparedStatement.setDate(8, (user.getBirthDate() != null) ? Date.valueOf(user.getBirthDate()) : null);
        preparedStatement.setString(9, user.getUsername());
        preparedStatement.setString(10, user.getPassword());
        preparedStatement.setInt(11, user.getId());
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
        preparedStatement = connection.prepareStatement("select * from users order by last_name");
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
        preparedStatement = connection.prepareStatement("select * from userS where id=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = EntityMapper.userMapper(resultSet);
        }
        return user;
    }

    public User findByUserAndPassword(String username,String password) throws Exception {
        User user = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from userS where user_name=? and password=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(1, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = EntityMapper.userMapper(resultSet);
        }
        return user;
    }

    @Override
    public void close() throws Exception {

    }
}

