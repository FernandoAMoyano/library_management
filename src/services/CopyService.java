package services;

import models.Book;
import models.Copy;
import repository.CopyRepository;

import java.util.List;

public class CopyService {
    private CopyRepository copyRepository;

    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public Copy createCopy(Book book) {
        String id = copyRepository.generateId();
        Copy copy = new Copy(id, book);
        copyRepository.save(copy);
        return copy;
    }

    public void save(Copy copy) {
        copyRepository.save(copy);
    }

    public Copy findById(String id) {
        return copyRepository.findById(id);
    }

    public List<Copy> findByBook(Book book) {
        return copyRepository.findByBook(book);
    }

    public List<Copy> findAll() {
        return copyRepository.findAll();
    }

    public void delete(String id) {
        copyRepository.delete(id);
    }
}
