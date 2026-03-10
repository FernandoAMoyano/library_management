import infrastructure.InMemoryAuthorRepository;
import infrastructure.InMemoryBookRepository;
import infrastructure.InMemoryCopyRepository;
import infrastructure.InMemoryGenreRepository;
import infrastructure.InMemoryLoanRepository;
import infrastructure.InMemoryMemberRepository;
import models.*;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.CopyRepository;
import repository.GenreRepository;
import repository.LoanRepository;
import repository.MemberRepository;
import services.*;
import ui.ConsoleMenu;

import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String args[]) {

        // === REPOSITORIOS ===
        AuthorRepository authorRepository = new InMemoryAuthorRepository();
        GenreRepository genreRepository = new InMemoryGenreRepository();
        BookRepository bookRepository = new InMemoryBookRepository();
        MemberRepository memberRepository = new InMemoryMemberRepository();
        CopyRepository copyRepository = new InMemoryCopyRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        // === SERVICIOS ===
        AuthorService authorService = new AuthorService(authorRepository);
        GenreService genreService = new GenreService(genreRepository);
        BookService bookService = new BookService(bookRepository);
        MemberService memberService = new MemberService(memberRepository);
        CopyService copyService = new CopyService(copyRepository);
        LoanService loanService = new LoanService(loanRepository);

        // === DATOS DE PRUEBA ===

        // MEMBERS
        Member member1 = memberService.createMember(
                "Juan Pérez",
                "juan.perez@email.com",
                "1123456789"
        );

        Member member2 = memberService.createMember(
                "María Gómez",
                "maria.gomez@email.com",
                "1134567890"
        );

        Member member3 = memberService.createMember(
                "Lucas Fernández",
                "lucas.fernandez@email.com",
                "1145678901"
        );

        Member member4 = memberService.createMember(
                "Ana Rodríguez",
                "ana.rodriguez@email.com",
                "1156789012"
        );

        Member member5 = memberService.createMember(
                "Sofía Martínez",
                "sofia.martinez@email.com",
                "1167890123"
        );

        // AUTHORS
        Author author1 = authorService.createAuthor(
                "Gabriel García Márquez",
                "Colombian",
                LocalDate.of(1927, 3, 6),
                LocalDate.of(2014, 4, 17)
        );

        Author author2 = authorService.createAuthor(
                "Jorge Luis Borges",
                "Argentine",
                LocalDate.of(1899, 8, 24),
                LocalDate.of(1986, 6, 14)
        );

        Author author3 = authorService.createAuthor(
                "Jane Austen",
                "British",
                LocalDate.of(1775, 12, 16),
                LocalDate.of(1817, 7, 18)
        );

        Author author4 = authorService.createAuthor(
                "George Orwell",
                "British",
                LocalDate.of(1903, 6, 25),
                LocalDate.of(1950, 1, 21)
        );

        Author author5 = authorService.createAuthor(
                "Haruki Murakami",
                "Japanese",
                LocalDate.of(1949, 1, 12),
                null
        );

        // GENRES
        Genre genre1 = genreService.createGenre(
                "Ficción",
                "Relatos imaginarios que pueden incluir elementos irreales o fantásticos."
        );

        Genre genre2 = genreService.createGenre(
                "Ciencia Ficción",
                "Historias basadas en avances científicos, tecnología y futuros posibles."
        );

        Genre genre3 = genreService.createGenre(
                "Fantasía",
                "Narraciones ambientadas en mundos imaginarios con magia y seres sobrenaturales."
        );

        Genre genre4 = genreService.createGenre(
                "Novela Histórica",
                "Relatos de ficción situados en contextos históricos reales."
        );

        Genre genre5 = genreService.createGenre(
                "Misterio",
                "Historias centradas en enigmas, crímenes o sucesos por resolver."
        );

        // BOOKS
        Book book1 = bookService.createBook(
                "978-0307474728",
                "Cien años de soledad",
                1967,
                author1,
                List.of(genre1)
        );

        Book book2 = bookService.createBook(
                "978-8439720984",
                "El Aleph",
                1949,
                author2,
                List.of(genre1, genre5)
        );

        Book book3 = bookService.createBook(
                "978-0141439518",
                "Orgullo y prejuicio",
                1813,
                author3,
                List.of(genre4)
        );

        Book book4 = bookService.createBook(
                "978-0451524935",
                "1984",
                1949,
                author4,
                List.of(genre2)
        );

        Book book5 = bookService.createBook(
                "978-0099448761",
                "Kafka en la orilla",
                2002,
                author5,
                List.of(genre3, genre1)
        );

        // COPIES
        Copy copy1 = copyService.createCopy(book1);
        Copy copy2 = copyService.createCopy(book2);
        Copy copy3 = copyService.createCopy(book3);
        Copy copy4 = copyService.createCopy(book4);
        Copy copy5 = copyService.createCopy(book5);

        // LOANS (usando createLoan para demostrar el flujo completo)
        Loan loan1 = loanService.createLoan(
                LocalDate.of(2025, 1, 25),
                LocalDate.of(2025, 1, 28),
                member1,
                List.of(copy1, copy2)
        );

        Loan loan2 = loanService.createLoan(
                LocalDate.of(2025, 1, 23),
                LocalDate.of(2025, 1, 25),
                member3,
                List.of(copy3, copy4)
        );

        // Simular devolución del loan2
        loanService.returnLoan(loan2.getId());

        // === INICIO DEL PROGRAMA ===
        ConsoleMenu consoleMenu = new ConsoleMenu(memberService, bookService, authorService, genreService, copyService, loanService);
        consoleMenu.iniciar();
    }
}
