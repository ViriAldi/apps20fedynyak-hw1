package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }

    @Test
    public void testDeviation() {
        double[] temperatureSeries = {5.0, 6.0, 7.0, 8.0, 9.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = Math.sqrt(2);

        double actualResult = seriesAnalysis.deviation();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testMin() {
        double[] temperatureSeries = {-5.0, 6.0, -7.0, 8.0, 9.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -7.0;

        double actualResult = seriesAnalysis.min();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testMax() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 8.0;

        double actualResult = seriesAnalysis.max();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testFindTempClosestToZero() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 5.0;

        double actualResult = seriesAnalysis.findTempClosestToZero();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testFindTempClosestToValue() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 5.0;

        double actualResult = seriesAnalysis.findTempClosestToValue(3.0);

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testFindTempsLessThen() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double[] expResult = {-6.0, -9.0, -5.0};

        double[] actualResult = seriesAnalysis.findTempsLessThen(3.0);

        assertEquals(expResult.length, actualResult.length);
        for (int i = 0; i < actualResult.length; i++){
            assertEquals(expResult[i], actualResult[i], 0.00001);
        }
    }

    @Test
    public void testFindTempsGreaterThen() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double[] expResult = {7.0, 8.0};

        double[] actualResult = seriesAnalysis.findTempsGreaterThen(6.0);

        assertEquals(expResult.length, actualResult.length);
        for (int i = 0; i < actualResult.length; i++){
            assertEquals(expResult[i], actualResult[i], 0.00001);
        }
    }

    @Test
    public void testAddTemps() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResultMin = -19.0;
        double expResultMax = 11.0;

        seriesAnalysis.addTemps(11.0, -19.0);

        double actualResultMin = seriesAnalysis.min();
        double actualResultMax = seriesAnalysis.max();

        assertEquals(expResultMin, actualResultMin, 0.00001);
        assertEquals(expResultMax, actualResultMax, 0.00001);
    }

    @Test(expected = InputMismatchException.class)
    public void testAddTempsBadTemperature() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        seriesAnalysis.addTemps(-315.2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationEmptyAvg() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();

        seriesAnalysis.average();
    }

    @Test
    public void testSummaryStatistics() {
        double[] temperatureSeries = {5.0, -6.0, 7.0, 8.0, -9.0, -5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResultAvg = 0.0;

        double actualResult = seriesAnalysis.summaryStatistics().avgTemp;

        assertEquals(expResultAvg, actualResult, 0.00001);
    }
}
