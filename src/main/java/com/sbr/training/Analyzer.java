package com.sbr.training;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private final static String INDENT = "    ";
    private final static String SPACE = " ";
    private final static String SEMICOLON = ";";
    private final static String OPENING_BRACE = "{";
    private final static String CLOSING_BRACE = "}";

    public List<String> analyse(List<String> text) {
        ArrayList<String> cleanText = new ArrayList<>();
        int nestingLevel = 0;

        for (String rawStr : text) {
            List<String> readyString = getReadyString(rawStr);
            for (String str : readyString) {

                if (nestingLevel > 0 && str.endsWith(CLOSING_BRACE)) {
                    nestingLevel--;
                }

                if (cleanText.size() > 0
                        && !cleanText.get(cleanText.size() - 1).endsWith(SEMICOLON)
                        && !cleanText.get(cleanText.size() - 1).endsWith(OPENING_BRACE)
                        && !cleanText.get(cleanText.size() - 1).endsWith(CLOSING_BRACE)) {
                    cleanText.set(cleanText.size() - 1, cleanText.get(cleanText.size() - 1) + SPACE + str);
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

    private List<String> getReadyString(String rawString) {
        rawString = rawString.trim();
        ArrayList<String> readyStrings = new ArrayList<>();
        int indexLastSplit = 0;
        for (int i = 0; i < rawString.length(); i++) {
            switch (rawString.charAt(i)) {
                case ';': {
                    readyStrings.add(rawString.substring(indexLastSplit, i).trim() + SEMICOLON);
                    rawString = rawString.substring(i + 1).trim();
                    i = -1;
                    break;
                }
                case '{': {
                    String baseStr = rawString.substring(indexLastSplit, i).trim().equals("") ? "" : rawString.substring(indexLastSplit, i).trim() + SPACE;
                    readyStrings.add(baseStr + OPENING_BRACE);
                    rawString = rawString.substring(i + 1).trim();
                    i = -1;
                    break;
                }
                case '}': {
                    readyStrings.add(CLOSING_BRACE);
                    rawString = rawString.substring(i + 1).trim();
                    i = -1;
                    break;
                }
                case ' ': {
                    readyStrings.add(rawString.substring(indexLastSplit, i));
                    rawString = rawString.substring(i + 1).trim();
                    i = -1;
                    break;
                }
                default: {
                    if (i == rawString.length() - 1) {
                        readyStrings.add(rawString);
                    }
                    break;
                }
            }


        }
        return readyStrings;
    }

    private String getIndent(int nestingLevel) {
        return INDENT.repeat(nestingLevel);
    }
}
