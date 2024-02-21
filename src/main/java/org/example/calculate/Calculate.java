package org.example.calculate;

import java.util.ArrayList;
import org.example.calculate.impl.CalculationTypes;

public interface Calculate<T> {
    T calculateArray(ArrayList<Long> arr, int start, int end);

    CalculationTypes getCalculationType();
}
