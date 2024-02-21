package org.example.calculate.impl;

import java.util.Arrays;

public class CalculateMedian {
    public long calculate(long[] arr, int low, int high, int k) {
        if (low > high || k < 0 || k > high - low + 1) {
            return -1;
        }

        int threshold = 5;
        int lengthLastRange;
        long medianOfMedians;
        int i;
        int n = high - low + 1;

        long[] medians = new long[(n + 4) / 5];

        for (i = 0; i < medians.length - 1; i++) {
            medians[i] = getMedian(Arrays.copyOfRange(arr, threshold * i + low, threshold * i + low + threshold));
        }

        if (n % threshold == 0) {
            lengthLastRange = threshold;
        } else {
            lengthLastRange = n % threshold;
        }

        medians[i] = getMedian(Arrays.copyOfRange(arr, threshold * i + low, threshold * i + low + lengthLastRange));
        i++;

        if (i == 1) {
            medianOfMedians = medians[i - 1];
        } else {
            medianOfMedians = calculate(medians, 0, i - 1, i / 2);
        }

        int partition = partition(arr, low, high, medianOfMedians);

        if (partition - low == k - 1) {
            return arr[partition];
        }

        if (partition - low > k - 1) {
            return calculate(arr, low, partition - 1, k);
        }

        return calculate(arr, partition + 1, high, k - (partition + 1) + low);
    }

    private long getMedian(long[] arr) {
        Arrays.sort(arr);
        return arr[arr.length / 2];
    }

    private void change(long[] arr, int i, int index) {
        long t;
        if (arr[i] == arr[index]) {
            return;
        }

        t = arr[i];
        arr[i] = arr[index];
        arr[index] = t;
    }

    private int partition(long[] arr, int low, int high, long pivot) {
        int step = 0;

        for (int i = low; i < high; i++) {
            if (arr[i] < pivot && step > 0) {
                change(arr, i - step, i);
            } else if (arr[i] == pivot && arr[high] != pivot) {
                change(arr, high, i);
                i--;
            } else if (arr[i] >= pivot) {
                step++;
            }
        }

        if (step > 0) {
            change(arr, high, high - step);
        }

        return high - step;
    }
}
