package hu.nive.ujratervezes.kepesitovizsga.covid;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SinoPharm extends Vaccine {
    public SinoPharm(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Person> getVaccinationList() {

        int age = 65;


        try (
                Connection conn = this.getDataSource().getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(new StringBuilder().append("(select person_name, age, chronic_disease, pregnancy from registrations where pregnancy = 'nem' AND chronic_disease = 'nem' AND age <= ? ORDER BY reg_id)\n")
                                .append("UNION\n")
                                .append("(select person_name, age, chronic_disease, pregnancy from registrations where pregnancy = 'nem' AND chronic_disease = 'nem' AND age > ? ORDER BY reg_id)")
                                .toString())
        ) {
            stmt.setInt(1, age);
            stmt.setInt(2, age);
            return getResult(stmt);
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by insert", sqle);
        }
    }

    private List<Person> getResult(PreparedStatement stmt) {
        List<Person> people = new ArrayList<>();
        try (
                ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                people.add(new Person(rs.getString("person_name"),
                        rs.getInt("age"), ChronicDisease.valueOf(rs.getString("chronic_disease").toUpperCase()),
                        Pregnancy.valueOf(rs.getString("pregnancy").toUpperCase())));
            }
            if (people.size() == 0) {
                throw new IllegalArgumentException("No result");
            }
            return people;
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by Query", sqle);
        }
    }
}
