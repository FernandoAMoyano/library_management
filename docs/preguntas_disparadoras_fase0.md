# Resumen de Preguntas Disparadoras - Fase 0

## Proceso de Modelado del Sistema de Biblioteca

Este documento resume las preguntas clave que guiaron el proceso de diseño, las respuestas alcanzadas y los problemas que cada una intentaba resolver.

---

## 1. Diseño del Modelo Entidad-Relación

### 1.1 Sobre el atributo `autor` en Libro

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Si mañana quisieras buscar "todos los libros de García Márquez", ¿cómo lo harías con tu modelo actual? | Necesito una entidad separada `models.Author` | Evitar redundancia e inconsistencias (ej: "Gabriel García Márquez" vs "G. García Márquez") |
| ¿Te alcanza con solo el nombre del autor, o querrías guardar más datos (nacionalidad, año de nacimiento)? | Quiero guardar más datos | Justifica crear una entidad separada en lugar de un simple texto |

---

### 1.2 Sobre la relación Préstamo ↔ Libro

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Un libro solo puede aparecer en *un* préstamo... ¿para siempre? | No, puede aparecer en muchos préstamos a lo largo del tiempo | La cardinalidad inicial 1:1 era incorrecta |
| Cuando un socio pide prestados 3 libros, ¿eso genera 1 préstamo o 3 préstamos? | 1 préstamo con varios libros | Confirma que es relación N:M |
| ¿Cómo se resuelve una relación N:M en el modelo relacional? | Con una tabla intermedia | Las tablas no pueden relacionarse N:M directamente |

---

### 1.3 Separación de Libro y Ejemplar (models.Copy)

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Si la biblioteca tiene 3 ejemplares de "Cien años de soledad", ¿es un registro con `cantidad=3` o son 3 registros distintos? | Depende de si quiero identificar cada ejemplar físico | Introduce la distinción entre obra y copia física |
| Cuando un socio devuelve un libro, ¿a la biblioteca le importa *cuál* ejemplar físico le prestaron? | Sí, importa el ejemplar específico | Justifica tener registros individuales por ejemplar |
| El ISBN identifica la *obra*, pero si agregás un `id` a Libro... ¿qué estarías identificando? | El ISBN identifica la obra, el id identificaría el ejemplar | Clarifica que son dos conceptos distintos |
| Si `titulo`, `autor`, `año` van a la tabla intermedia, ¿qué pasaría cuando prestás el mismo libro 10 veces? | Tendría datos repetidos 10 veces | Los atributos deben vivir donde pertenecen naturalmente |
| ¿Cómo llamaría un bibliotecario a las copias físicas? | "Ejemplares" | Los nombres de entidades deben hablar el idioma del negocio |

**Resultado:** Dos entidades separadas:
- `models.Book` (la obra): isbn, titulo, año, autor
- `models.Copy` (el ejemplar físico): id, referencia al libro

---

### 1.4 Sobre atributos derivados/calculados

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Si cada registro de Ejemplar representa *una* copia física, ¿necesitás el atributo `cantidad`? | No, se puede contar los registros | Evitar almacenar datos que se pueden calcular |
| Si un ejemplar tiene un préstamo sin `fecha_devolucion`, ¿está disponible? | No | El atributo `disponible` se puede deducir, no hace falta guardarlo |

**Conclusión:** `cantidad` y `disponible` son datos derivados → no se almacenan.

---

### 1.5 Sobre las cardinalidades

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| ¿Un libro puede tener varios géneros? ¿Un género puede tener varios libros? | Sí a ambas | Confirma relación N:M entre models.Book y models.Genre |
| ¿Un ejemplar puede pertenecer a varios libros? | No, cada copia es de un solo libro | Confirma relación 1:N entre models.Book y models.Copy |
| ¿La cardinalidad se expresa en ambos lados? | Sí, cada número se coloca cerca de la entidad a la que se refiere | Clarifica la notación correcta del diagrama ER |

---

