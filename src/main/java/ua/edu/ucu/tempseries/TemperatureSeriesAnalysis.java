package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final int START_BUFFER = 8;
    static final double LOWEST_TEMPERATURE = -273.0;
    static final double DELTA = 0.0000001;

    private double[] temp;
    private int length;
    private int capacity;

    public TemperatureSeriesAnalysis() {
        this.temp = new double[START_BUFFER];
        this.length = 0;
        this.capacity = START_BUFFER;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries)
            throws InputMismatchException {
        checkBadTemperature(temperatureSeries);
        this.temp = new double[temperatureSeries.length];
        System.arraycopy(temperatureSeries, 0,
                this.temp, 0, temperatureSeries.length);
        this.length = this.temp.length;
        this.capacity = this.temp.length;
    }

    public double average() throws IllegalArgumentException {
         checkEmpty();
         double sm = 0;
         for (double x: this.temp) {
             sm += x;
         }
         return sm / this.length;
    }

    public double deviation() throws  IllegalArgumentException {
        checkEmpty();
        double sqrSum = 0;
        for (double x: this.temp) {
            sqrSum += x*x;
        }
        double avg = this.average();
        return Math.sqrt(sqrSum / this.length - avg * avg);
    }

    public double min() throws IllegalArgumentException {
        checkEmpty();
        double mn = this.temp[0];
        for (double temperature: this.temp) {
            mn = Math.min(mn, temperature);
        }
        return mn;
    }

    public double max() throws IllegalArgumentException {
        checkEmpty();
        double mx = this.temp[0];
        for (double temperature: this.temp) {
            mx = Math.max(mx, temperature);
        }
        return mx;
    }

    public double findTempClosestToZero() throws IllegalArgumentException {
        return this.findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue)
            throws IllegalArgumentException {
        checkEmpty();
        double closestToValue = this.temp[0];
        double currentDistance = Math.abs(closestToValue - tempValue);
        for (double temperature: this.temp) {
            double distance = Math.abs(temperature - tempValue);
            if (distance < currentDistance
                    || (Math.abs(distance - currentDistance) < DELTA
                            && temperature >= tempValue)) {
                closestToValue = temperature;
                currentDistance = Math.abs(closestToValue - tempValue);
            }
        }
        return closestToValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        int numberOfNeeded = 0;
        for (double temperature: this.temp) {
            if (temperature < tempValue) {
                numberOfNeeded++;
            }
        }
        double[] lessArray = new double[numberOfNeeded];
        int idx = 0;
        for (double temperature: this.temp) {
            if (temperature < tempValue) {
                lessArray[idx] = temperature;
                idx++;
            }
        }
        return lessArray;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int numberOfNeeded = 0;
        for (double temperature: this.temp) {
            if (temperature >= tempValue) {
                numberOfNeeded++;
            }
        }
        double[] lessArray = new double[numberOfNeeded];
        int idx = 0;
        for (double temperature: this.temp) {
            if (temperature >= tempValue) {
                lessArray[idx] = temperature;
                idx++;
            }
        }
        return lessArray;
    }

    public TempSummaryStatistics summaryStatistics()
            throws IllegalArgumentException {
        checkEmpty();
        double mean = this.average();
        double dev = this.deviation();
        double min = this.min();
        double max = this.max();
        return new TempSummaryStatistics(mean, dev, min, max);
    }

    public int addTemps(double... temps) throws InputMismatchException {
        checkBadTemperature(temps);
        for (double tmp: temps) {
            this.addTemp(tmp);
        }
        return this.length;
    }

    private void resize() {
        double[] newTemp = new double[2 * this.length + 1];
        System.arraycopy(this.temp, 0, newTemp, 0, this.length);
        this.temp = newTemp;
        this.capacity = this.capacity * 2 + 1;
    }

    private void addTemp(double temperature) {
        if (this.length == this.capacity) {
            this.resize();
        }
        this.temp[this.length] = temperature;
        this.length += 1;
    }

    private void checkEmpty() {
        if (this.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    private void checkBadTemperature(double[] temps) {
        for (double temperature: temps) {
            if (temperature < LOWEST_TEMPERATURE) {
                throw new InputMismatchException();
            }
        }
    }
}
