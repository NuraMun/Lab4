package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс, представляющий человека.
 */
class Person {
    private final int id;
    private final String name;
    private final String gender;
    private final Department department;
    private final double salary;
    private final Date birthDate;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    /**
     * Создает новый объект Person.
     * @param id идентификатор
     * @param name имя
     * @param gender пол
     * @param department подразделение
     * @param salary зарплата
     * @param birthDate дата рождения
     */
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

    /**
     * @return ID человека
     */
    public int getId() {
        return id;
    }

    /**
     * @return имя человека
     */
    public String getName() {
        return name;
    }

    /**
     * @return пол человека
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return подразделение
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @return зарплата
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @return дата рождения
     */
    public Date getBirthDate() {
        return birthDate;
    }
}