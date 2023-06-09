package com.techelevator.dao;

import com.techelevator.model.Brewery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcBreweryDao implements breweryDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcBreweryDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Brewery> getAllBrewery() {
        List<Brewery> allBreweries = new ArrayList<>();
        String sqlSelectAllBreweries = "SELECT * FROM brewery";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllBreweries);

        while(results.next()) {
            Brewery brewery = mapRowToBrewery(results);
            allBreweries.add(brewery);
        }
        return allBreweries;
    }

    @Override
    public void addBrewery(Brewery brewery) {
        String sqlAddBrewery = "INSERT INTO brewery(user_id, name, image, history, website," +
                "hours_days_operation, address, phone) VALUES(?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sqlAddBrewery, brewery.getUserId(), brewery.getName(), brewery.getImage(), brewery.getHistory(),
                brewery.getWebsite(),brewery.getHoursDaysOperation(),brewery.getAddress(),brewery.getPhone());
    }

    @Override
    public void deleteBrewery(long breweryId) {
        String sqlDeleteBrewery = "DELETE FROM brewery WHERE brewery_id = ?;";
        jdbcTemplate.update(sqlDeleteBrewery, breweryId);
    }

    @Override
    public boolean updateBrewery(Brewery brewery, long id) {
        String sqlUpdateBrewery = "UPDATE brewery SET user_id = ?, name = ?, image = ?, history = ?," +
                "website = ?, hours_days_operation = ?, address = ?, phone = ? " +
                "WHERE brewery_id = ?";
        try {
            jdbcTemplate.update(sqlUpdateBrewery, brewery.getUserId(), brewery.getName(), brewery.getImage(), brewery.getHistory(),
                    brewery.getWebsite(), brewery.getHoursDaysOperation(), brewery.getAddress(), brewery.getPhone(), id);
        }catch(DataIntegrityViolationException e){
            sqlUpdateBrewery = "ROLLBACK;";
            jdbcTemplate.update(sqlUpdateBrewery);
            return false;
        }
        return true;
    }

    @Override
    public Brewery getBreweryById(long breweryId) {
        Brewery brewery = new Brewery();
        String sqlGetBreweryById = "SELECT * FROM brewery WHERE brewery_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetBreweryById, breweryId);
        if(results.next()){
            brewery = mapRowToBrewery(results);
        }
        return brewery;
    }

    @Override
    public Brewery getBreweryByName(String name) {
        Brewery brewery = new Brewery();
        String sqlGetBreweryByName = "SELECT * FROM brewery WHERE UPPER(name) = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetBreweryByName, name);
        if(results.next()){
            brewery = mapRowToBrewery(results);
        }
        return brewery;
    }

    @Override
    public List<Brewery> getBreweriesByUserId(int userId) {
        List<Brewery> breweries = new ArrayList<>();
        String sql = "SELECT * FROM brewery WHERE user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Brewery brewery = mapRowToBrewery(results);
            breweries.add(brewery);
        }

        return breweries;
    }

    private Brewery mapRowToBrewery(SqlRowSet rs) {
        Brewery brewery = new Brewery();
        brewery.setUserId(rs.getInt("user_id"));
        brewery.setAddress(rs.getString("address"));
        brewery.setBreweryId(rs.getInt("brewery_id"));
        brewery.setHistory(rs.getString("history"));
        brewery.setHoursDaysOperation(rs.getString("hours_days_operation"));
        brewery.setImage(rs.getString("image"));
        brewery.setName(rs.getString("name"));
        brewery.setPhone(rs.getString("phone"));
        brewery.setWebsite(rs.getString("website"));
        return brewery;
    }
}
