package statisticsfirstmidterm;

public class Interval {

    private final double lowerLimit;
    private final double upperLimit;
    private final double classMark;
    private int frequency;

    public Interval(double lowerLimit, double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        // Class mark (midpoint) is the representative value for grouped formulas.
        this.classMark = (lowerLimit + upperLimit) / 2.0;
        this.frequency = 0;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public double getClassMark() {
        return classMark;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        // Count how many sample values fall inside this interval.
        frequency++;
    }
}
