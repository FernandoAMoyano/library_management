# Business Rules - Library Management System

## Resumen de Relaciones del Modelo

### models.Author ↔ models.Book
- Un **models.Author** puede tener cero o muchos **Books** registrados
- Todo **models.Book** debe tener exactamente un **models.Author**

### models.Book ↔ models.Copy
- Un **models.Book** puede tener cero o muchas **Copies** físicas
- Toda **models.Copy** debe pertenecer a exactamente un **models.Book**

### models.Book ↔ models.Genre (a través de Book_Genre)
- Un **models.Book** debe tener al menos un **models.Genre** asignado
- Un **models.Genre** puede tener cero o muchos **Books** asociados

### models.Member ↔ models.Loan
- Un **models.Member** puede tener cero o muchos **Loans**
- Todo **models.Loan** debe pertenecer a exactamente un **models.Member**

### models.Loan ↔ models.Copy (a través de Loan_Copy)
- Todo **models.Loan** debe incluir al menos una **models.Copy** prestada
- Una **models.Copy** puede tener cero o muchos **Loans** en su historial

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
| models.Author → models.Book | 1 mandatory to many optional | `\|\|--o{` |
| models.Book → models.Copy | 1 mandatory to many optional | `\|\|--o{` |
| models.Book → Book_Genre | 1 mandatory to many mandatory | `\|\|--\|{` |
| models.Genre → Book_Genre | 1 mandatory to many optional | `\|\|--o{` |
| models.Member → models.Loan | 1 mandatory to many optional | `\|\|--o{` |
| models.Loan → Loan_Copy | 1 mandatory to many mandatory | `\|\|--\|{` |
| models.Copy → Loan_Copy | 1 mandatory to many optional | `\|\|--o{` |
