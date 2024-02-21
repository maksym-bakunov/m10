package org.example.calculate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.example.calculate.Calculate;

public class CalculateDescSubsequenceLength implements Calculate<Map<CalculationTypes, Integer>> {
    @Override
    public Map<CalculationTypes, Integer> calculateArray(ArrayList<Long> arrayList, int start, int end) {
        int [][] arr = {{0, 0}, {0, 1}};

        for (int i = 1; i < arrayList.size(); i++) {
            if (arrayList.get(i) < arrayList.get(i - 1)) {
                arr[1][1]++;
            } else {
                if (arr[1][1] > arr[0][1]) {
                    arr[0][0] = arr[1][0];
                    arr[0][1] = arr[1][1];
                }

                arr[1][0] = i;
                arr[1][1] = 1;
            }
        }

        if (arr[1][1] > arr[0][1]) {
            arr[0][0] = arr[1][0];
            arr[0][1] = arr[1][1];
        }

        Map<CalculationTypes, Integer> result = new HashMap<>();
        result.put(CalculationTypes.POSITION_MAX_SEQ_DESC, arr[0][0]);
        result.put(CalculationTypes.MAX_SEQ_DESC, arr[0][1]);

        return result;
    }

    @Override
    public CalculationTypes getCalculationType() {
        return CalculationTypes.MAX_SEQ_DESC;
    }
}
