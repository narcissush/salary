package salary.model.repository;

import salary.model.entity.Allowance;
import salary.model.entity.enums.Year;
import salary.tools.ConnectionProvider;
import salary.tools.EntityMapper;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class AllowanceRepository implements Repository<Allowance>{
    private Connection connection;
    private PreparedStatement ps;

    public AllowanceRepository() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }
    @Override
    public void save(Allowance allowance) throws Exception {
        allowance.setId(ConnectionProvider.getConnectionProvider().getNextId(connection, "allowances_seq"));
        ps = connection.prepareStatement(
                "insert into ALLOWANCE values (?, ?, ?, ?, ?,?)"
        );
        ps.setInt(1, allowance.getId());
        ps.setInt(2, allowance.getYear());
        ps.setDouble(3, allowance.getHousingAllowance());
        ps.setDouble(4, allowance.getFoodAllowance());
        ps.setDouble(5, allowance.getMarriageAllowance());
        ps.setDouble(6, allowance.getChildAllowance());

        ps.execute();
    }

    @Override
    public void edit(Allowance allowance) throws Exception {
        ps = connection.prepareStatement(
                "UPDATE ALLOWANCE SET year = ?, Housing_allowance = ?, food_allowance = ?, marriage_allowance = ?, child_allowance = ? WHERE id = ?"
        );
        ps.setInt(1, allowance.getYear());
        ps.setDouble(2, allowance.getHousingAllowance());
        ps.setDouble(3, allowance.getFoodAllowance());
        ps.setDouble(4, allowance.getMarriageAllowance());
        ps.setDouble(5, allowance.getChildAllowance());
        ps.setInt(6, allowance.getId());
        ps.execute();
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
    public Allowance findByYear(int year) throws Exception {
        Allowance allowance = null;
        connection = ConnectionProvider.getConnectionProvider().getconnection();
        ps = connection.prepareStatement("select * from Allowance where year=?");
        ps.setInt(1, year);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            allowance = EntityMapper.allowanceMapper(resultSet);
        }
        return allowance;
    }


    @Override
    public void close() throws Exception {
        ps.close();
        connection.close();
    }
}
