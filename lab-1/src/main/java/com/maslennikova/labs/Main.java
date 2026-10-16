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

    private static List<Character> readCharactersFromFile(String filePath) {
        List<Character> characters = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // пропускаем заголовок
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
            System.err.println("Файл не найден: " + filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return characters;
    }




