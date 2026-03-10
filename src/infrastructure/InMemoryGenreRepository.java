package infrastructure;

import models.Genre;
import repository.GenreRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryGenreRepository implements GenreRepository {
    private Map<String, Genre> genres = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Genre genre) {
        genres.put(genre.getId(), genre);
    }

    @Override
    public Genre findById(String id) {
        return genres.get(id);
    }

    @Override
    public Genre findByName(String name) {
        if (name == null) {
            return null;
        }
        for (Genre genre : genres.values()) {
            if (name.equals(genre.getName())) {
                return genre;
            }
        }
        return null;
    }

    @Override
    public List<Genre> findAll() {
        return new ArrayList<>(genres.values());
    }

    @Override
    public void delete(String id) {
        genres.remove(id);
    }
}
