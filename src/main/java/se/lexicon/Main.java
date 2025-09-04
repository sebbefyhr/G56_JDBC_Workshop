package se.lexicon;

import se.lexicon.dao.CityDaoImpl;
import se.lexicon.model.City;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Represents the entry point of the application.
 */
public class Main {
    public static void main(String[] args) {

        ResourceBundle rb = ResourceBundle.getBundle("application");

        try {

            Connection conn = DriverManager.getConnection(rb.getString("URL"), rb.getString("USER"), rb.getString("PASSWORD"));

            CityDaoImpl cdi = new CityDaoImpl(conn);

            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
