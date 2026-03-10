package infrastructure;

import models.Loan;
import models.Member;
import repository.LoanRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryLoanRepository implements LoanRepository {
    private Map<String, Loan> loans = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Loan loan) {
        loans.put(loan.getId(), loan);
    }

    @Override
    public Loan findById(String id) {
        return loans.get(id);
    }

    @Override
    public List<Loan> findByMember(Member member) {
        if (member == null) {
            return new ArrayList<>();
        }
        List<Loan> memberLoans = new ArrayList<>();
        for (Loan loan : loans.values()) {
            if (member.equals(loan.getMember())) {
                memberLoans.add(loan);
            }
        }
        return memberLoans;
    }

    @Override
    public List<Loan> findActiveLoans() {
        List<Loan> activeLoans = new ArrayList<>();
        for (Loan loan : loans.values()) {
            if (loan.isActive()) {
                activeLoans.add(loan);
            }
        }
        return activeLoans;
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }

    @Override
    public void delete(String id) {
        loans.remove(id);
    }
}
