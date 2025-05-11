package org.example;

class Department {
    private static int nextId = 1;
    private final int id;
    private final String code; // Используем код подразделения (I, J, F и т.д.)

    public Department(String code) {
        this.id = nextId++;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", code='" + code + "'}";
    }
}

