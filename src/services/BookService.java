package services;

import models.Author;
import models.Book;
import models.Genre;
import repository.BookRepository;

import java.util.List;

public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(String isbn, String title, Integer publicationYear,
                           Author author, List<Genre> genreList) {
        String id = bookRepository.generateId();
        Book book = new Book(id, isbn, title, publicationYear, author, genreList);
        bookRepository.save(book);
        return book;
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book findById(String id) {
        return bookRepository.findById(id);
    }

    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void delete(String id) {
        bookRepository.delete(id);
    }
}
