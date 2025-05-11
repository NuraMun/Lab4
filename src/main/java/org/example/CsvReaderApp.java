package org.example;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvReaderApp {
    private static final Map<String, Department> departmentCache = new HashMap<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) {
        String csvFilePath = "foreign_names.csv";
        char separator = ';';

        List<Person> people = readPeopleFromCsv(csvFilePath, separator);

        System.out.println("Total people read: " + people.size());
        System.out.println("\nSample records:");
        people.stream().limit(5).forEach(System.out::println);
    }

    public static List<Person> readPeopleFromCsv(String csvFilePath, char separator) {
        List<Person> people = new ArrayList<>();

        try (InputStream in = CsvReaderApp.class.getClassLoader().getResourceAsStream(csvFilePath);
             InputStreamReader inputStreamReader = new InputStreamReader(in);
             CSVReader reader = in == null ? null : new CSVReaderBuilder(inputStreamReader)
                     .withCSVParser(new CSVParserBuilder().withSeparator(separator).build())
                     .build()) {

            if (reader == null) {
                throw new FileNotFoundException("File not found in resources: " + csvFilePath);
            }

            List<String[]> records = reader.readAll();
            boolean isHeader = true;

            for (String[] nextLine : records) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (nextLine.length >= 6) {
                    try {
                        int id = Integer.parseInt(nextLine[0].trim());
                        String name = nextLine[1].trim();
                        String gender = nextLine[2].trim();
                        Date birthDate = dateFormat.parse(nextLine[3].trim());
                        String departmentCode = nextLine[4].trim();
                        double salary = Double.parseDouble(nextLine[5].trim());

                        Department department = departmentCache.computeIfAbsent(
                                departmentCode,
                                code -> new Department(code)
                        );

                        people.add(new Person(id, name, gender, department, salary, birthDate));
                    } catch (ParseException | NumberFormatException e) {
                        System.err.println("Skipping invalid record: " + String.join(";", nextLine));
                    }
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return people;
    }
}