### 1.6 Sobre tablas intermedias

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| ¿Las tablas intermedias se representan en el diagrama ER? | No, solo en el modelo relacional (el rombo con N:M ya las implica) | Distinguir nivel conceptual (ER) de nivel físico (relacional) |
| Los atributos de la tabla intermedia, ¿son FK o PK? | Son FK que juntas forman la PK compuesta | Entender la doble función de estos atributos |

---

## 2. Traducción a Clases Java

### 2.1 Claves foráneas en POO

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| ¿Cómo se representa `author_id` en la clase models.Book? | Como referencia al objeto `models.Author`, no como String | En POO trabajamos con objetos, no con IDs |
| Si tuvieras solo `author_id`, para obtener el nombre del autor tendrías que... | Buscar en una colección, obtener el objeto | Con el objeto completo: `book.getAuthor().getName()` es directo |

---

### 2.2 Relaciones N:M en clases

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Las tablas intermedias como `loan_copy`, ¿se escriben en clases también? | No, si solo tienen FKs. Se usan listas de objetos | En POO las clases pueden tener listas, las tablas no |
| Si la tabla intermedia tuviera atributos propios, ¿cambiaría la respuesta? | Sí, ahí sí necesitaría una clase propia | Regla: solo FKs → Lista. Con atributos propios → Clase |

---

### 2.3 Convenciones Java

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Tu constructor tiene `void` y el nombre en minúscula... ¿notás el problema? | El constructor no lleva `void` y va con mayúscula | Sintaxis correcta de constructores en Java |
| `publication_year` vs `publicationYear`... ¿cuál es la convención Java? | camelCase | Seguir convenciones del lenguaje |
| `phone` como `Integer`: si un número empieza con 0, ¿qué pasaría? | Se perdería el cero inicial | Un teléfono no es un número matemático, es un identificador → `String` |

---

## 3. Almacenamiento en Memoria

### 3.1 Elección de estructura de datos

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Si quisieras buscar "todos los libros de García Márquez", ¿cómo lo harías con variables sueltas? | No podría fácilmente | Necesidad de colecciones para almacenar objetos |
| ¿Buscarías un miembro por su posición (0, 1, 2...) o por su ID ("M1", "M2")? | Por su ID | `Map` es mejor que `List` para búsqueda por clave |
| ¿Qué estructura te permite hacer `coleccion.get("M1")` directamente? | Map | Acceso directo vs recorrer toda una lista |

---

### 3.2 Qué guardar en el Map

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| En `copies.put(copy1.getId(), copy1.getBook())`, ¿qué estás guardando? | El libro, no la copia | Si guardo solo el libro, pierdo la información de la copia |
| Si guardás la copia, ¿podés llegar al libro? | Sí, con `miCopia.getBook()` | Guardando la copia tenés acceso a todo |

---

### 3.3 Mutabilidad de listas

| Pregunta | Respuesta | Problema que resuelve |
|----------|-----------|----------------------|
| Una vez que se registra el préstamo, ¿tiene sentido agregar otra copia después? | No, sería un préstamo nuevo | `List.of()` (inmutable) es la elección correcta |

---

## Conclusiones

### Principios descubiertos:

1. **Los nombres deben hablar el idioma del negocio** - "Ejemplar" en vez de "detalle_libro"
2. **Los atributos viven donde pertenecen naturalmente** - título en models.Book, no en la tabla intermedia
3. **No almacenar datos que se pueden calcular** - `cantidad` y `disponible` se derivan
4. **En POO trabajamos con objetos, no con IDs** - referencias completas en lugar de FKs
5. **Las tablas intermedias sin atributos propios se convierten en listas**
6. **Inmutabilidad intencional** - si algo no debe cambiar, hacerlo inmutable

### Patrón de preguntas efectivas:

- "Si quisieras hacer X, ¿cómo lo harías con tu modelo actual?"
- "¿Qué pasaría si...?"
- "¿Cómo lo llamaría alguien del negocio?"
- "¿Es necesario guardar esto o se puede calcular?"
