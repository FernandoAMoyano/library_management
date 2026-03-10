package ui;

import models.Book;
import models.Copy;
import models.Loan;
import models.Member;
import services.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private MemberService memberService;
    private BookService bookService;
    private AuthorService authorService;
    private GenreService genreService;
    private CopyService copyService;
    private LoanService loanService;
    private Scanner scanner;

    public ConsoleMenu(MemberService memberService, BookService bookService, AuthorService authorService, GenreService genreService, CopyService copyService, LoanService loanService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.copyService = copyService;
        this.loanService = loanService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    checkAvailability();
                    break;
                case 2:
                    checkoutBook();
                    break;
                case 3:
                    checkinBook();
                    break;
                case 0:
                    System.out.println("¡Hasta Luego!");
            }
        } while (opcion != 0);
    }

    public void mostrarMenu() {
        System.out.println("\n======== BIBLIOTECA ==========");
        System.out.println("1. Consultar disponibilidad de un libro");
        System.out.println("2. Pedir prestado");
        System.out.println("3. Devolver libro");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void checkAvailability() {
        System.out.print("Ingrese el título del libro: ");
        String title = scanner.nextLine();
        Book book = bookService.findByTitle(title);
        if (book == null) {
            System.out.println("Libro no encontrado");
        } else {
            int availableCount = 0;
            List<Copy> copies = copyService.findByBook(book);
            for (Copy c : copies) {
                boolean isAvailable = loanService.isCopyAvailable(c);
                if (isAvailable) availableCount++;
            }
            System.out.println("Tenemos " + availableCount + " copias de " + book.getTitle() + " disponibles");
        }
    }

    public void checkoutBook() {
        // Buscando miembro por nombre
        System.out.print("Coloque a continuación el nombre con el que se registró como socio: ");
        String memberName = scanner.nextLine();
        Member member = memberService.findByName(memberName);
        if (member == null) {
            System.out.println("Aún no estás registrado como miembro");
            return;
        }
        // Buscando libro por titulo
        System.out.print("Ingrese el título del libro: ");
        String bookTitle = scanner.nextLine();
        Book book = bookService.findByTitle(bookTitle);
        if (book == null) {
            System.out.println("No tenemos disponible este libro");
            return;
        }
        // Obteniendo copias del libro
        List<Copy> bookCopies = copyService.findByBook(book);
        // Obteniendo copia disponible
        Copy availableCopy = loanService.getFirstAvailableCopy(bookCopies);
        if (availableCopy == null) {
            System.out.println("No tenemos copias disponibles en este momento");
            return;
        }
        // Datos para el préstamo
        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = LocalDate.now().plusDays(14);
        
        // Creación del préstamo usando el servicio
        Loan loan = loanService.createLoan(loanDate, dueDate, member, List.of(availableCopy));

        // Mensaje final
        System.out.println("\nPréstamo registrado exitosamente");
        System.out.println("Libro: " + book.getTitle());
        System.out.println("Copia: " + availableCopy.getId());
        System.out.println("Fecha de devolución: " + dueDate);
    }

    public void checkinBook() {
        // Buscando miembro por nombre
        System.out.print("Ingrese su nombre: ");
        String memberName = scanner.nextLine();
        Member member = memberService.findByName(memberName);
        if (member == null) {
            System.out.println("Aún no estás registrado como miembro");
            return;
        }
        // Buscando libro por titulo
        System.out.print("Ingrese el título del libro a devolver: ");
        String bookTitle = scanner.nextLine();
        Book book = bookService.findByTitle(bookTitle);
        if (book == null) {
            System.out.println("No tenemos disponible este libro");
            return;
        }
        // Buscando Prestamo activo del miembro y su libro
        Loan loan = loanService.findActiveLoanByBookAndMember(book, member);
        if (loan == null) {
            System.out.println("No se encontró un préstamo activo de este libro para este miembro");
            return;
        }
        // Registrar devolución usando el servicio
        loanService.returnLoan(loan.getId());

        System.out.println("\nDevolución registrada exitosamente");
        System.out.println("Libro: " + book.getTitle());
        System.out.println("Miembro: " + member.getName());
        System.out.println("Fecha de devolución: " + loan.getReturnDate());
    }
}
