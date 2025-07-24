package salary.model.repository;

import salary.model.entity.Allowance;
import salary.model.entity.Loan;
import salary.model.entity.enums.Year;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class AllowanceRepository implements Repository<Allowance>{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public AllowanceRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }
    @Override
    public void save(Allowance allowance) throws Exception {
        allowance.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "allowance_seq"));
        preparedStatement = connection.prepareStatement(
                "insert into ALLOWANCE values (?, ?, ?, ?, ?,?)"
        );
        preparedStatement.setInt(1, allowance.getId());
        preparedStatement.setString(2, allowance.getYear().name());
        preparedStatement.setDouble(3, allowance.getHousingAllowance());
        preparedStatement.setDouble(4, allowance.getFoodAllowance());
        preparedStatement.setDouble(5, allowance.getMarriageAllowance());
        preparedStatement.setDouble(6, allowance.getChildAllowance());

        preparedStatement.execute();
    }

    @Override
    public void edit(Allowance allowance) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE ALLOWANCE SET year = ?, Housing_allowance = ?, food_allowance = ?, marriage_allowance = ?, child_allowance = ? WHERE id = ?"
        );

        preparedStatement.setString(1, allowance.getYear().name());
        preparedStatement.setDouble(2, allowance.getHousingAllowance());
        preparedStatement.setDouble(3, allowance.getFoodAllowance());
        preparedStatement.setDouble(4, allowance.getMarriageAllowance());
        preparedStatement.setDouble(5, allowance.getChildAllowance());
        preparedStatement.setInt(6, allowance.getId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<Allowance> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Allowance findById(int id) throws Exception {
        return null;
    }
    public Allowance findByYear(Year year) throws Exception {
        Allowance allowance = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        preparedStatement = connection.prepareStatement("select * from Allowance where year=?");
        preparedStatement.setString(1, year.name());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            allowance = EntityMapper.allowanceMapper(resultSet);
        }
        return allowance;
    }


    @Override
    public void close() throws Exception {

    }
}
