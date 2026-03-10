package repository;

import models.Book;

import java.util.List;

public interface BookRepository {
    String generateId();

    void save(Book book);

    Book findById(String id);

    Book findByTitle(String title);

    List<Book> findAll();

    void delete(String id);
}
