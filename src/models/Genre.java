package models;

public class Genre {
    private String id;
    private String name;
    private String description;

    public Genre(String id, String name, String description) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id debe ser un valor valido");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre debe ser un valor valido");
        }
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
