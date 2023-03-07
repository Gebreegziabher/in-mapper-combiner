package org.lengthcount;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reducer {
    private List<Pair<Character, List<Pair<Integer, Integer>>>> reducerInput;
    private List<Pair> reducedPairs;

    public Reducer() {
        this.reducerInput = new ArrayList<>();
        this.reducedPairs = new ArrayList<>();
    }

    public Reducer(List<Pair<Character, Pair<Integer, Integer>>> pairs) {
        this.reducerInput = group(pairs);
        this.reducedPairs = reduce(reducerInput);
    }

    public List<Pair<Character, List<Pair<Integer, Integer>>>> getReducerInput() {
        return reducerInput;
    }

    public List<Pair> getReducedPairs() {
        return reducedPairs;
    }

    private List<Pair<Character, List<Pair<Integer, Integer>>>> group(List<Pair<Character, Pair<Integer, Integer>>> pairs) {

        Map<Character, List<Pair<Character, Pair<Integer, Integer>>>> grouped = pairs.stream().collect(Collectors.groupingBy(f -> f.getKey()));

        List<Pair<Character, List<Pair<Integer, Integer>>>> output = new ArrayList<>();
        for (Map.Entry<Character, List<Pair<Character, Pair<Integer, Integer>>>> entry : grouped.entrySet()) {
            List<Pair<Integer, Integer>> values = new ArrayList<>();
            for (Pair<Character, Pair<Integer, Integer>> pair : entry.getValue())
                values.add(pair.getValue());
            output.add(new Pair(entry.getKey(), values));
        }
        return output.stream().sorted(Comparator.comparing(s -> s.getKey())).collect(Collectors.toList());
    }

    private List<Pair> reduce(List<Pair<Character, List<Pair<Integer, Integer>>>> input) {
        List<Pair<Character, Double>> output = new ArrayList<>();

        for (Pair<Character, List<Pair<Integer, Integer>>> entry : input) {
            var value = entry.getValue();
            double average = value.stream().mapToDouble(i -> i.getKey().doubleValue()).sum()/value.stream().mapToDouble(i -> i.getValue()).sum();
            output.add(new Pair(entry.getKey(), average));
        }
        return output.stream().sorted(Comparator.comparing(s -> s.getKey())).collect(Collectors.toList());
    }

    public void printGroupedPairs() {
        System.out.println("\nREDUCER INPUT\n");
        System.out.println(reducerInput.toString());
    }

    public void printReducedPairs() {
        System.out.println("\nREDUCER OUTPUT\n");
        System.out.println(reducedPairs.toString());
    }
}
