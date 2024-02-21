package org.example.calculate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import org.example.calculate.CalculateAggregateValue;

public class CalculateAggregateValues extends RecursiveTask<Map<CalculationTypes, Long>> {
    private static final int THRESHOLD = 25000;
    private final ArrayList<Long> arrayList;
    private final int begin;
    private final int end;
    private final List<CalculateAggregateValue<Long>> calculateAggregateValueList;
    private final Map<CalculationTypes, Long> results = new HashMap<>();

    public CalculateAggregateValues(ArrayList<Long> arrayList,
                                    List<CalculateAggregateValue<Long>> calculateAggregateValueList) {
        this.arrayList = arrayList;
        this.begin = 0;
        this.end = arrayList.size() - 1;
        this.calculateAggregateValueList = calculateAggregateValueList;
    }

    public CalculateAggregateValues(ArrayList<Long> arrayList, int begin, int end,
                                    List<CalculateAggregateValue<Long>> calculateAggregateValueList) {
        this.arrayList = arrayList;
        this.begin = begin;
        this.end = end;
        this.calculateAggregateValueList = calculateAggregateValueList;
    }

    @Override
    protected Map<CalculationTypes, Long> compute() {

        if ((end - begin) < THRESHOLD) {
            for (CalculateAggregateValue<Long> e : calculateAggregateValueList) {
                results.put(e.getCalculationType(), e.calculateArray(arrayList, begin, end));
            }

        } else {
            int middle = begin + (end - begin) / 2;
            CalculateAggregateValues leftTask = new CalculateAggregateValues(arrayList, begin,
                    middle, calculateAggregateValueList);
            CalculateAggregateValues rightTask = new CalculateAggregateValues(arrayList, middle + 1,
                    end, calculateAggregateValueList);

            leftTask.fork();
            rightTask.fork();

            Map<CalculationTypes, Long> leftResult = leftTask.join();
            Map<CalculationTypes, Long> rightResult = rightTask.join();

            for (CalculateAggregateValue<Long> e : calculateAggregateValueList) {
                results.put(e.getCalculationType(), e.calculate(leftResult.get(e.getCalculationType()),
                        rightResult.get(e.getCalculationType())));
            }

        }
        return results;
    }
}
