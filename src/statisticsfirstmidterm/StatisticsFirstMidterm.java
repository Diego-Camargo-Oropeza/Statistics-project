package statisticsfirstmidterm;

import static java.lang.Math.ceil;
import static java.lang.Math.log10;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Diego Camargo
 */
public class StatisticsFirstMidterm {

    public static void main(String[] args) {

        ArrayList<Double> sample = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int length = 0;

        System.out.println("Welcome to the statistics calculator! Please enter the length of the data set and hit ENTER:");
        while (!in.hasNextInt()) {
            System.out.println("That's not a valid integer! Try again:");
            in.next();
        }

        length = in.nextInt();
        System.out.println("The data set will have a length of: " + length);
        clearConsole();

        for (int i = 0; i < length; i++) {
            System.out.println("Enter number " + (i + 1) + " of " + length);
            while (!in.hasNextInt()) {
                System.out.println("That's not a valid integer! Try again:");
                in.next();
                if (!(i == 0)) {
                    i--;
                }
            }
            sample.add(in.nextDouble());
        }

        // Sort the list in ascending order
        Collections.sort(sample);

        System.out.println("Sorted list: ");
        for (Double n : sample) {
            System.out.print(n + " ");
        }

        boolean switchFlag = true;
        do {
            System.out.println("""
                               Please select the way that we are going to treat the data and hit ENTER...
                               1.- Group the data
                               2.- Leave it ungrouped""");
            String dec = in.next();
            switch (dec) {
                case "1" -> {
                    switchFlag = true;

                }

                case "2" -> {
                    switchFlag = true;

                }

                default -> {
                    switchFlag = false;
                    clearConsole();
                }

            }
        } while (!switchFlag);

    }

    public static double[] getWidth(int n, double first, double last) {
        double k = 1 + (3.322 * log10(n));
        int width = (int) ((last - first) / ceil(k));
        double[] kAndWidth = {k, width};
        return kAndWidth;
    }

    public static ArrayList<Double> getIntervalsAndClassMarks(double width, int k, double first) {
        ArrayList<Double> intervalsAndClassMarks = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            double lowerBound = first + (i * width);
            double upperBound = lowerBound + width;
            double classMark = (lowerBound + upperBound) / 2.0;

            intervalsAndClassMarks.add(lowerBound);
            intervalsAndClassMarks.add(upperBound);
            intervalsAndClassMarks.add(classMark);
        }

        return intervalsAndClassMarks;
    }

    public static double getUngroupedAvg(ArrayList<Integer> group) {

        return 2;

    }

    public static void clearConsole() {
        //clear cmd screen without new lines
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }

    }

}
