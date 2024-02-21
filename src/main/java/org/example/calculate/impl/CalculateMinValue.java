package org.example.calculate.impl;

import java.util.ArrayList;
import org.example.calculate.CalculateAggregateValue;

public class CalculateMinValue implements CalculateAggregateValue<Long> {
    @Override
    public Long calculateArray(ArrayList<Long> arrayList, int start, int end) {
        long minValue = arrayList.get(start);

        for (int i = start + 1; i <= end; i++) {
            if (arrayList.get(i) < minValue) {
                minValue = arrayList.get(i);
            }
        }

        return minValue;
    }

    @Override
    public Long calculate(Long value1, Long value2) {
        return Math.min(value1, value2);
    }

    @Override
    public CalculationTypes getCalculationType() {
        return CalculationTypes.MIN;
    }
}
