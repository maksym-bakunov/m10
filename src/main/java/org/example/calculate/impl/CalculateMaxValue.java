package org.example.calculate.impl;

import java.util.ArrayList;
import org.example.calculate.CalculateAggregateValue;

public class CalculateMaxValue implements CalculateAggregateValue<Long> {
    @Override
    public Long calculateArray(ArrayList<Long> arrayList, int start, int end) {
        long maxValue = arrayList.get(start);

        for (int i = start + 1; i <= end; i++) {
            if (arrayList.get(i) > maxValue) {
                maxValue = arrayList.get(i);
            }
        }

        return maxValue;
    }

    @Override
    public Long calculate(Long value1, Long value2) {
        return Math.max(value1, value2);
    }

    @Override
    public CalculationTypes getCalculationType() {
        return CalculationTypes.MAX;
    }
}
