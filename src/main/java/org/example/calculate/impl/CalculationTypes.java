package org.example.calculate.impl;

public enum CalculationTypes {
    MIN("Min value", 1, true),
    MAX("Max value", 2, true),
    SUM("Sum", 3, false),
    AVG("Avg. value", 4, true),
    MEDIAN("Median value", 5, true),
    POSITION_MAX_SEQ_ASC("Sequence starting position (asc)", 6, false),
    MAX_SEQ_ASC("Max subsequence length (asc)", 7, true),
    POSITION_MAX_SEQ_DESC("Sequence starting position (desc)", 8, false),
    MAX_SEQ_DESC("Max subsequence length (desc)", 9, true);

    private final String caption;
    private final int code;
    private boolean optionalValue;

    CalculationTypes(String s, int i, boolean o) {
        caption = s;
        code = i;
        optionalValue = o;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isOptionalValue() {
        return optionalValue;
    }
}
