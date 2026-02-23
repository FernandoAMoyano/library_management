package services;

import models.Author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorService {
    private Map<String, Author> authors = new HashMap<>();

    public void save(Author author) {
        authors.put(author.getId(), author);
    }

    public Author findById(String id) {
        return authors.get(id);
    }

    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    public void delete(String id) {
        authors.remove(id);
    }
}
