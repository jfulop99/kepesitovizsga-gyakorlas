package hu.nive.ujratervezes.kepesitovizsga.numberofdigits;

public class NumberOfDigits {
    public int getNumberOfDigits(int number) {

        int counter = 0;
        for (int i = 1; i <= number; i++) {
            counter += String.valueOf(i).length();
        }
        return counter;
    }
}
