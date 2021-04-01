package hu.nive.ujratervezes.kepesitovizsga.covid;

import javax.sql.DataSource;
import java.util.List;

public abstract class Vaccine {

    private DataSource dataSource;

    public Vaccine(DataSource dataSource) {

        this.dataSource = dataSource;

    }


    public abstract List<Person> getVaccinationList();

    public DataSource getDataSource() {
        return dataSource;
    }
}
