package hu.nive.ujratervezes.kepesitovizsga.dogs;

public class Dog {
    private String name;
    private String country;
    private String url;

    public Dog(String name, String country, String url) {
        this.name = name;
        this.country = country;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getUrl() {
        return url;
    }
}
