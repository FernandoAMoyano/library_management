import models.*;
import services.*;
import ui.ConsoleMenu;

import java.time.LocalDate;
import java.util.List;


public class Main {
    public static void main(String args[]) {

        //MEMBERS
        Member member1 = new Member(
                "M1",
                "Juan Pérez",
                "juan.perez@email.com",
                "1123456789"
        );

        Member member2 = new Member(
                "M2",
                "María Gómez",
                "maria.gomez@email.com",
                "1134567890"
        );

        Member member3 = new Member(
                "M3",
                "Lucas Fernández",
                "lucas.fernandez@email.com",
                "1145678901"
        );

        Member member4 = new Member(
                "M4",
                "Ana Rodríguez",
                "ana.rodriguez@email.com",
                "1156789012"
        );

        Member member5 = new Member(
                "M5",
                "Sofía Martínez",
                "sofia.martinez@email.com",
                "1167890123"
        );


        //AUTHORS
        Author author1 = new Author(
                "A1",
                "Gabriel García Márquez",
                "Colombian",
                LocalDate.of(1927, 3, 6),
                LocalDate.of(2014, 4, 17)
        );

        Author author2 = new Author(
                "A2",
                "Jorge Luis Borges",
                "Argentine",
                LocalDate.of(1899, 8, 24),
                LocalDate.of(1986, 6, 14)
        );

        Author author3 = new Author(
                "A3",
                "Jane Austen",
                "British",
                LocalDate.of(1775, 12, 16),
                LocalDate.of(1817, 7, 18)
        );

        Author author4 = new Author(
                "A4",
                "George Orwell",
                "British",
                LocalDate.of(1903, 6, 25),
                LocalDate.of(1950, 1, 21)
        );

        Author author5 = new Author(
                "A5",
                "Haruki Murakami",
                "Japanese",
                LocalDate.of(1949, 1, 12),
                null // sigue vivo
        );


        //GENRES
        Genre genre1 = new Genre(
                "G1",
                "Ficción",
                "Relatos imaginarios que pueden incluir elementos irreales o fantásticos."
        );

        Genre genre2 = new Genre(
                "G2",
                "Ciencia Ficción",
                "Historias basadas en avances científicos, tecnología y futuros posibles."
        );

        Genre genre3 = new Genre(
                "G3",
                "Fantasía",
                "Narraciones ambientadas en mundos imaginarios con magia y seres sobrenaturales."
        );

        Genre genre4 = new Genre(
                "G4",
                "Novela Histórica",
                "Relatos de ficción situados en contextos históricos reales."
        );

        Genre genre5 = new Genre(
                "G5",
                "Misterio",
                "Historias centradas en enigmas, crímenes o sucesos por resolver."
        );

        //BOOKS
        /* Ficción */
        Book book1 = new Book(
                "B1",
                "978-0307474728",
                "Cien años de soledad",
                1967,
                author1, // Gabriel García Márquez
                List.of(genre1)
        );

        /* Ficción + Misterio */
        Book book2 = new Book(
                "B2",
                "978-8439720984",
                "El Aleph",
                1949,
                author2, // Jorge Luis Borges
                List.of(genre1, genre5)
        );

        /* Novela Histórica */
        Book book3 = new Book(
                "B3",
                "978-0141439518",
                "Orgullo y prejuicio",
                1813,
                author3, // Jane Austen
                List.of(genre4)
        );

        /* Ciencia Ficción */
        Book book4 = new Book(
                "B4",
                "978-0451524935",
                "1984",
                1949,
                author4, // George Orwell
                List.of(genre2)
        );

        /* Fantasía + Ficción */
        Book book5 = new Book(
                "B5",
                "978-0099448761",
                "Kafka en la orilla",
                2002,
                author5, // Haruki Murakami
                List.of(genre3, genre1)
        );

        //COPYS
        Copy copy1 = new Copy("C1", book1);
        Copy copy2 = new Copy("C2", book2);
        Copy copy3 = new Copy("C3", book3);
        Copy copy4 = new Copy("C4", book4);
        Copy copy5 = new Copy("C5", book5);

        //ALMACENAMIENTOS

        //Almacenamiento de Miembros
        MemberService memberService = new MemberService();
        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);
        memberService.save(member4);
        memberService.save(member5);


        //Almacenamiento de Autores
        AuthorService authorService = new AuthorService();
        authorService.save(author1);
        authorService.save(author2);
        authorService.save(author3);
        authorService.save(author4);
        authorService.save(author5);


        //Almacenamiento de Generos
        GenreService genreService = new GenreService();
        genreService.save(genre1);
        genreService.save(genre2);
        genreService.save(genre3);
        genreService.save(genre4);
        genreService.save(genre5);


        //Almacenamiento de libros
        BookService bookService = new BookService();
        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);
        bookService.save(book5);


        //Almacenamiento de copias
        CopyService copyService = new CopyService();
        copyService.save(copy1);
        copyService.save(copy2);
        copyService.save(copy3);
        copyService.save(copy4);
        copyService.save(copy5);


        //Creacion de Loans
        Loan loan1 = new Loan(
                "L1",
                LocalDate.of(2025, 01, 25),
                LocalDate.of(2025, 01, 28),
                LocalDate.of(2025, 01, 28),
                memberService.findById("M1"),
                List.of(copyService.findById("C1"), copyService.findById("C2"))

        );
        //Creacion de Loans
        Loan loan2 = new Loan(
                "L2",
                LocalDate.of(2025, 01, 23),
                LocalDate.of(2025, 01, 25),
                LocalDate.of(2025, 01, 26),
                memberService.findById("M3"),
                List.of(copyService.findById("C3"), copyService.findById("C4"))

        );

        //almacenamiento de Loans
        LoanService loanService = new LoanService();
        loanService.save(loan1);
        loanService.save(loan2);

        //Inicio del programa
        ConsoleMenu consoleMenu = new ConsoleMenu(memberService, bookService, authorService, genreService, copyService, loanService);
        consoleMenu.iniciar();
    }
}


