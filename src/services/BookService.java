package services;

import models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    private Map<String, Book> books = new HashMap<>();

    public void save(Book book) {
        books.put(book.getId(), book);
    }

    public Book findById(String id) {
        return books.get(id);
    }

    public Book findByTitle(String title) {
        for (Book book : books.values()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public void delete(String id) {
        books.remove(id);
    }
}
