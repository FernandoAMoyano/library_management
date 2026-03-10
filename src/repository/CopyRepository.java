package repository;

import models.Book;
import models.Copy;

import java.util.List;

public interface CopyRepository {
    String generateId();

    void save(Copy copy);

    Copy findById(String id);

    List<Copy> findByBook(Book book);

    List<Copy> findAll();

    void delete(String id);
}
