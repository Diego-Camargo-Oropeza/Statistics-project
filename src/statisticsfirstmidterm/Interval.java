
package statisticsfirstmidterm;

public class Interval {
    private final double lowerLimit;
    private final double upperLimit;
    private final double classMark;
    private int frequency;
    private int cumFrequency;
    private double relFrequency;
    private double cumRelFrequency;
    
   

    public Interval(double lowerLimit, double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        // Class mark (midpoint) is the representative value for grouped formulas.
        this.classMark = (lowerLimit + upperLimit) / 2.0;
        this.frequency = 0;
        this.cumFrequency = 0;
        this.relFrequency = 0.0;
        this.cumRelFrequency = 0.0;
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
    
    public int getCumFrequency(){
        return cumFrequency;
    }
    
    public double getRelFrequency(){
        return relFrequency;
    }
    
    public double getCumRelFrequency(){
        return cumRelFrequency;
    }
    
    public void setCumFrequency(int cumFrequency){
        this.cumFrequency = cumFrequency;
    }
    
    public void setRelFrequency(int n){
        this.relFrequency = (double)frequency / n;
    }
    
    public void setCumRelFrequency(double cumRelFrequency){
        this.cumRelFrequency = cumRelFrequency;
    }
    
    public void incrementFrequency() {
        // Count how many sample values fall inside this interval.
        frequency++;
    }
}
