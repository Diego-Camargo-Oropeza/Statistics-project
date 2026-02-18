package statisticsfirstmidterm;

import static java.lang.Math.log10;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
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

        Collections.sort(sample);

        System.out.println("Sorted list: ");
        for (Double n : sample) {
            System.out.print(n + " ");
        }

        boolean watcher = true;
        do {
            System.out.println("Please select the way that we are going to treat the data and hit ENTER...\n"
                    + "1.- Group the data\n"
                    + "2.- Leave it ungrouped");
            String dec = in.next();
            switch (dec) {
                case "1" -> {
                    watcher = true;

                }

                case "2" -> {
                    watcher = true;

                }

                default -> {
                    watcher = false;
                }
            }
        } while (!watcher);

    }

    public static double[] getWidth(int n, double first, double last) {
        double k = 1 + (3.322 * log10(n));
        int width = (int) ((last - first) / n);
        double[] kAndWidth = {k, width};
        return kAndWidth;
    }

    public static ArrayList<Double> getIntervalsAndClassMarks(ArrayList<Double> sample, int width, int k, double first, double last) {

    }

    public static double getUngroupedAvg(ArrayList<Integer> group) {

        return 2;

    }

    /**
     * Simulates clearing the console screen by printing many new lines. This is
     * the recommended method for Java IDEs like NetBeans.
     */
    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}
