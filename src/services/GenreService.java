package services;

import models.Genre;
import repository.GenreRepository;

import java.util.List;

public class GenreService {
    private GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre createGenre(String name, String description) {
        String id = genreRepository.generateId();
        Genre genre = new Genre(id, name, description);
        genreRepository.save(genre);
        return genre;
    }

    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    public Genre findById(String id) {
        return genreRepository.findById(id);
    }

    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public void delete(String id) {
        genreRepository.delete(id);
    }
}
