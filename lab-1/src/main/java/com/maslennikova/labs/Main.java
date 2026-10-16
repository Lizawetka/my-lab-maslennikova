package com.maslennikova.labs;

import java.io.*;
import java.util.*;

public class Main {
    private static final String INPUT_FILE = "lab-1/src/main/resources/characters.csv";
    private static final String OUT_FILE = "lab-1/src/main/resources/grouped_by_gender.csv";

    public static void main(String[]args) {
        List<Character> characters = readCharactersFromFile (INPUT_FILE);
        Map<String, List<Character>> groupedByGender =  groupByGender (characters);
writeResultToFile(groupByGender, OUT_FILE);
    }



        try {
            File outputFile = new File("lab-1/src/main/resources/grouped_by_gender.csv");
            //
            FileWriter writer = new FileWriter(outputFile);
            writer.write("gender,name,status,species\n");

            for (String gender : groupedByGender.keySet()) {
                List<Character> list = groupedByGender.get(gender);

                for (Character c : list) {
                    writer.write(gender + "," + c.name + "," + c.status + "," + c.species + "\n");
                }
            }

            writer.close();
            System.out.println("Result in file group_by_gender.csv");

        } catch (IOException e) {
            System.out.println("Error while writing file: " + e.getMessage());
        }
    }
}