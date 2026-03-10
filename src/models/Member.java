package models;

public class Member {
    private String id;
    private String name;
    private String email;
    private String phone;

    public Member(String id, String name, String email, String phone) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id debe ser un valor valido");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre debe ser un valor valido");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
