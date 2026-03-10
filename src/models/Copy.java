package models;

public class Copy {
    private String id;
    private Book book;

    public Copy(String id, Book book) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id debe ser un valor valido");
        }
        if (book == null) {
            throw new IllegalArgumentException("El libro debe ser un valor valido");
        }
        this.id = id;
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("El libro debe ser un valor valido");
        }
        this.book = book;
    }
}
