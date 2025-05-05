package com.main.cleaningservice;

public class CleaningType {
    private int id;
    private String name;

    public CleaningType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object otherObj) {
        if ((otherObj == null) || !(otherObj instanceof CleaningType))
            return false;

        CleaningType otherCleaningType = (CleaningType) otherObj;

        return (id == otherCleaningType.getId()) && (name.equals(otherCleaningType.getName()));
    }
}
