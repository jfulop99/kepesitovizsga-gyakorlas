package hu.nive.ujratervezes.kepesitovizsga.dogs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DogManager {

    private List<Dog> dogs;

    public DogManager() {
        dogs = new ArrayList<>();
        getDataFromFile();
    }

    private void getDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(DogManager.class.getResourceAsStream("/dogs.csv")))){
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file", e);
        }


    }

    private void processLine(String line) {
        String[] parts = line.split(";");
        dogs.add(new Dog(parts[1], parts[4], parts[5]));
    }


    public String getCountryByExactDogSpecies(String name) {
        return dogs.stream()
                .filter(dog -> dog.getName().equalsIgnoreCase(name))
                .map(Dog::getCountry)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No such dog name found."));
    }

    public List<Dog> getDogsInAlphabeticalOrderByName() {
        List<Dog> result = new ArrayList<>(dogs);
        result.sort(Comparator.comparing(Dog::getName));
        return result;
    }

    public Map<String, Integer> getDogStatistics() {
        Map<String, Integer> result = new HashMap<>();
        for (Dog dog: dogs){
            result.merge(dog.getCountry(), 1, Integer::sum);
        }
        return result;
    }
}
