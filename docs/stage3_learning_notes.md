# Stage 3 - Learning Notes

## Objetivo de la Etapa

**Desacoplar la lógica de negocio de la implementación de almacenamiento** usando interfaces y el patrón Repository.

**Principio clave:** Los servicios deben depender de abstracciones (interfaces), no de implementaciones concretas.

---

## Evolución del Proyecto

### Estructura: Etapa 2 → Etapa 3

**Etapa 2 - Servicios acoplados al almacenamiento:**
```
src/
├── models/
│   └── ...
├── services/           ← Contienen Map<String, Entity> directamente
│   ├── BookService.java
│   └── ...
├── ui/
│   └── ConsoleMenu.java
└── Main.java
```

**Etapa 3 - Separación con Repository Pattern:**
```
src/
├── models/             ← Entidades con validaciones
│   ├── Author.java
│   ├── Book.java
│   ├── Copy.java
│   ├── Genre.java
│   ├── Loan.java
│   └── Member.java
│
├── repository/         ← Interfaces (contratos)
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── CopyRepository.java
│   ├── GenreRepository.java
│   ├── LoanRepository.java
│   └── MemberRepository.java
│
├── infrastructure/     ← Implementaciones concretas
│   ├── InMemoryAuthorRepository.java
│   ├── InMemoryBookRepository.java
│   ├── InMemoryCopyRepository.java
│   ├── InMemoryGenreRepository.java
│   ├── InMemoryLoanRepository.java
│   └── InMemoryMemberRepository.java
│
├── services/           ← Lógica de negocio (depende de interfaces)
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── CopyService.java
│   ├── GenreService.java
│   ├── LoanService.java
│   └── MemberService.java
│
├── ui/
│   └── ConsoleMenu.java
│
└── Main.java           ← Wiring: conecta implementaciones con servicios
```

---

## Conceptos Clave Aprendidos

### 1. Patrón Repository

El Repository es una abstracción que separa la lógica de negocio del acceso a datos.

```java
// Interfaz (contrato) - en repository/
public interface BookRepository {
    String generateId();
    void save(Book book);
    Book findById(String id);
    Book findByTitle(String title);
    List<Book> findAll();
    void delete(String id);
}

// Implementación - en infrastructure/
public class InMemoryBookRepository implements BookRepository {
    private Map<String, Book> books = new HashMap<>();
    
    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
    }
    // ... resto de implementación
}
```

**Beneficio:** Si mañana quiero usar una base de datos, solo creo `DatabaseBookRepository` sin tocar el servicio.

---

### 2. Inyección de Dependencias Manual

Los servicios reciben sus dependencias por constructor en lugar de crearlas internamente.

```java
// ❌ Etapa 2 - Servicio crea su propia dependencia
public class BookService {
    private Map<String, Book> books = new HashMap<>();  // Acoplado
}

// ✅ Etapa 3 - Dependencia inyectada
public class BookService {
    private BookRepository bookRepository;  // Interfaz
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;  // Inyectada
    }
}
```

**En Main.java (wiring manual):**
```java
// 1. Crear implementaciones
BookRepository bookRepository = new InMemoryBookRepository();

// 2. Inyectar en servicios
BookService bookService = new BookService(bookRepository);
```

---

### 3. Generación de ID en el Repositorio

**Problema identificado:** ¿Quién es responsable de generar el ID de una entidad?

**Opciones analizadas:**

| Opción | Problema |
|--------|----------|
| Pasarlo manualmente al constructor | El código que crea el objeto debe inventar IDs únicos |
| Generarlo dentro de la entidad | La entidad conoce detalles de infraestructura |
| Generarlo en el repositorio al guardar | El objeto existe temporalmente sin ID (estado inválido) |
| **Generarlo en el repositorio ANTES de crear** | ✅ El objeto nace completo y válido |

**Solución implementada:**

