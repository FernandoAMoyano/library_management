package services;

import models.Book;
import models.Copy;
import models.Loan;
import models.Member;
import repository.LoanRepository;

import java.time.LocalDate;
import java.util.List;

public class LoanService {
    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(LocalDate loanDate, LocalDate dueDate, Member member, List<Copy> copyList) {
        String id = loanRepository.generateId();
        Loan loan = new Loan(id, loanDate, dueDate, null, member, copyList);
        loanRepository.save(loan);
        return loan;
    }

    public void save(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan findById(String id) {
        return loanRepository.findById(id);
    }

    public List<Loan> findByMember(Member member) {
        return loanRepository.findByMember(member);
    }

    public List<Loan> findActiveLoans() {
        return loanRepository.findActiveLoans();
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public void delete(String id) {
        loanRepository.delete(id);
    }

    public boolean isCopyAvailable(Copy copy) {
        List<Loan> activeLoans = loanRepository.findActiveLoans();
        for (Loan loan : activeLoans) {
            for (Copy c : loan.getCopyList()) {
                if (c.getId().equals(copy.getId())) {
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

    public Loan findActiveLoanByBookAndMember(Book book, Member member) {
        List<Loan> activeLoans = loanRepository.findActiveLoans();
        for (Loan loan : activeLoans) {
            if (loan.getMember().equals(member)) {
                for (Copy c : loan.getCopyList()) {
                    if (c.getBook().equals(book)) {
                        return loan;
                    }
                }
            }
        }
        return null;
    }

    public Loan returnLoan(String loanId) {
        Loan loan = loanRepository.findById(loanId);
        if (loan != null && loan.isActive()) {
            loan.setReturnDate(LocalDate.now());
            loanRepository.save(loan);
        }
        return loan;
    }
}
