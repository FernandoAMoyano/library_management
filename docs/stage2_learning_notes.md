# Stage 2 - Learning Notes

## Evolución del Proyecto

### Estructura: Etapa 1 → Etapa 2

**Etapa 1 - Todo en un lugar:**
```
src/
├── Author.java
├── Book.java
├── Copy.java
├── Genre.java
├── Loan.java
├── Member.java
└── Main.java      ← Entidades + Datos + Almacenamiento mezclados
```

**Etapa 2 - Separación por responsabilidad:**
```
src/
├── models/        ← Solo entidades (datos)
│   ├── Author.java
│   ├── Book.java
│   ├── Copy.java
│   ├── Genre.java
│   ├── Loan.java
│   └── Member.java
│
├── services/      ← Lógica de negocio + almacenamiento
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── CopyService.java
│   ├── GenreService.java
│   ├── LoanService.java
│   └── MemberService.java
│
├── ui/            ← Interacción con usuario
│   └── ConsoleMenu.java
│
└── Main.java      ← Solo datos iniciales + inicio del programa
```

---

## Preguntas Clave y Aprendizajes

### 1. ¿Por qué retornar `List` en lugar de `ArrayList`?

**Pregunta:** En `findAll()` el tipo retornado es `List` pero se crea con `new ArrayList<>()`. ¿Por qué?

**Respuesta:** Programar contra interfaces, no contra implementaciones.

```java
// ❌ Atado a la implementación
public ArrayList<Member> findAll() { ... }

// ✅ Flexible - retorna la interfaz
public List<Member> findAll() { ... }
```

Si mañana cambio de `ArrayList` a `LinkedList`, el código que usa el método no se rompe. Solo sabe que recibe un `List`.

**Concepto aprendido:** `List`, `Set` y `Map` son interfaces. `ArrayList`, `HashSet`, `HashMap` son implementaciones concretas. Array (`String[]`) es una estructura nativa del lenguaje, no una interfaz.

---

### 2. ¿Dónde poner la lógica de `isCopyAvailable`?

**Pregunta inicial:** ¿Debería crear un método en `Copy` para verificar si está disponible?

**Problema:** Para saber si una copia está disponible, necesito revisar todos los préstamos. `Copy` no tiene acceso a los préstamos.

**Solución:** El método va en `LoanService` porque tiene acceso a `loans.values()`.

```java
// En LoanService (tiene acceso a todos los préstamos)
public boolean isCopyAvailable(Copy copy) {
    for (Loan loan : loans.values()) {
        for (Copy c : loan.getCopyList()) {
            if (c.getId().equals(copy.getId()) && loan.getReturnDate() == null) {
                return false;
            }
        }
    }
    return true;
}
```

**Concepto aprendido:** La lógica debe estar donde están los datos que necesita.

---

### 3. ¿Por qué `.equals()` en lugar de `==` para Strings?

**Pregunta:** ¿Por qué `b.getTitle() == title` no funciona correctamente?

**Respuesta:**
- `==` compara si son el **mismo objeto en memoria**
- `.equals()` compara si tienen el **mismo contenido**

```java
String a = "Hola";
String b = new String("Hola");

a == b        // false (diferentes objetos en memoria)
a.equals(b)   // true (mismo contenido)
```

**Concepto aprendido:** Siempre usar `.equals()` para comparar contenido de Strings y objetos.

---

### 4. ¿Quién crea los objetos cuando el usuario interactúa con el menú?

**Pregunta inicial:** ¿Los objetos se crean en `Main` con datos de `ConsoleMenu`?

**Razonamiento:**
1. `Main` arranca
2. `Main` crea los Services y datos iniciales
3. `Main` llama a `consoleMenu.iniciar()`
4. El programa queda "atrapado" en el loop del menú
5. Cuando el usuario elige "Agregar", `Main` ya no está ejecutándose línea por línea

**Respuesta:** `ConsoleMenu` crea los objetos porque es quien está ejecutándose cuando el usuario ingresa datos.

```java
// En ConsoleMenu.checkoutBook()
Loan loan = new Loan(loanId, loanDate, dueDate, returnDate, member, List.of(availableCopy));
loanService.save(loan);
```

**Concepto aprendido:** El flujo de ejecución determina quién tiene la responsabilidad.

---

### 5. ¿Qué retornar cuando no se encuentra algo: `null` o excepción?

