package org.wordcount;

import java.util.ArrayList;
import java.util.List;

public class InMapperWordCount {
    private List<Pair<String,Integer>> pairs;

    public InMapperWordCount() {
        pairs = new ArrayList<>();
    }

    public InMapperWordCount(List<String> tokens) {
        pairs = new ArrayList<>();
        map(tokens);
    }

    public List<Pair<String,Integer>> getPairs() {
        return pairs;
    }

    public void map(List<String> tokens) {
        for (String token : tokens) {
            var optionalPair = pairs.stream().filter(f -> f.getKey().equals(token)).findFirst();
            if (!optionalPair.isPresent())
                pairs.add(new Pair(token.toLowerCase(), 1));
            else
                optionalPair.get().setValue((Integer)optionalPair.get().getValue() + 1);
        }
    }

    @Override
    public String toString() {
        return pairs.toString();
    }

    public void printMapperOutput() {
        System.out.println("MAPPER OUTPUT\n");
        System.out.println(pairs.toString());
    }
}
