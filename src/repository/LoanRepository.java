package repository;

import models.Loan;
import models.Member;

import java.util.List;

public interface LoanRepository {
    String generateId();

    void save(Loan loan);

    Loan findById(String id);

    List<Loan> findByMember(Member member);

    List<Loan> findActiveLoans();

    List<Loan> findAll();

    void delete(String id);
}
