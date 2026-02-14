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
        this.id=id;
        this.loanDate=loanDate;
        this.dueDate=dueDate;
        this.returnDate=returnDate;
        this.member=member;
        this.copyList=copyList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
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

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Copy> getCopyList() {
        return copyList;
    }

    public void setCopyList(List<Copy> copyList) {
        this.copyList = copyList;
    }
}
