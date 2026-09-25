package com.maslennikova.labs;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //
        List<Character> allCharacters = new ArrayList<>();

        try {

            File file = new File("lab-1/src/main/resources/characters.csv");
            Scanner scanner = new Scanner(file);

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(",");
                String name = parts[1];
                String status = parts[2];
                String species = parts[3];
                String gender = parts[5];

                Character character = new Character(name, status, species, gender);
                allCharacters.add(character);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: file characters.csv don't find");
            return;
        }

        Map<String, List<Character>> groupedByGender = new HashMap<>();

        for (Character c : allCharacters) {
            String gender = c.gender;

            if (!groupedByGender.containsKey(gender)) {
                groupedByGender.put(gender, new ArrayList<>());
            }

            groupedByGender.get(gender).add(c);
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