# Cardinalities Reference - Crow's Foot Notation

## Símbolos Básicos (Componentes)

### Cero (Zero / Optional)
```
────────────o
```
El círculo representa cero/opcional.

---

### Uno (One / Mandatory)
```
────────────┼
```
La línea vertical indica uno/obligatorio.

---

### Muchos (Many)
```
────────────<
```
La pata de gallo (crow's foot) indica muchos.

---

## Cardinalidades Combinadas

### Cero o Muchos (Zero or Many)
```
───────────o<
```
Combinación del círculo (cero) + pata de gallo (muchos).
Significa: puede haber ninguno, uno o varios.

---

### Uno o Muchos (One or Many)
```
───────────┼<
```
Combinación de la línea vertical (uno) + pata de gallo (muchos).
Significa: debe haber al menos uno, puede haber varios.

---

### Uno y Solo Uno (One and Only One)
```
───────────┼┼
```
Dos líneas verticales indican exactamente uno, obligatorio.
Significa: debe haber exactamente uno.

---

### Cero o Uno (Zero or One)
```
───────────o┼
```
Combinación del círculo (cero) + línea vertical (uno).
Significa: puede haber ninguno o como máximo uno.

---

## Tabla Resumen

| Símbolo | Gráfico | Inglés | Español |
|:-------:|:-------:|--------|---------|
| `o` | `──o` | Zero / Optional | Cero / Opcional |
| `\|` | `──┼` | One / Mandatory | Uno / Obligatorio |
| `<` | `──<` | Many | Muchos |
| `o<` | `──o<` | Zero or Many | Cero o Muchos |
| `\|<` | `──┼<` | One or Many | Uno o Muchos |
| `\|\|` | `──┼┼` | One and Only One | Uno y Solo Uno |
| `o\|` | `──o┼` | Zero or One | Cero o Uno |

---

## Relaciones Completas (Ejemplos)

### 1 Mandatory to Many Optional
```
┼┼──────────────o<
```
- Lado izquierdo: exactamente uno, obligatorio
- Lado derecho: cero o muchos

**Ejemplo:** Author → Book (un autor puede tener 0 o muchos libros)

---

### 1 Mandatory to Many Mandatory
```
┼┼──────────────┼<
```
- Lado izquierdo: exactamente uno, obligatorio
- Lado derecho: uno o muchos (al menos uno)

**Ejemplo:** Book → Book_Genre (un libro debe tener al menos un género)

---

### 1 Mandatory to 1 Mandatory
```
┼┼──────────────┼┼
```
- Ambos lados: exactamente uno, obligatorio

**Ejemplo:** Person → Passport (una persona tiene exactamente un pasaporte)

---

## Relaciones del Proyecto Biblioteca

| Relación | Gráfico | Significado |
|----------|:-------:|-------------|
| Author → Book | `┼┼────o<` | Un autor puede tener 0 o muchos libros |
| Book → Copy | `┼┼────o<` | Un libro puede tener 0 o muchas copias |
| Book → Book_Genre | `┼┼────┼<` | Un libro debe tener al menos 1 género |
| Genre → Book_Genre | `┼┼────o<` | Un género puede tener 0 o muchos libros |
| Member → Loan | `┼┼────o<` | Un miembro puede tener 0 o muchos préstamos |
| Loan → Loan_Copy | `┼┼────┼<` | Un préstamo debe tener al menos 1 copia |
| Copy → Loan_Copy | `┼┼────o<` | Una copia puede tener 0 o muchos préstamos |
