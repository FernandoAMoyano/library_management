import java.time.LocalDate;

public class Author {
    private String id;
    private String name;
    private String nationality;
    private LocalDate birthYear;
    private LocalDate deathYear;

    public Author(String id, String name, String nationality, LocalDate birthYear, LocalDate deathYear){
        this.id=id;
        this.name=name;
        this.nationality=nationality;
        this.birthYear=birthYear;
        this.deathYear=deathYear;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getNationality(){
        return nationality;
    }

    public void setNationality(String nationality){
        this.nationality=nationality;
    }

    public LocalDate getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(LocalDate birthYear) {
        this.birthYear = birthYear;
    }

    public LocalDate getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(LocalDate deathYear) {
        this.deathYear = deathYear;
    }
}
