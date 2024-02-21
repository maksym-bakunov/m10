package org.example.servises.method.median;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;
import org.example.calculate.impl.CalculationTypes;

public abstract class MedianMethod implements Supplier<Map<CalculationTypes, ? extends Number>> {
    protected final long[] arr;

    public MedianMethod(ArrayList<Long> arrayList) {
        arr = new long[arrayList.size()];

        for (int idx = 0; idx < arrayList.size(); idx++) {
            arr[idx] = arrayList.get(idx);
        }
    }

    @Override
    public abstract Map<CalculationTypes, Double> get();
}
