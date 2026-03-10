package infrastructure;

import models.Book;
import repository.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryBookRepository implements BookRepository {
    private Map<String, Book> books = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Book findById(String id) {
        return books.get(id);
    }

    @Override
    public Book findByTitle(String title) {
        if (title == null) {
            return null;
        }
        for (Book book : books.values()) {
            if (title.equals(book.getTitle())) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void delete(String id) {
        books.remove(id);
    }
}
