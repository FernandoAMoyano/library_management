package models;

import java.time.LocalDate;

public class Author {
    private String id;
    private String name;
    private String nationality;
    private LocalDate birthYear;
    private LocalDate deathYear;

    public Author(String id, String name, String nationality, LocalDate birthYear, LocalDate deathYear) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id debe ser un valor valido");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre debe ser un valor valido");
        }
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre debe ser un valor valido");
        }
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(LocalDate birthYear) {
        this.birthYear = birthYear;
    }

    public LocalDate getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(LocalDate deathYear) {
        this.deathYear = deathYear;
    }
}
