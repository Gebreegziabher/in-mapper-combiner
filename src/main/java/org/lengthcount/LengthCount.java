package org.lengthcount;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LengthCount {
    private InMapperLengthCount[] mappers;
    private ReducerLengthCount[] reducers;

    public LengthCount(int mappers, int reducers) {
        this.mappers = new InMapperLengthCount[mappers];
        this.reducers = new ReducerLengthCount[reducers];

        for (int i = 0; i < mappers; i++)
            this.mappers[i] = new InMapperLengthCount();
        for (int i = 0; i < reducers; i++)
            this.reducers[i] = new ReducerLengthCount();
    }

    public void setMappers(List<List<String>> tokenSubLists) {
        for (int i = 0; i < mappers.length; i++)
            mappers[i] = new InMapperLengthCount(tokenSubLists.get(i));
    }

    public InMapperLengthCount[] getMappers() {
        return mappers;
    }

    public void setReducers(InMapperLengthCount[] mappers) {
        List<Pair<Integer, Pair<Character, Pair<Integer, Integer>>>> pairsInReducers = new ArrayList<>();
        for (int i = 0; i < this.mappers.length; i++) {
            List<Pair<Integer, Pair<Character, Pair<Integer, Integer>>>> pairsInReducersPerMapper = new ArrayList<>();
            for (Pair pair : mappers[i].getPairs()) {
                int partition = getPartition(pair.getKey()+"");
                pairsInReducersPerMapper.add(new Pair(partition, pair));
            }
            pairsInReducers.addAll(pairsInReducersPerMapper);

            //TODO: NOTE - below lines are added only for the purpose of showing the output. CAN BE REMOVED
            //START
            System.out.println("\nPAIRS SENT FROM MAPPER " + i + " TO \n");
            var groups = pairsInReducersPerMapper.stream().collect(Collectors.groupingBy(f -> f.getKey()));
            for (Map.Entry<Integer, List<Pair<Integer, Pair<Character, Pair<Integer, Integer>>>>> entry : groups.entrySet()) {
                System.out.println("REDUCER " + entry.getKey());
                System.out.println(entry.getValue().stream().map(m -> m.getValue()).collect(Collectors.toList()).toString());
            }
            //END
        }
        var groupedByReducerIndex = pairsInReducers.stream().sorted(Comparator.comparing(s -> s.getKey())).collect(Collectors.groupingBy(f -> f.getKey()));
        for (Map.Entry<Integer, List<Pair<Integer, Pair<Character, Pair<Integer, Integer>>>>> entry : groupedByReducerIndex.entrySet()) {
            List<Pair<Character, Pair<Integer, Integer>>> pairs = new ArrayList<>();
            for (Pair<Integer, Pair<Character, Pair<Integer, Integer>>> value : entry.getValue()) {
                pairs.add(value.getValue());
            }
            this.reducers[entry.getKey()] = new ReducerLengthCount(pairs);
        }
    }

    public ReducerLengthCount[] getReducers() {
        return reducers;
    }

    public void printMapperOutputs() {
        System.out.println("\nALL MAPPERS OUTPUTS\n");
        for (int i = 0; i < mappers.length; i++) {
            System.out.println("MAPPER " + i);
            System.out.println(mappers[i].getPairs());
        }
    }

    public void printReducersInputs() {
        System.out.println("\nREDUCER INPUTS\n");
        for (int i = 0; i < reducers.length; i++) {
            System.out.println("Reducer " + i + " input");
            System.out.println(reducers[i].getGroupByPair());
        }
    }

    public void printReducersOutputs() {
        System.out.println("\nREDUCER OUTPUTS\n");
        for (int i = 0; i < reducers.length; i++) {
            System.out.println("Reducer " + i + " output");
            System.out.println(reducers[i].getReducedPairs());
        }
    }

    public int getPartition(String key) {
        return Math.abs((int) key.hashCode()) % reducers.length;
    }
}
