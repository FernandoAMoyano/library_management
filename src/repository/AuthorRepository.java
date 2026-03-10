package repository;

import models.Author;

import java.util.List;

public interface AuthorRepository {
    String generateId();
    void save(Author author);
    Author findById(String id);
    Author findByName(String name);
    List<Author>findAll();
    void delete(String id);
}
