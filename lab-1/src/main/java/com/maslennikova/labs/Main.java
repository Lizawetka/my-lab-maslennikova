package com.maslennikova.labs;

import java.io.*;
import java.util.*;

public class Main {
    private static final String INPUT_FILE = "lab-1/src/main/resources/characters.csv";
    private static final String OUT_FILE = "lab-1/src/main/resources/grouped_by_gender.csv";

    public static void main(String[] args) {
        List<Character> characters = readCharactersFromFile(INPUT_FILE);
        Map<String, List<Character>> groupByGender = groupByGender(characters);
        writeResultToFile(groupByGender, OUT_FILE);
    }

    private static List<Character> readCharactersFromFile(String filePath) {
        List<Character> characters = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[1];
                String status = parts[2];
                String species = parts[3];
                String gender = parts[5];
                characters.add(new Character(name, status, species, gender));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error with read file: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return characters;
    }
    private static Map<String, List<Character>> groupByGender(List<Character> characters) {
        Map<String, List<Character>> grouped = new HashMap<>();
        for (Character c : characters) {
            String gender = c.getGender();
            grouped.computeIfAbsent(gender, k -> new ArrayList<>()).add(c);
        }
        return grouped;
    }

    private static void writeResultToFile(Map<String, List<Character>> grouped, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("gender,name,status,species\n");
            for (Map.Entry<String, List<Character>> entry : grouped.entrySet()) {
                String gender = entry.getKey();
                for (Character c : entry.getValue()) {
                    writer.write(gender + "," + c.getName() + "," + c.getStatus() + "," + c.getSpecies() + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println(" Error whith write file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}


