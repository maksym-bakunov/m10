package org.example;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import org.example.calculate.Calculate;
import org.example.calculate.CalculateAggregateValue;
import org.example.calculate.impl.CalculateAggregateValues;
import org.example.calculate.impl.CalculateAscSubsequenceLength;
import org.example.calculate.impl.CalculateDescSubsequenceLength;
import org.example.calculate.impl.CalculateMaxValue;
import org.example.calculate.impl.CalculateMinValue;
import org.example.calculate.impl.CalculateSum;
import org.example.calculate.impl.CalculationTypes;
import org.example.servises.io.impl.ReadFileImpl;
import org.example.servises.method.median.impl.MedianOfMedianMethod;
import org.example.servises.method.median.impl.MedianSortMethod;

public class Main {

    public static void main(String[] args) {
        boolean detailedOutput = false;
        boolean medianSortCalculation = false;

        if (args.length == 0) {
            System.out.println("File is not specified." + System.lineSeparator());
            return;
        }

        if (args.length > 3) {
            System.out.println("Too many parameters!");
            return;
        }

        File fileInput = new File(args[0]);
        if (!fileInput.exists()) {
            System.out.println("incorrect file path.");
            return;
        }

        for (int n = 1; n < args.length; n++) {
            if (args[n].equals("-v")) {
                detailedOutput = true;
            } else if (args[n].equals("-ms")) {
                medianSortCalculation = true;
            } else {
                System.out.println(args[1] + " incorrect parameter value.");
                return;
            }
        }

        System.out.println("start: " + LocalDateTime.now() + System.lineSeparator());

        ArrayList<Long> arrayList = new ReadFileImpl().read(fileInput);
        if (arrayList.size() == 0) {
            System.out.println("No data in file.");
            return;
        }

        List<CompletableFuture<? extends Map<CalculationTypes, ? extends Number>>> completableFutureList = new ArrayList<>();

        //Calculation of min, max, sum, avg
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        CompletableFuture<Map<CalculationTypes, Long>> future = CompletableFuture.supplyAsync(() -> {
            List<CalculateAggregateValue<Long>> calculateAggregateValueList = new ArrayList<>();
            calculateAggregateValueList.add(new CalculateMinValue());
            calculateAggregateValueList.add(new CalculateMaxValue());
            calculateAggregateValueList.add(new CalculateSum());

            CalculateAggregateValues calculateAggregateValues =
                    new CalculateAggregateValues(arrayList, calculateAggregateValueList);

            return forkJoinPool.invoke(calculateAggregateValues);
        });

        completableFutureList.add(future);

        completableFutureList.add(future.thenApply(f -> {
            Map<CalculationTypes, Double> mapAvg = new HashMap<>();
            mapAvg.put(CalculationTypes.AVG, ((double)f.get(CalculationTypes.SUM) / arrayList.size()));

            return mapAvg;
        }));

        //Calculation subsequence (asc) length
        completableFutureList.add(CompletableFuture.supplyAsync(() -> {
            Calculate<Map<CalculationTypes, Integer>> calculateAscSubsequenceLength = new CalculateAscSubsequenceLength();

            return calculateAscSubsequenceLength.calculateArray(arrayList, 0, arrayList.size() - 1);
        }));

        //Calculation subsequence (desc) length
        completableFutureList.add(CompletableFuture.supplyAsync(() -> {
            Calculate<Map<CalculationTypes, Integer>> calculateDescSubsequenceLength = new CalculateDescSubsequenceLength();

            return calculateDescSubsequenceLength.calculateArray(arrayList, 0, arrayList.size() - 1);
        }));

        //Calculation of median
        if (medianSortCalculation) {
            completableFutureList.add(CompletableFuture.supplyAsync(new MedianOfMedianMethod(arrayList)));
        } else {
            completableFutureList.add(CompletableFuture.supplyAsync(new MedianSortMethod(arrayList)));
        }

        CompletableFuture<Map<CalculationTypes, ? extends Number>>[] completableFutures =
                completableFutureList.toArray(new CompletableFuture[0]);
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFutures);
        allFutures.join();

        //print
        try {
            for (int i = 0; i < completableFutures.length; i++) {
                for (Map.Entry<CalculationTypes, ? extends Number> e : completableFutures[i].get().entrySet()) {
                    if (e.getKey().isOptionalValue()) {
                        System.out.println(e.getKey().getCaption() + ": " + e.getValue());

                        if (e.getKey() == CalculationTypes.MAX_SEQ_ASC && detailedOutput) {
                            printSequence(arrayList, (int)completableFutures[i].get().get(CalculationTypes.POSITION_MAX_SEQ_ASC),
                                    (int)e.getValue());
                        }
                        if (e.getKey() == CalculationTypes.MAX_SEQ_DESC && detailedOutput) {
                            printSequence(arrayList, (int)completableFutures[i].get().get(CalculationTypes.POSITION_MAX_SEQ_DESC),
                                    (int)e.getValue());
                        }
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println(System.lineSeparator() + "end: " + LocalDateTime.now());
    }

    static void printSequence(ArrayList<Long> arrayList, int start, int length) {
        for (int i = start; i < start + length; i++) {
            System.out.println(arrayList.get(i));
        }
    }

}
