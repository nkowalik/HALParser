package com.example;

import java.io.*;
import java.util.ArrayList;
public class Main {
    static String dictionaryPath = ""; // add your path to dictionary
    static String inputPath = ""; // add your path to results from HAL
    static String outputPath = "tokens.txt";

    public static void main(String[] args) {
        parseResults();
    }

    public static void parseResults() {
        String arrow = " -> ";
        try {
            FileReader fileReader = new FileReader(inputPath);
            BufferedReader br = new BufferedReader(fileReader);
            String line;

            while ((line = br.readLine()) != null) {
                int arrowIdx = line.indexOf(arrow);
                if (arrowIdx == -1) continue;
                String token = findTokenById(line, arrowIdx);
                String vector = line.substring(arrowIdx+4);
                saveTokenToFile(token + arrow + vector + "\n");
            }

        } catch (IOException e) {
            System.out.println("Problem with reading input file: " + e);
        }
    }

    static String findTokenById(String token, int lastIdx) {
        String tokenId = token.substring(0, lastIdx);
        try {
            FileReader fileReader = new FileReader(dictionaryPath);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                if (s[1].equals(tokenId)) {
                    return s[0];
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with reading dictionary file: " + e);
        }

        return "";
    }

    static ArrayList<Integer> findTokensVector(String line, int firstIdx) {
        String vector = line.substring(firstIdx);
        String[] vec = vector.split(",");
        ArrayList<Integer> resultVec = new ArrayList<>();
        for (String v : vec) {
            resultVec.add(Integer.getInteger(v));
        }
        return resultVec;
    }

    static void saveTokenToFile(String token) {
        try {
            File f1 = new File(outputPath);
            if(!f1.exists()) {
                f1.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(f1.getName(), true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(token);
            bw.close();

        } catch (IOException e) {
            System.out.println("Problem occurred while writing to file: " + e);
        }
    }
}
