package models;

import java.util.List;

public class Book {
    private String id;
    private String isbn;
    private String title;
    private Integer publicationYear;
    private Author author;
    private List<Genre> genreList;

    public Book(String id, String isbn, String title, Integer publicationYear, Author author, List<Genre> genreList) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id debe ser un valor valido");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo debe ser un valor valido");
        }
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
        this.genreList = genreList;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo debe ser un valor valido");
        }
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
}
