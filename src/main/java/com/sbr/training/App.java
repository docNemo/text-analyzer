package com.sbr.training;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class App {
    private final static String PATH_TO_FILE_JAVA = "src/program.java";
    private final static String EXAMPLE_PROGRAM = "aaa; { bbbb; ccc;}";
    private final static String DELIMITER = " ";

    public static void main(String[] args) throws AppException {

        List<String> text = readJavaCodeFromFile(PATH_TO_FILE_JAVA);
        Analyzer analyzerJavaFile = new Analyzer();
        List<String> cleanText = analyzerJavaFile.analyse(text);

        for (String str : cleanText) {
            System.out.println(str);
        }

//        List<String> text2 = readJavaCodeFromString(EXAMPLE_PROGRAM);
//        List<String> cleanText2 = analyzerJavaFile.analyse(text2);
//
//        for (String str : cleanText2) {
//            System.out.println(str);
//        }
    }

    private static List<String> readJavaCodeFromFile(String pathToFile) {
        ArrayList<String> text = new ArrayList<>();
        try (
                FileInputStream javaFileInputStream = new FileInputStream(PATH_TO_FILE_JAVA);
                InputStreamReader javaInputStreamReader = new InputStreamReader(javaFileInputStream);
                BufferedReader javaFileReader = new BufferedReader(javaInputStreamReader)
        ) {
            while (javaFileReader.ready()) {
                text.add(javaFileReader.readLine());
            }
        } catch (IOException e) {
            throw new AppException("Error while reading java file: ", e);
        }
        return text;
    }

    private static List<String> readJavaCodeFromString(String javaCode) {
        return new ArrayList<>(List.of(EXAMPLE_PROGRAM.split(" ")));
    }

    private static void writeToFile(List<String> text) {
        try (
                FileOutputStream javaFileOutputStream = new FileOutputStream(PATH_TO_FILE_JAVA);
                OutputStreamWriter javaOutputStreamReader = new OutputStreamWriter(javaFileOutputStream);
                BufferedWriter javaFileWriter = new BufferedWriter(javaOutputStreamReader)
        ) {
            for (String str : text) {
                javaFileWriter.write(str);
                javaFileWriter.write("\n");
            }

        } catch (IOException e) {
            throw new AppException("Error while writing clean java file: ", e);
        }
    }
}
