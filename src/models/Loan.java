package models;

import java.time.LocalDate;
import java.util.List;

public class Loan {
    private String id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Member member;
    private List<Copy> copyList;

    public Loan(String id, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, Member member, List<Copy> copyList) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id debe ser un valor valido");
        }
        if (loanDate == null) {
            throw new IllegalArgumentException("La fecha de prestamo debe ser un valor valido");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser un valor valido");
        }
        if (member == null) {
            throw new IllegalArgumentException("El miembro debe ser un valor valido");
        }
        if (copyList == null || copyList.isEmpty()) {
            throw new IllegalArgumentException("El prestamo debe incluir al menos una copia");
        }
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.member = member;
        this.copyList = copyList;
    }

    public String getId() {
        return id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser un valor valido");
        }
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Member getMember() {
        return member;
    }

    public List<Copy> getCopyList() {
        return copyList;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    public boolean isOverdue() {
        if (!isActive()) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }
}
