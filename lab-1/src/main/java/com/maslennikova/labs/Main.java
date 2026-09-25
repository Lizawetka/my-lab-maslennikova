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

    }
}