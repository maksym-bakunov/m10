package org.example.servises.method.median.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.example.calculate.impl.CalculateMedian;
import org.example.calculate.impl.CalculationTypes;
import org.example.servises.method.median.MedianMethod;

public class MedianOfMedianMethod extends MedianMethod {

    public MedianOfMedianMethod(ArrayList<Long> arrayList) {
        super(arrayList);
    }

    @Override
    public Map<CalculationTypes, Double> get() {
        CalculateMedian calculateMedian = new CalculateMedian();
        double median = calculateMedian.calculate(arr, 0, arr.length - 1, arr.length / 2 + 1);

        if (arr.length % 2 == 0) {
            median = (median + calculateMedian.calculate(arr, 0, arr.length - 1, arr.length / 2)) / 2;
        }

        Map<CalculationTypes, Double> results = new HashMap<>();
        results.put(CalculationTypes.MEDIAN, median);

        //System.out.println("Calculation of median : " + Duration.between(startTime, LocalDateTime.now()));
        return results;
    }
}
