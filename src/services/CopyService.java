package services;

import models.Book;
import models.Copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyService {
    private Map<String, Copy> copies = new HashMap<>();

    public void save(Copy copy) {
        copies.put(copy.getId(), copy);
    }

    public Copy findById(String id) {
        return copies.get(id);
    }

    public List<Copy> findAll() {
        return new ArrayList<>(copies.values());
    }

    public void delete(String id) {
        copies.remove(id);
    }

    public List<Copy> obtainCopies(Book book) {
        //Listado de copias del libro seleccionado
        List<Copy> copiesOfTheBook = new ArrayList<>();
        for (Copy c : copies.values()) {
            if (c.getBook().equals(book)) {
                //Agregamos una copia al listado
                copiesOfTheBook.add(c);
            }
        }
        return copiesOfTheBook;
    }
}