**Pregunta:** En `findByTitle()`, si no encuentro el libro, ¿qué retorno?

**Respuesta para Etapa 2:** Retornar `null` y verificar en el llamador.

```java
public Book findByTitle(String title) {
    for (Book book : books.values()) {
        if (book.getTitle().equals(title)) {
            return book;
        }
    }
    return null;  // No encontrado
}

// En ConsoleMenu
Book book = bookService.findByTitle(title);
if (book == null) {
    System.out.println("Libro no encontrado");
    return;
}
```

**Nota:** En Etapa 4 (DDD) usaremos excepciones del dominio como `BookNotFoundException`.

---

### 6. ¿Cómo buscar un elemento cuando la key del Map no es el criterio de búsqueda?

**Pregunta:** El `Map<String, Book>` tiene `id` como key, pero quiero buscar por `title`.

**Respuesta:** Recorrer `values()` y comparar:

```java
public Book findByTitle(String title) {
    for (Book book : books.values()) {
        if (book.getTitle().equals(title)) {
            return book;
        }
    }
    return null;
}
```

**Concepto aprendido:** `map.get(key)` solo sirve si buscás por la key. Para otros criterios, recorrés `values()`.

---

### 7. ¿Cómo acumular resultados en un loop?

**Pregunta:** `obtainCopies(Book book)` debe retornar **todas** las copias de un libro, no solo una.

**Respuesta:** Crear lista antes del loop, agregar dentro, retornar después:

```java
public List<Copy> obtainCopies(Book book) {
    List<Copy> copiesOfTheBook = new ArrayList<>();  // 1. Crear antes
    for (Copy c : copies.values()) {
        if (c.getBook().equals(book)) {
            copiesOfTheBook.add(c);                   // 2. Agregar dentro
        }
    }
    return copiesOfTheBook;                          // 3. Retornar después
}
```

---

### 8. ¿Por qué `services` y no `controllers`?

**Pregunta:** ¿Es correcto llamar a la carpeta `services`?

**Respuesta:**

| Capa | Responsabilidad | En el proyecto |
|------|-----------------|----------------|
| Controller | Recibir entrada, coordinar flujo | `ConsoleMenu` |
| Service | Lógica de negocio, reglas del dominio | `BookService`, `LoanService`, etc. |

`services` es correcto porque contienen **lógica de negocio**, no manejo de entrada/salida.

---

### 9. ¿Por qué `ui` y no `view`?

**Pregunta:** ¿Debería llamarse `view` en lugar de `ui`?

**Respuesta:** `ConsoleMenu` hace:
1. Muestra menú y resultados (presentación)
2. Lee entrada del usuario (input)
3. Decide qué método llamar (coordinación)

`view` implicaría solo mostrar datos. `ui` abarca la **interfaz de usuario completa** (entrada + salida).

---

## Problemas de la Etapa 2 (Intencionales)

Estos problemas se resolverán en etapas futuras:

1. **Services conocen el almacenamiento:** `Map<String, Book>` está directamente en `BookService`. Si cambio a base de datos, debo modificar todos los Services.

2. **Entidades anémicas:** Las clases del modelo solo tienen getters/setters, sin comportamiento propio.

3. **Testing acoplado:** No puedo testear la lógica sin testear también el almacenamiento.

---

## Métodos Creados en Etapa 2

| Service | Método | Propósito |
|---------|--------|-----------|
| `BookService` | `findByTitle(String)` | Buscar libro por título |
| `MemberService` | `findByName(String)` | Buscar miembro por nombre |
| `CopyService` | `obtainCopies(Book)` | Obtener copias de un libro |
| `LoanService` | `isCopyAvailable(Copy)` | Verificar si copia está disponible |
| `LoanService` | `getFirstAvailableCopy(List<Copy>)` | Obtener primera copia disponible |
| `LoanService` | `obtainActiveLoan(Book, Member)` | Obtener préstamo activo |

---

## Funcionalidades del ConsoleMenu

| Método | Flujo |
|--------|-------|
| `checkAvailability()` | Título → Libro → Copias → Contar disponibles |
| `checkoutBook()` | Nombre → Miembro → Título → Libro → Copias → Primera disponible → Crear Loan |
| `checkinBook()` | Nombre → Miembro → Título → Libro → Loan activo → Marcar devuelto |