```java
// En la interfaz
public interface BookRepository {
    String generateId();  // ← Nuevo método
    // ...
}

// En la implementación
@Override
public String generateId() {
    return UUID.randomUUID().toString();
}

// En el servicio (orquestación)
public Book createBook(String isbn, String title, ...) {
    String id = bookRepository.generateId();  // Paso 1: generar ID
    Book book = new Book(id, isbn, title, ...);  // Paso 2: crear con ID
    bookRepository.save(book);  // Paso 3: guardar
    return book;
}
```

**Principio:** El objeto nunca existe en estado inválido.

---

### 4. Validaciones en el Constructor

**Problema:** ¿Qué pasa si alguien crea un `Book` con título `null`?

```java
Book libro = new Book(id, isbn, null, ...);  // ← ¡Inválido!
```

**Solución:** Validar en el constructor y lanzar excepción si es inválido.

```java
public Book(String id, String isbn, String title, ...) {
    if (id == null || id.trim().isEmpty()) {
        throw new IllegalArgumentException("El id debe ser un valor valido");
    }
    if (title == null || title.trim().isEmpty()) {
        throw new IllegalArgumentException("El titulo debe ser un valor valido");
    }
    this.id = id;
    this.title = title;
    // ...
}
```

**Aprendizaje sobre validación de Strings:**

| Valor | `== null` | `.isEmpty()` | `.trim().isEmpty()` |
|-------|-----------|--------------|---------------------|
| `null` | true | ❌ NullPointerException | ❌ NullPointerException |
| `""` | false | true | true |
| `"   "` | false | false | true |
| `"Hola"` | false | false | false |

Por eso validamos: `if (title == null || title.trim().isEmpty())`

---

### 5. Consistencia entre Constructor y Setters

Si un atributo es obligatorio al crear, también debe validarse al modificar.

```java
// Constructor valida
public Book(String id, String isbn, String title, ...) {
    if (title == null || title.trim().isEmpty()) {
        throw new IllegalArgumentException("...");
    }
    this.title = title;
}

// Setter también valida (consistencia)
public void setTitle(String title) {
    if (title == null || title.trim().isEmpty()) {
        throw new IllegalArgumentException("...");
    }
    this.title = title;
}
```

---

### 6. Eliminación de `setId()`

**Razonamiento:** Si el ID lo genera el repositorio y nunca debe cambiar después de la creación, no tiene sentido tener un setter.

```java
// ❌ Permite cambiar el ID (peligroso)
public void setId(String id) {
    this.id = id;
}

// ✅ Solo getter (inmutable después de creación)
public String getId() {
    return id;
}
```

---

### 7. Protección contra NullPointerException en búsquedas

**Problema descubierto:**

```java
public Book findByTitle(String title) {
    for (Book book : books.values()) {
        if (book.getTitle().equals(title)) {  // ← ¿Qué pasa si book.getTitle() es null?
            return book;
        }
    }
    return null;
}
```

**Análisis de `objeto.equals(valor)`:**

| Escenario | Resultado |
|-----------|-----------|
| `"Hola".equals(null)` | `false` (no explota) |
| `"Hola".equals("Hola")` | `true` |
| `null.equals("Hola")` | ❌ `NullPointerException` |

**Solución:** Invertir el orden y validar el parámetro.

```java
public Book findByTitle(String title) {
    if (title == null) {
        return null;  // Protección contra parámetro null
    }
    for (Book book : books.values()) {
        if (title.equals(book.getTitle())) {  // ← Invertido: title nunca es null aquí
            return book;
        }
    }
    return null;
}
```

---

### 8. Atributos de Dominio vs Atributos de Sistema

**Pregunta:** ¿El `id` es parte del concepto "libro" o es para gestión del sistema?

| Tipo | Ejemplos | Característica |
|------|----------|----------------|
| **Dominio** | título, autor, ISBN, año publicación | Inherentes al concepto |
| **Sistema** | id, fechaCreacion, ultimaModificacion | Para gestión técnica |

