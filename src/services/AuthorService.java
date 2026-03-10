package services;

import models.Author;
import repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

public class AuthorService {
    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String name, String nationality, LocalDate birthYear, LocalDate deathYear) {
        String id = authorRepository.generateId();
        Author author = new Author(id, name, nationality, birthYear, deathYear);
        authorRepository.save(author);
        return author;
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public Author findById(String id) {
        return authorRepository.findById(id);
    }

    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void delete(String id) {
        authorRepository.delete(id);
    }
}
