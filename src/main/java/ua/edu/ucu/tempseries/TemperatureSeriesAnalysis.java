package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private double[] temp;
    private int length;
    private int capacity;

    public TemperatureSeriesAnalysis() {
        this.temp = new double[8];
        this.length = 0;
        this.capacity = 8;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) throws InputMismatchException{
        checkBadTemperature(temperatureSeries);
        this.temp = temperatureSeries;
        this.length = this.temp.length;
        this.capacity = this.temp.length;
    }

    public double average() throws IllegalArgumentException{
         checkEmpty();
         double sm = 0;
         for (double x: this.temp){
             sm += x;
         }
         return sm / this.length;
    }

    public double deviation() throws  IllegalArgumentException{
        checkEmpty();
        double sqr_sum = 0;
        for (double x: this.temp){
            sqr_sum += x*x;
        }
        double avg = this.average();
        return Math.sqrt(sqr_sum / this.length - avg * avg);
    }

    public double min() throws IllegalArgumentException{
        checkEmpty();
        double mn = this.temp[0];
        for (double temperature: this.temp){
            mn = Math.min(mn, temperature);
        }
        return mn;
    }

    public double max() throws IllegalArgumentException{
        checkEmpty();
        double mx = this.temp[0];
        for (double temperature: this.temp){
            mx = Math.max(mx, temperature);
        }
        return mx;
    }

    public double findTempClosestToZero() throws IllegalArgumentException{
        return this.findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) throws IllegalArgumentException{
        checkEmpty();
        double closest_to_value = this.temp[0];
        double current_distance = Math.abs(closest_to_value - tempValue);
        for (double temperature: this.temp){
            double distance = Math.abs(temperature - tempValue);
            if (distance < current_distance || (distance == current_distance && temperature >= tempValue)){
                closest_to_value = temperature;
                current_distance = Math.abs(closest_to_value - tempValue);
            }
        }
        return closest_to_value;
    }

    public double[] findTempsLessThen(double tempValue) {
        int number_of_needed = 0;
        for (double temperature: this.temp){
            if (temperature < tempValue){
                number_of_needed++;
            }
        }
        double[] lessArray = new double[number_of_needed];
        int idx = 0;
        for (double temperature: this.temp){
            if (temperature < tempValue){
                lessArray[idx] = temperature;
                idx++;
            }
        }
        return lessArray;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int number_of_needed = 0;
        for (double temperature: this.temp){
            if (temperature >= tempValue){
                number_of_needed++;
            }
        }
        double[] lessArray = new double[number_of_needed];
        int idx = 0;
        for (double temperature: this.temp){
            if (temperature >= tempValue){
                lessArray[idx] = temperature;
                idx++;
            }
        }
        return lessArray;
    }

    public TempSummaryStatistics summaryStatistics() throws IllegalArgumentException{
        checkEmpty();
        double mean = this.average();
        double dev = this.deviation();
        double min = this.min();
        double max = this.max();
        return new TempSummaryStatistics(mean, dev, min, max);
    }

    public int addTemps(double... temps) throws InputMismatchException{
        checkBadTemperature(temps);
        for (double tmp: temps){
            this.addTemp(tmp);
        }
        return this.length;
    }

    private void resize(){
        double[] new_temp = new double[2 * this.length + 1];
        System.arraycopy(this.temp, 0, new_temp, 0, this.length);
        this.temp = new_temp;
        this.capacity = this.capacity * 2 + 1;
    }

    private void addTemp(double temp){
        if (this.length == this.capacity){
            this.resize();
        }
        this.temp[this.length] = temp;
        this.length += 1;
    }

    private void checkEmpty(){
        if (this.length == 0){
            throw new IllegalArgumentException();
        }
    }

    private void checkBadTemperature(double[] temps){
        for (double temperature: temps){
            if (temperature < -273.0){
                throw new InputMismatchException();
            }
        }
    }
}
