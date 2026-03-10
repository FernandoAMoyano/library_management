package repository;

import models.Genre;

import java.util.List;

public interface GenreRepository {
    String generateId();

    void save(Genre genre);

    Genre findById(String id);

    Genre findByName(String name);

    List<Genre> findAll();

    void delete(String id);
}
