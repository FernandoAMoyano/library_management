package services;

import models.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreService {
    private Map<String, Genre> genres = new HashMap<>();

    public void save(Genre genre) {
        genres.put(genre.getId(), genre);
    }

    public Genre findById(String id) {
        return genres.get(id);
    }

    public List<Genre> findAll() {
        return new ArrayList<>(genres.values());
    }

    public void delete(String id) {
        genres.remove(id);
    }
}
