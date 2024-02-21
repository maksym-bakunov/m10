package org.example.servises.method.median.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.example.calculate.impl.CalculationTypes;
import org.example.servises.method.median.MedianMethod;

public class MedianSortMethod extends MedianMethod {
    public MedianSortMethod(ArrayList<Long> arrayList) {
        super(arrayList);
    }

    @Override
    public Map<CalculationTypes, Double> get() {
        double median = arr[arr.length / 2];

        Arrays.sort(arr);

        median = arr[arr.length / 2];

        if (arr.length % 2 == 0) {
            median = (median + arr[arr.length / 2 - 1]) / 2;
        }

        Map<CalculationTypes, Double> results = new HashMap<>();
        results.put(CalculationTypes.MEDIAN, median);
        return results;
    }
}
