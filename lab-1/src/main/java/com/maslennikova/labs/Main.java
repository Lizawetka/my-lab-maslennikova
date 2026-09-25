package com.maslennikova.labs;

import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[]args) {
        List<Character> allCharacters = new ArrayList<>();
        try{
            File file = new File("lab-1/src/main/resourses/characters.csv");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            while(scanner.hasNextLine()){
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
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: файл characters.csv не найден/");
            return;
        }

        Map<String, List<Character>> groupedByGender = new HashMap<>();
        for (Character c : allCharacters) {
            String gender = c.gender;

            if (!groupedByGender.containsKey(gender)) {
                groupedByGender.put(gender, new ArrayList<>());
            }

            groupedByGender.get(gender).add(c);
            try {
                File outputFile = new File("lab-1/src/main/resources/grouped_by_gender.txt");
                FileWriter writer = new FileWriter(outputFile);

                for (String gender : groupedByGender.keySet()) {
                    List<Character> list = groupedByGender.get(gender);

                    writer.write(gender + " (" + list.size() + " чел.):\n");

                    for (Character c : list) {
                        writer.write("- " + c + "\n");
                    }
                    writer.close();
                    System.out.println("Готовый результат записан в group_by_gender.txt");
                }
                catch(IOException e){
                    System.out.println("Ошибка при записи файла: " + e.getMessage());
                }
            }
        }
    }
}