**Implicación:** El `id` es un atributo del sistema, por eso tiene sentido que el repositorio (infraestructura) lo genere.

---

### 9. Atributos Obligatorios vs Opcionales por Entidad

| Entidad | Obligatorios | Opcionales |
|---------|--------------|------------|
| **Book** | id, title | isbn, publicationYear, author, genreList |
| **Author** | id, name | nationality, birthYear, deathYear |
| **Genre** | id, name | description |
| **Member** | id, name | email, phone |
| **Copy** | id, book | — |
| **Loan** | id, loanDate, dueDate, member, copyList (mín. 1) | returnDate |

**Razonamiento para cada decisión:**
- **ISBN opcional:** Libros anteriores a 1970 no tienen ISBN
- **Author opcional:** Libros anónimos o autor desconocido
- **returnDate opcional:** `null` significa préstamo activo

---

### 10. Métodos de Dominio en Entidades

Aunque las entidades son mayormente "anémicas" en Etapa 3, `Loan` tiene métodos útiles:

```java
public boolean isActive() {
    return returnDate == null;
}

public boolean isOverdue() {
    if (!isActive()) {
        return false;
    }
    return LocalDate.now().isAfter(dueDate);
}
```

**Beneficio:** Encapsulan lógica que pertenece conceptualmente al préstamo.

---

## Diferencia Clave: `return` vs `throw` en Constructor

**Pregunta inicial:** ¿Puedo usar `return` para rechazar la creación de un objeto inválido?

```java
public Book(String id, String isbn, String title, ...) {
    if (title == null) {
        return;  // ← ¿Esto evita crear el objeto?
    }
    this.title = title;
}
```

**Respuesta:** No. El `return` solo corta la ejecución del constructor, pero `new Book(...)` ya reservó memoria. El objeto existe con `title = null`.

**Solución correcta:** Usar `throw` para que el objeto nunca se cree.

```java
if (title == null || title.trim().isEmpty()) {
    throw new IllegalArgumentException("El titulo debe ser un valor valido");
}
```

---

## Flujo Completo de Creación (Ejemplo: Book)

```
┌─────────────┐     ┌─────────────────┐     ┌────────────────────────┐
│    Main     │     │   BookService   │     │ InMemoryBookRepository │
└──────┬──────┘     └────────┬────────┘     └───────────┬────────────┘
       │                     │                          │
       │ createBook(isbn,    │                          │
       │   title, ...)       │                          │
       │────────────────────>│                          │
       │                     │                          │
       │                     │ generateId()             │
       │                     │─────────────────────────>│
       │                     │                          │
       │                     │<─────────────────────────│
       │                     │      UUID string         │
       │                     │                          │
       │                     │ new Book(id, isbn, ...)  │
       │                     │──────────┐               │
       │                     │          │ Validaciones  │
       │                     │<─────────┘               │
       │                     │                          │
       │                     │ save(book)               │
       │                     │─────────────────────────>│
       │                     │                          │
       │<────────────────────│                          │
       │      Book           │                          │
```

---

## Resumen de Cambios por Capa

### Models (Entidades)

| Cambio | Razón |
|--------|-------|
| Validaciones en constructor | Objetos siempre válidos |
| Validaciones en setters (atributos obligatorios) | Consistencia |
| Eliminación de `setId()` | ID inmutable después de creación |
| Métodos de dominio básicos (`isActive()`, `isOverdue()`) | Encapsular lógica del concepto |

### Repository (Interfaces)

| Método | Propósito |
|--------|-----------|
| `generateId()` | Generar identificador único |
| `save(Entity)` | Guardar o actualizar |
| `findById(String)` | Buscar por ID |
| `findByX(...)` | Buscar por otros criterios |
| `findAll()` | Obtener todos |
| `delete(String)` | Eliminar por ID |

### Infrastructure (Implementaciones)

