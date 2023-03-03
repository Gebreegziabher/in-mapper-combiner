package org.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Util<T> {
    public static String punctuationMarkRegex = "[a-zA-Z.,'\"?!{}()\\[\\]\\-:;]*";

    public static String removePunctuationMarks(String token) {
        boolean isTokenValid = Pattern.matches(punctuationMarkRegex, token);
        if (token.contains(";") && !token.endsWith(";"))
            isTokenValid = false;

        if (token.contains(".") && !token.endsWith("."))
            isTokenValid = false;

        if (token.contains("\"") && token.indexOf("\"") == token.lastIndexOf("\""))
            isTokenValid = false;

        if (token.contains("(") && token.indexOf("(") != 0)
            isTokenValid = false;

        if (token.contains(")") && token.indexOf(")") != token.length() - 1)
            isTokenValid = false;

        if (token.contains("{") && token.indexOf("{") != 0)
            isTokenValid = false;

        if (token.contains("}") && token.indexOf("}") != token.length() - 1)
            isTokenValid = false;

        if (token.contains("[") && token.indexOf("[") != 0)
            isTokenValid = false;

        if (token.contains("]") && token.indexOf("]") != token.length() - 1)
            isTokenValid = false;

        String output = "";
        if (isTokenValid) {
            for (Character c : token.toCharArray()) {
                if (Character.isLetter(c))
                    output += c;
            }
        }
        return output;
    }

    public static List<String> getValidTokens(File file) {
        List<String> tokens = new ArrayList<>();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] lineTokens = reader.nextLine().split("[ :-]");
                for (int i = 0; i < lineTokens.length; i++) {
                    String token = Util.removePunctuationMarks(lineTokens[i].trim());
                    if (!token.isEmpty())
                        tokens.add(token.toLowerCase());
                }
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return tokens;
    }

    public static List<List<String>> getSubLists(List<String> inputList, int splitSize) {
        List<List<String>> subLists = new ArrayList<>();
        int nextIndex = 0;
        int quotient = inputList.size() / splitSize;
        int remainder = inputList.size() % splitSize;
        for (int i = 0; i < splitSize; i++) {
            nextIndex = i * quotient;
            subLists.add(new ArrayList<>(inputList.subList(nextIndex, nextIndex + quotient)));
        }
        nextIndex = nextIndex + quotient;
        for (int i = 0; i < remainder; i++) {
            String nextToken = inputList.get(nextIndex + i);
            subLists.get(i).add(nextToken);
        }
        return subLists;
    }
}
