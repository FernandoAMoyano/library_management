package services;

import models.Book;
import models.Copy;
import models.Loan;
import models.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanService {
    private Map<String, Loan> loans = new HashMap<>();

    public void save(Loan loan) {
        loans.put(loan.getId(), loan);
    }

    public Loan findById(String id) {
        return loans.get(id);
    }

    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }

    public void delete(String id) {
        loans.remove(id);
    }

    public boolean isCopyAvailable(Copy copy) {
        for (Loan loan : loans.values()) {
            for (Copy c : loan.getCopyList()) {
                if (c.getId().equals(copy.getId()) && loan.getReturnDate() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Copy getFirstAvailableCopy(List<Copy> copies) {
        for (Copy c : copies) {
            if (isCopyAvailable(c)) {
                return c;
            }
        }
        return null;
    }

    public Loan obtainActiveLoan(Book book, Member member) {
        for (Loan loan : loans.values()) {
            if (loan.getMember().equals(member) && loan.getReturnDate() == null) {
                for (Copy c : loan.getCopyList()) {
                    if (c.getBook().equals(book)) {
                        return loan;
                    }
                }
            }

        }
        return null;
    }
}