| Característica | Implementación |
|----------------|----------------|
| Almacenamiento | `Map<String, Entity>` |
| Generación de ID | `UUID.randomUUID().toString()` |
| Protección null | Validar parámetros antes de buscar |
| Búsquedas | Iterar `values()` con condición |

### Services

| Cambio | Antes (Etapa 2) | Después (Etapa 3) |
|--------|-----------------|-------------------|
| Dependencia | `Map<String, Entity>` interno | `EntityRepository` inyectado |
| Creación | Constructor sin parámetros | Constructor con Repository |
| Método `createX()` | No existía | Orquesta: generateId → new → save |

### Main

| Responsabilidad | Descripción |
|-----------------|-------------|
| Crear repositorios | `new InMemoryXRepository()` |
| Crear servicios | `new XService(repository)` |
| Wiring | Conectar todo |
| Datos de prueba | Usar `createX()` de servicios |

---

## Principios SOLID Aplicados

| Principio | Aplicación en Etapa 3 |
|-----------|----------------------|
| **S**ingle Responsibility | Repositorio: almacenamiento. Servicio: lógica. Entidad: datos + validación. |
| **O**pen/Closed | Agregar `DatabaseRepository` sin modificar servicios existentes |
| **L**iskov Substitution | Cualquier implementación de `BookRepository` funciona igual |
| **I**nterface Segregation | Interfaces específicas por entidad |
| **D**ependency Inversion | Servicios dependen de interfaces (abstracciones), no de implementaciones |

---

## Checklist de Etapa 3 Completada

- [x] Interfaces de repositorio para todas las entidades
- [x] Implementaciones InMemory para todas las interfaces
- [x] Servicios reciben repositorio por constructor (inyección de dependencias)
- [x] Servicios dependen de interfaces, no de implementaciones
- [x] Método `generateId()` en repositorios
- [x] Método `createX()` en servicios (orquesta el flujo)
- [x] Validaciones en constructores de entidades
- [x] Validaciones en setters de atributos obligatorios
- [x] Eliminación de `setId()` en todas las entidades
- [x] Protección contra null en métodos de búsqueda
- [x] Main actualizado con wiring manual
- [x] ConsoleMenu actualizado para usar nuevos métodos
- [x] Código compila y ejecuta correctamente

---

## Problemas que Quedan para Etapa 4 (DDD Liviano)

1. **Entidades mayormente anémicas:** Solo tienen getters/setters, poca lógica propia
2. **Excepciones genéricas:** Usamos `IllegalArgumentException` en lugar de excepciones del dominio
3. **Reglas de negocio en servicios:** La lógica de "no prestar libro ya prestado" está en el servicio, no en la entidad
4. **Sin Value Objects:** ISBN, Email podrían ser objetos de valor con validación propia

---

## Métodos Nuevos/Modificados en Etapa 3

| Clase | Método | Descripción |
|-------|--------|-------------|
| `*Repository` | `generateId()` | Genera UUID único |
| `*Service` | `createX(...)` | Orquesta creación completa |
| `LoanService` | `returnLoan(String)` | Marca préstamo como devuelto |
| `LoanService` | `findActiveLoanByBookAndMember(Book, Member)` | Buscar préstamo activo |
| `LoanRepository` | `findActiveLoans()` | Retorna préstamos sin devolver |
| `Loan` | `isActive()` | Verifica si está activo |
| `Loan` | `isOverdue()` | Verifica si está vencido |

---

## Próximo Paso: Etapa 4

En la Etapa 4 (DDD Liviano) se trabajará en:

1. Convertir entidades anémicas en **entidades ricas** con comportamiento
2. Crear **excepciones del dominio** (`LibroNoDisponibleException`, etc.)
3. Mover reglas de negocio hacia las entidades
4. Implementar **Value Objects** donde tenga sentido
5. El dominio como núcleo independiente que no depende de nada
