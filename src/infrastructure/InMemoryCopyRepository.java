package infrastructure;

import models.Book;
import models.Copy;
import repository.CopyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryCopyRepository implements CopyRepository {
    private Map<String, Copy> copies = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Copy copy) {
        copies.put(copy.getId(), copy);
    }

    @Override
    public Copy findById(String id) {
        return copies.get(id);
    }

    @Override
    public List<Copy> findByBook(Book book) {
        if (book == null) {
            return new ArrayList<>();
        }
        List<Copy> copiesOfBook = new ArrayList<>();
        for (Copy copy : copies.values()) {
            if (book.equals(copy.getBook())) {
                copiesOfBook.add(copy);
            }
        }
        return copiesOfBook;
    }

    @Override
    public List<Copy> findAll() {
        return new ArrayList<>(copies.values());
    }

    @Override
    public void delete(String id) {
        copies.remove(id);
    }
}
