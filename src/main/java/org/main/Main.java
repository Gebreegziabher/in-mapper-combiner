package org.main;

import org.lengthcount.LengthCount;
import org.util.Util;
import org.wordcount.WordCount;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File file = new File("testDataForW1D1.txt");

        var tokens = Util.getValidTokens(file);

        /**
         LAB 4 - QUESTION 1
         */
        WordCount wordCount = new WordCount(4, 3);

        System.out.println("\n\nLAB 4 - QUESTION 1\n________________\n");

        wordCount.setMappers(Util.getSubLists(tokens, wordCount.getMappers().length));
        wordCount.printMapperOutputs();

        wordCount.setReducers(wordCount.getMappers());
        wordCount.printReducersInputs();
        wordCount.printReducersOutputs();

        /**
         LAB 4 - QUESTION 2
         */
        LengthCount lengthCounter = new LengthCount(4, 3);

        System.out.println("\n\nLAB 4 - QUESTION 2\n________________\n");

        File file1 = new File("File1.txt");
        File file2 = new File("File2.txt");
        File file3 = new File("File3.txt");
        File file4 = new File("File4.txt");

        List<List<String>> subLists = new ArrayList<>();
        subLists.add(Util.getValidTokens(file1));
        subLists.add(Util.getValidTokens(file2));
        subLists.add(Util.getValidTokens(file3));
        subLists.add(Util.getValidTokens(file4));

        lengthCounter.setMappers(subLists);
        lengthCounter.printMapperOutputs();

        lengthCounter.setReducers(lengthCounter.getMappers());
        lengthCounter.printReducersInputs();
        lengthCounter.printReducersOutputs();
    }
}