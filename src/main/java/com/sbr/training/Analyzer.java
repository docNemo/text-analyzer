package com.sbr.training;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private final static String INDENT = "    ";
    private final static String SEMICOLON = ";";
    private final static String OPENING_BRACE = "{";
    private final static String CLOSING_BRACE = "}";

    public List<String> analyse(List<String> text) {
        ArrayList<String> cleanText = new ArrayList<>();
        int nestingLevel = 0;
        for (String rawStr : text) {
            List<String> readyString = getString(rawStr);
            for (String str : readyString) {

                if (str.endsWith(CLOSING_BRACE)) {
                    nestingLevel--;
                }

                if (cleanText.size() > 0
                        && !cleanText.get(cleanText.size() - 1).endsWith(SEMICOLON)
                        && !cleanText.get(cleanText.size() - 1).endsWith(OPENING_BRACE)
                        && !cleanText.get(cleanText.size() - 1).endsWith(CLOSING_BRACE)) {
                    cleanText.set(cleanText.size() - 1, cleanText.get(cleanText.size() - 1) + " " + str);
                } else {
                    cleanText.add(getIndent(nestingLevel) + str);
                }
                if (str.endsWith(OPENING_BRACE)) {
                    nestingLevel++;
                }

            }
        }
        return cleanText;
    }

    private List<String> getString(String inputString) {
        inputString = inputString.trim();
        int indexLastSplit = 0;
        ArrayList<String> finishedString = new ArrayList<>();
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == SEMICOLON.charAt(0) || inputString.charAt(i) == OPENING_BRACE.charAt(0)) {
                finishedString.add(inputString.substring(indexLastSplit, i + 1).trim());
                indexLastSplit = i + 1;
            } else if (inputString.charAt(i) == CLOSING_BRACE.charAt(0)) {
                String subStr = inputString.substring(indexLastSplit, i).trim();
                if (!subStr.isEmpty()) {
                    finishedString.add(subStr);
                }
                finishedString.add(CLOSING_BRACE);
                indexLastSplit = i + 1;
            }
        }
        if (indexLastSplit < inputString.length()) {
            finishedString.add(inputString.substring(indexLastSplit).trim());
        }
        return finishedString;
    }

    private String getIndent(int nestingLevel) {
        return INDENT.repeat(nestingLevel);
    }
}
