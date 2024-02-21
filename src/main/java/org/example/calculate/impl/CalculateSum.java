package org.example.calculate.impl;

import java.util.ArrayList;
import org.example.calculate.CalculateAggregateValue;

public class CalculateSum implements CalculateAggregateValue<Long> {
    @Override
    public Long calculateArray(ArrayList<Long> arrayList, int start, int end) {
        long sum = 0L;

        for (int i = start; i <= end; i++) {
            sum += arrayList.get(i);
        }

        return sum;
    }

    @Override
    public Long calculate(Long value1, Long value2) {
        return value1 + value2;
    }

    @Override
    public CalculationTypes getCalculationType() {
        return CalculationTypes.SUM;
    }
}
