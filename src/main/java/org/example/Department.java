package org.example;

/**
 * Класс, представляющий подразделение.
 */
class Department {
    private static int nextId = 1;
    private final int id;
    private final String code;

    /**
     * Создает новое подразделение.
     * @param code код подразделения
     */
    public Department(String code) {
        this.id = nextId++;
        this.code = code;
    }

    /**
     * @return ID подразделения
     */
    public int getId() {
        return id;
    }

    /**
     * @return код подразделения
     */
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", code='" + code + "'}";
    }
}