package se.lexicon.dao;

import se.lexicon.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Represents the implementation of CityDao for interacting with the 'city' table in the database.
 */
public class CityDaoImpl implements CityDao {

    private Connection conn;

    public CityDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public Optional<City> findById(int id) {
        //TODO -- Completed? Ask simon!

        String stmnt = "select * from city where id = ?";
        Optional<City> op = null;

        try (PreparedStatement findCity = conn.prepareStatement(stmnt)) {

            findCity.setInt(1, id);

            ResultSet rs = findCity.executeQuery();
            while (rs.next()) {
                op = Optional.of(new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return op;
    }

    public List<City> findByCode(String code) {

        if (code.isEmpty() || code == null) return null;

        List<City> list = new ArrayList<>();

        String stmnt = "select * from city where countrycode = ?";

        try (PreparedStatement findByCCode = conn.prepareStatement(stmnt)) {

            findByCCode.setString(1, code);

            ResultSet rs = findByCCode.executeQuery();

            while (rs.next()) {
                list.add(new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<City> findByName(String name) {
        //TODO -- Completed? why list? ASk SIMON!

        if (name.isEmpty() || name == null) return null;

        List<City> list = new ArrayList<>();

        String stmnt = "select * from city where name = ?";

        try (PreparedStatement findByCCode = conn.prepareStatement(stmnt)) {

            findByCCode.setString(1, name);

            ResultSet rs = findByCCode.executeQuery();

            while (rs.next()) {
                list.add(new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<City> findAll() {
        List<City> cityList = new ArrayList<>();

        String statement = "select * from city";


        try (Statement findAllCities = conn.createStatement();
             ResultSet rs = findAllCities.executeQuery(statement)) {

            while (rs.next()) {
                cityList.add(new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cityList;
    }

    public City save(City city) {

        if (city == null) return null;

        String insert = "insert into city (name, countrycode, district, population) values(?,?,?,?)";

        try (PreparedStatement statement = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, city.getName());
            statement.setString(2, city.getCountryCode());
            statement.setString(3, city.getDistrict());
            statement.setInt(4, city.getPopulation());

            int res = statement.executeUpdate();

            if (res == 1) {
                try (ResultSet key = statement.getGeneratedKeys()) {
                    if (key.next()) {
                        city.setId(key.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return city;
    }

    public void update(City city) {

        if(city == null) return;

        String stmnt = "update city set name = ?, countrycode = ?, district = ?, population = ? where id = ?";

        try (PreparedStatement updateCity = conn.prepareStatement(stmnt)) {
            updateCity.setString(1, city.getName());
            updateCity.setString(2, city.getCountryCode());
            updateCity.setString(3, city.getDistrict());
            updateCity.setInt(4, city.getPopulation());
            updateCity.setInt(5, city.getId());

            int res = updateCity.executeUpdate();
            if(res == 1){
                System.out.println("Update successful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {

        String stmnt = "delete from city where id = ?";

        try (PreparedStatement statement = conn.prepareStatement(stmnt)) {
            statement.setInt(1, id);

            int res = statement.executeUpdate();

            if(res == 1){
                System.out.println("Delete successful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
