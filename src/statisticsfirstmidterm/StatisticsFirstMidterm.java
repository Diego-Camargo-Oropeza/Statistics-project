package statisticsfirstmidterm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Diego Camargo
 */
public class StatisticsFirstMidterm {

    public static void main(String[] args) {

        ArrayList<Double> sample = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int length;

        System.out.println("Welcome to the statistics calculator.");
        System.out.println("Enter the number of values in your data set:");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid integer. Try again:");
            scanner.next();
        }
        length = scanner.nextInt();

        if (length <= 0) {
            System.out.println("The data set length must be greater than zero.");
            return;
        }

        System.out.println("Data set length: " + length);
        clearConsole();

        for (int i = 0; i < length; i++) {
            System.out.println("Enter value " + (i + 1) + " of " + length + ":");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid number. Try again:");
                scanner.next();
            }
            sample.add(scanner.nextDouble());
        }

        Collections.sort(sample);
        //Sample data set for testing grouped statistics:
        ArrayList<Double> sample2 = new ArrayList<>();
        sample2.add(1.0);
        sample2.add(2.0);
        sample2.add(3.0);
        sample2.add(4.0);
        sample2.add(5.0);
        sample2.add(6.0);
        sample2.add(7.0);
        sample2.add(8.0);
        sample2.add(9.0);
        sample2.add(12.0);
        sample2.add(13.0);
        sample2.add(12.0);
        sample2.add(12.0);
        sample2.add(11.0);
        sample2.add(20.0);
        sample2.add(18.0);
        sample2.add(13.0);
        sample2.add(17.0);
        sample2.add(7.0);
        sample2.add(2.0);
        sample2.add(1.0);
        sample2.add(1.0);
        Collections.sort(sample2);

        boolean validOption;
        do {
            validOption = true;
            System.out.println();
            System.out.println("""
                               Select how to process the data:
                               1. Grouped data
                               2. Ungrouped data""");
            String option = scanner.next();
            switch (option) {
                case "1" -> {
                    runGroupedStatistics(sample2);
                    System.out.println("Want to try again? (y/n)");
                    String repeat = scanner.next();
                    if (repeat.equalsIgnoreCase("y")) {
                        validOption = false;
                    }
                }
                case "2" -> {
                    runUngroupedStatistics(sample);
                    System.out.println("Want to repeat with grouped data? (y/n)");
                    String repeat = scanner.next();
                    if (repeat.equalsIgnoreCase("y")) {
                        validOption = false;
                    }
                }
                default -> {
                    validOption = false;
                    System.out.println("Invalid option. Please enter 1 or 2.");
                }
            }
        } while (!validOption);

    }

    public static int getClassCount(int n) {
        // Sturges' rule for grouped distributions.
        return Math.max(1, (int) Math.ceil(1 + (3.322 * Math.log10(n))));
    }

    public static double getClassWidth(double min, double max, int classCount) {
        double range = max - min;
        if (range == 0) {
            // Avoid zero-width intervals when all values are equal.
            return 1.0;
        }
        return range / classCount;
    }

    public static ArrayList<Interval> buildIntervals(double width, int classCount, double min) {
        ArrayList<Interval> intervals = new ArrayList<>();

        for (int i = 0; i < classCount; i++) {
            // Build consecutive class limits from the minimum value.
            double lowerBound = min + (i * width);
            double upperBound = lowerBound + width;
            intervals.add(new Interval(lowerBound, upperBound));
        }

        return intervals;
    }

    public static void assignFrequencies(ArrayList<Double> sample, ArrayList<Interval> intervals) {
        for (double value : sample) {
            for (int i = 0; i < intervals.size(); i++) {
                Interval interval = intervals.get(i);
                boolean isLastInterval = i == intervals.size() - 1;
                boolean insideInterval;

                // Use [lower, upper) for all classes except the last one, which is [lower, upper].
                if (isLastInterval) {
                    insideInterval = value >= interval.getLowerLimit() && value <= interval.getUpperLimit();
                } else {
                    insideInterval = value >= interval.getLowerLimit() && value < interval.getUpperLimit();
                }

                if (insideInterval) {
                    interval.incrementFrequency();
                    break;
                }
            }
        }
    }

    public static void runUngroupedStatistics(ArrayList<Double> sample) {
        double mean = getUngroupedMean(sample);
        double median = getUngroupedMedian(sample);
        List<Double> modes = getUngroupedModes(sample);
        double stdDev = getUngroupedStdDev(sample, true);

        System.out.println();
        System.out.println("Ungrouped statistics:");
        System.out.printf("Mean: %.6f%n", mean);
        System.out.printf("Median: %.6f%n", median);
        System.out.println("Mode(s): " + modes);
        System.out.printf("Standard deviation (population): %.6f%n", stdDev);
    }

    public static void runGroupedStatistics(ArrayList<Double> sample) {
        int n = sample.size();
        double min = sample.get(0);
        double max = sample.get(sample.size() - 1);
        int classCount = getClassCount(n);
        double width = getClassWidth(min, max, classCount);

        ArrayList<Interval> intervals = buildIntervals(width, classCount, min);
        assignFrequencies(sample, intervals);

        printFrequencyTable(intervals);

        double mean = getGroupedMean(intervals);
        double median = getGroupedMedian(intervals, width);
        double mode = getGroupedMode(intervals, width);
        double stdDev = getGroupedStdDev(intervals, mean, true);

        System.out.println();
        System.out.println("Grouped statistics:");
        System.out.printf("Mean: %.6f%n", mean);
        System.out.printf("Median: %.6f%n", median);
        System.out.printf("Mode: %.6f%n", mode);
        System.out.printf("Standard deviation (population): %.6f%n", stdDev);
    }

    public static double getUngroupedMean(ArrayList<Double> sample) {
        double sum = 0;
        for (double value : sample) {
            sum += value;
        }
        return sum / sample.size();
    }

    public static double getUngroupedMedian(ArrayList<Double> sample) {
        int n = sample.size();
        // Odd n: middle element. Even n: mean of the two central elements.
        if (n % 2 == 1) {
            return sample.get(n / 2);
        }
        return (sample.get((n / 2) - 1) + sample.get(n / 2)) / 2.0;
    }

    public static List<Double> getUngroupedModes(ArrayList<Double> sample) {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        int maxFrequency = 0;

        for (double value : sample) {
            int newFrequency = frequencyMap.getOrDefault(value, 0) + 1;
            frequencyMap.put(value, newFrequency);
            if (newFrequency > maxFrequency) {
                maxFrequency = newFrequency;
            }
        }

        ArrayList<Double> modes = new ArrayList<>();
        if (maxFrequency <= 1) {
            // No repeated values means no mode in this implementation.
            return modes;
        }

        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }

        Collections.sort(modes);
        return modes;
    }

    public static double getUngroupedStdDev(ArrayList<Double> sample, boolean population) {
        double mean = getUngroupedMean(sample);
        double sumSquaredDiff = 0;

        for (double value : sample) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }

        int n = sample.size();
        // Population uses n; sample uses n - 1.
        double divisor = population ? n : n - 1;
        if (divisor <= 0) {
            return 0;
        }

        return Math.sqrt(sumSquaredDiff / divisor);
    }

    public static double getGroupedMean(ArrayList<Interval> intervals) {
        double weightedSum = 0;
        int totalFrequency = 0;

        for (Interval interval : intervals) {
            // Grouped mean uses class marks weighted by class frequencies.
            weightedSum += interval.getClassMark() * interval.getFrequency();
            totalFrequency += interval.getFrequency();
        }

        return weightedSum / totalFrequency;
    }

    public static double getGroupedMedian(ArrayList<Interval> intervals, double width) {
        int totalFrequency = 0;
        for (Interval interval : intervals) {
            totalFrequency += interval.getFrequency();
        }

        double half = totalFrequency / 2.0;
        int cumulativeFrequency = 0;

        for (Interval interval : intervals) {
            int previousCumulative = cumulativeFrequency;
            cumulativeFrequency += interval.getFrequency();
            if (cumulativeFrequency >= half) {
                // Median class interpolation: Lm + ((N/2 - Fprev) / fm) * h
                double Lm = interval.getLowerLimit();
                double Fprev = previousCumulative;
                double fm = interval.getFrequency();
                if (fm == 0) {
                    return Lm;
                }
                return Lm + ((half - Fprev) / fm) * width;
            }
        }

        return intervals.get(intervals.size() - 1).getUpperLimit();
    }

    public static double getGroupedMode(ArrayList<Interval> intervals, double width) {
        if (intervals.isEmpty()) {
            return 0;
        }

        int modalIndex = 0;
        for (int i = 1; i < intervals.size(); i++) {
            if (intervals.get(i).getFrequency() > intervals.get(modalIndex).getFrequency()) {
                modalIndex = i;
            }
        }

        Interval modalClass = intervals.get(modalIndex);
        double Lmo = modalClass.getLowerLimit();
        double f1 = modalClass.getFrequency();
        double f0 = modalIndex > 0 ? intervals.get(modalIndex - 1).getFrequency() : 0;
        double f2 = modalIndex < intervals.size() - 1 ? intervals.get(modalIndex + 1).getFrequency() : 0;
        double denominator = (2 * f1) - f0 - f2;

        if (denominator == 0) {
            return modalClass.getClassMark();
        }

        // Grouped mode interpolation: Lmo + ((f1 - f0) / (2f1 - f0 - f2)) * h
        return Lmo + ((f1 - f0) / denominator) * width;
    }

    public static double getGroupedStdDev(ArrayList<Interval> intervals, double mean, boolean population) {
        double sumSquaredDiff = 0;
        int totalFrequency = 0;

        for (Interval interval : intervals) {
            double diff = interval.getClassMark() - mean;
            // Use class marks as representative values for each class.
            sumSquaredDiff += interval.getFrequency() * diff * diff;
            totalFrequency += interval.getFrequency();
        }

        // Population uses N; sample uses N - 1.
        double divisor = population ? totalFrequency : totalFrequency - 1;
        if (divisor <= 0) {
            return 0;
        }

        return Math.sqrt(sumSquaredDiff / divisor);
    }

    public static void printFrequencyTable(ArrayList<Interval> intervals) {
        System.out.println();
        System.out.println("Frequency table:");
        System.out.printf("%-12s %-12s %-12s %-12s%n", "Lower", "Upper", "Class Mark", "Frequency");
        for (Interval interval : intervals) {
            System.out.printf(
                    "%-12.6f %-12.6f %-12.6f %-12d%n",
                    interval.getLowerLimit(),
                    interval.getUpperLimit(),
                    interval.getClassMark(),
                    interval.getFrequency()
            );
        }

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
