package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Главный класс приложения для чтения данных о людях из CSV-файла.
 */
public class CsvReaderApp {
    private static final Map<String, Department> departmentCache = new HashMap<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        String csvFilePath = "foreign_names.csv";
        char separator = ';';

        List<Person> people = readPeopleFromCsv(csvFilePath, separator);

        System.out.println("Total people read: " + people.size());
        System.out.println("\nSample records:");
        people.stream().limit(5).forEach(System.out::println);
    }

    /**
     * Читает данные о людях из CSV-файла.
     * @param csvFilePath путь к CSV-файлу
     * @param separator разделитель полей
     * @return список объектов Person
     */
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
                        Person person = createPersonFromCsvRecord(nextLine);
                        if (person != null) {
                            people.add(person);
                        }
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

    /**
     * Создает объект Person из записи CSV.
     * @param record массив строк с данными
     * @return объект Person или null, если запись невалидна
     * @throws ParseException если ошибка парсинга даты
     * @throws NumberFormatException если ошибка парсинга чисел
     */
    public static Person createPersonFromCsvRecord(String[] record) throws ParseException, NumberFormatException {
        int id = Integer.parseInt(record[0].trim());
        String name = record[1].trim();
        String gender = record[2].trim();
        Date birthDate = dateFormat.parse(record[3].trim());
        String departmentCode = record[4].trim();
        double salary = Double.parseDouble(record[5].trim());

        Department department = departmentCache.computeIfAbsent(
                departmentCode,
                code -> new Department(code)
        );

        return new Person(id, name, gender, department, salary, birthDate);
    }
}