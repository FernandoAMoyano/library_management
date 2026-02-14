# Business Rules - Library Management System

## Resumen de Relaciones del Modelo

### Author ↔ Book
- Un **Author** puede tener cero o muchos **Books** registrados
- Todo **Book** debe tener exactamente un **Author**

### Book ↔ Copy
- Un **Book** puede tener cero o muchas **Copies** físicas
- Toda **Copy** debe pertenecer a exactamente un **Book**

### Book ↔ Genre (a través de Book_Genre)
- Un **Book** debe tener al menos un **Genre** asignado
- Un **Genre** puede tener cero o muchos **Books** asociados

### Member ↔ Loan
- Un **Member** puede tener cero o muchos **Loans**
- Todo **Loan** debe pertenecer a exactamente un **Member**

### Loan ↔ Copy (a través de Loan_Copy)
- Todo **Loan** debe incluir al menos una **Copy** prestada
- Una **Copy** puede tener cero o muchos **Loans** en su historial

---

## Reglas de Negocio Implícitas

1. No existen libros sin autor conocido
2. No existen libros sin clasificación de género
3. No existen préstamos vacíos (sin copias)
4. Pueden existir miembros que nunca han pedido prestado
5. Pueden existir copias que nunca han sido prestadas
6. Pueden existir géneros sin libros asignados (catálogo preparado)
7. Pueden existir libros sin copias físicas (en pedido o agotados)

---

## Resumen de Cardinalidades

| Relación | Cardinalidad | Notación |
|----------|--------------|----------|
| Author → Book | 1 mandatory to many optional | `\|\|--o{` |
| Book → Copy | 1 mandatory to many optional | `\|\|--o{` |
| Book → Book_Genre | 1 mandatory to many mandatory | `\|\|--\|{` |
| Genre → Book_Genre | 1 mandatory to many optional | `\|\|--o{` |
| Member → Loan | 1 mandatory to many optional | `\|\|--o{` |
| Loan → Loan_Copy | 1 mandatory to many mandatory | `\|\|--\|{` |
| Copy → Loan_Copy | 1 mandatory to many optional | `\|\|--o{` |
