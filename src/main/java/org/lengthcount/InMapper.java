package org.lengthcount;

import java.util.ArrayList;
import java.util.List;

public class InMapper {
    private List<Pair<Character, Pair<Integer, Integer>>> pairs;

    public InMapper() {
        pairs = new ArrayList<>();
    }

    public InMapper(List<String> tokens) {
        pairs = new ArrayList<>();
        map(tokens);
    }

    public List<Pair<Character, Pair<Integer, Integer>>> getPairs() {
        return pairs;
    }

    public void map(List<String> tokens) {
        for (String token : tokens) {
            var optionalPair = pairs.stream().filter(f -> f.getKey().equals(token.charAt(0))).findFirst();
            if (!optionalPair.isPresent())
                pairs.add(
                        new Pair(
                                token.charAt(0),
                                new Pair(token.toLowerCase().length(), 1)
                        )
                );
            else
                optionalPair.get().setValue(
                        new Pair(
                                optionalPair.get().getValue().getKey() + token.toLowerCase().length(),
                                optionalPair.get().getValue().getValue() + 1
                        )
                );
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
