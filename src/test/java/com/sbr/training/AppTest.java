package com.sbr.training;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class AppTest {
    private static final String STR_TEST_1 = "} close";
    private static final String STR_TEST_2 = "close";

    private static final String STR_TEST_3 = "{} close";
    private static final String STR_TEST_4 = "{ close";

    @Test
    public void testIfClosingBrace() {
        Analyzer analyzer = new Analyzer();
        {
            List<String> dirtText = List.of(STR_TEST_1);
            List<String> actual = analyzer.analyse(dirtText);

            List<String> expected = List.of(new String[]{"}", "close"});

            assertEquals(expected, actual);
        }

        {
            List<String> dirtText = List.of(STR_TEST_2);
            List<String> actual = analyzer.analyse(dirtText);

            List<String> expected = List.of(new String[]{"close"});

            assertEquals(expected, actual);
        }

        {
            List<String> dirtText = List.of(STR_TEST_3);
            List<String> actual = analyzer.analyse(dirtText);

            List<String> expected = List.of(new String[]{"{", "}", "close"});

            assertEquals(expected, actual);
        }

        {
            List<String> dirtText = List.of(STR_TEST_4);
            List<String> actual = analyzer.analyse(dirtText);

            List<String> expected = List.of(new String[]{"{", "    close"});

            assertEquals(expected, actual);
        }
    }
}