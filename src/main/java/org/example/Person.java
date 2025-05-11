package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

class Person {
    private final int id;
    private final String name;
    private final String gender;
    private final Department department;
    private final double salary;
    private final Date birthDate;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public Person(int id, String name, String gender, Department department, double salary, Date birthDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.salary = salary;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", department=" + department +
                ", salary=" + salary +
                ", birthDate=" + dateFormat.format(birthDate) +
                '}';
    }
}
