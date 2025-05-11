package org.example;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderAppTest {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void testCreatePersonFromCsvRecord() throws ParseException {
        String[] validRecord = {
                "28281",
                "Aahan",
                "Male",
                "15.05.1970",
                "I",
                "4800"
        };

        Person person = CsvReaderApp.createPersonFromCsvRecord(validRecord);

        assertNotNull(person);
        assertEquals(28281, person.getId());
        assertEquals("Aahan", person.getName());
        assertEquals("Male", person.getGender());
        assertEquals("I", person.getDepartment().getCode());
        assertEquals(4800, person.getSalary(), 0.001);
        assertEquals(dateFormat.parse("15.05.1970"), person.getBirthDate());
    }

    @Test
    void testDepartmentCache() throws ParseException {
        String[] record1 = {"1", "John", "Male", "01.01.1990", "IT", "5000"};
        String[] record2 = {"2", "Anna", "Female", "02.02.1991", "IT", "6000"};

        Person person1 = CsvReaderApp.createPersonFromCsvRecord(record1);
        Person person2 = CsvReaderApp.createPersonFromCsvRecord(record2);

        assertNotNull(person1);
        assertNotNull(person2);
        assertSame(person1.getDepartment(), person2.getDepartment());
    }

    @Test
    void testInvalidNumberFormat() {
        String[] invalidRecord = {
                "invalid_id",
                "Aahan",
                "Male",
                "15.05.1970",
                "I",
                "4800"
        };

        assertThrows(NumberFormatException.class,
                () -> CsvReaderApp.createPersonFromCsvRecord(invalidRecord));
    }

    @Test
    void testInvalidDateFormat() {
        String[] invalidRecord = {
                "28281",
                "Aahan",
                "Male",
                "invalid_date",
                "I",
                "4800"
        };

        assertThrows(ParseException.class,
                () -> CsvReaderApp.createPersonFromCsvRecord(invalidRecord));
    }
}