package org.example.calculate;

public interface CalculateAggregateValue<T extends Number> extends Calculate<T> {
    T calculate(T value1, T value2);
}
