package infrastructure;

import models.Author;
import repository.AuthorRepository;

import java.util.*;


public class InMemoryAuthorRepository implements AuthorRepository {
    private Map<String, Author> authors = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Author author) {
        authors.put(author.getId(), author);
    }

    @Override
    public Author findById(String id) {
        return authors.get(id);
    }

    @Override
    public Author findByName(String name) {
        if (name == null) {
            return null;
        }
        for (Author author : authors.values()) {
            if (name.equals(author.getName())) {
                return author;
            }
        }
        return null;
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public void delete(String id) {
        authors.remove(id);
    }
}
