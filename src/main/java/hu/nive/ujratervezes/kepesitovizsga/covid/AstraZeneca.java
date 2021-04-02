package hu.nive.ujratervezes.kepesitovizsga.covid;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AstraZeneca extends Vaccine {
    @Override
    public List<Person> getVaccinationList() {

        int age = 65;

        try (
                Connection conn = this.getDataSource().getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(new StringBuilder().append("(select person_name, age, chronic_disease, pregnancy from registrations where pregnancy = 'nem' AND chronic_disease = 'igen' ORDER BY reg_id)\n")
                                .append("UNION\n")
                                .append("(select person_name, age, chronic_disease, pregnancy from registrations where age > ? ORDER BY reg_id)\n")
                                .append("UNION\n")
                                .append("(select person_name, age, chronic_disease, pregnancy from registrations where pregnancy = 'nem' AND age <= 65 ORDER BY reg_id);\n")
                                .toString())
        ) {
            stmt.setInt(1, age);
            return getResult(stmt);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error by insert", e);
        }
    }

    private List<Person> getResult(PreparedStatement stmt) {
        List<Person> people = new ArrayList<>();
        try (
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                people.add(new Person(rs.getString("person_name"),
                        rs.getInt("age"), ChronicDisease.valueOf(rs.getString("chronic_disease").toUpperCase()),
                        Pregnancy.valueOf(rs.getString("pregnancy").toUpperCase())));
            }
            if (people.isEmpty()) {
                throw new IllegalArgumentException("No result");
            }
            return people;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error by Query", e);
        }
    }

    public AstraZeneca(DataSource dataSource) {
        super(dataSource);
    }
}
