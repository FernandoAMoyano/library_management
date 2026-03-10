# Sistema de Gestión de Biblioteca

Sistema de gestión bibliotecaria desarrollado en Java puro como proyecto de portfolio para demostrar dominio de POO, modelado de dominio y patrones de diseño.

## 🎯 Objetivo

Demostrar dominio real de Programación Orientada a Objetos y diseño de software a través de un enfoque **progresivo**. En lugar de aplicar directamente patrones y arquitecturas, el proyecto evoluciona en etapas para experimentar los problemas que cada patrón resuelve.

## 📈 Evolución del Proyecto

| Etapa | Enfoque | Estado |
|-------|---------|--------|
| 1 | Clases por entidad | ✅ Completada |
| 2 | Separación de responsabilidades | ✅ Completada |
| 3 | Repositorios e interfaces | ✅ Completada |
| 4 | DDD Liviano | 🔜 Próxima |

## 🏗️ Estructura Actual (Etapa 3)

```
src/
├── models/                 ← Entidades con validaciones
│   ├── Author.java
│   ├── Book.java
│   ├── Copy.java
│   ├── Genre.java
│   ├── Loan.java
│   └── Member.java
│
├── repository/             ← Interfaces (contratos)
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   ├── CopyRepository.java
│   ├── GenreRepository.java
│   ├── LoanRepository.java
│   └── MemberRepository.java
│
├── infrastructure/         ← Implementaciones concretas
│   ├── InMemoryAuthorRepository.java
│   ├── InMemoryBookRepository.java
│   ├── InMemoryCopyRepository.java
│   ├── InMemoryGenreRepository.java
│   ├── InMemoryLoanRepository.java
│   └── InMemoryMemberRepository.java
│
├── services/               ← Lógica de negocio
│   ├── AuthorService.java
│   ├── BookService.java
│   ├── CopyService.java
│   ├── GenreService.java
│   ├── LoanService.java
│   └── MemberService.java
│
├── ui/                     ← Interfaz de usuario
│   └── ConsoleMenu.java
│
└── Main.java               ← Punto de entrada y wiring
```

## 🔑 Conceptos Aplicados

### Etapa 3 - Repository Pattern & Dependency Injection

- **Interfaces de repositorio**: Contratos que definen operaciones de persistencia
- **Implementaciones InMemory**: Almacenamiento en memoria usando `Map<String, Entity>`
- **Inyección de dependencias manual**: Servicios reciben repositorios por constructor
- **Generación de ID en repositorio**: `generateId()` usando UUID
- **Validaciones en constructor**: Objetos nunca existen en estado inválido
- **Protección de invariantes**: Atributos obligatorios validados

### Principios SOLID Aplicados

| Principio | Aplicación |
|-----------|------------|
| **S**ingle Responsibility | Cada clase tiene una única responsabilidad |
| **O**pen/Closed | Nuevas implementaciones de repositorio sin modificar servicios |
| **L**iskov Substitution | Cualquier implementación de Repository funciona igual |
| **I**nterface Segregation | Interfaces específicas por entidad |
| **D**ependency Inversion | Servicios dependen de interfaces, no implementaciones |

## 🚀 Funcionalidades

- **Consultar disponibilidad**: Verificar copias disponibles de un libro
- **Pedir prestado**: Registrar préstamo de una copia a un miembro
- **Devolver libro**: Registrar devolución de un préstamo

## 💻 Requisitos

- Java 11 o superior
- IDE compatible (IntelliJ IDEA recomendado)

## ▶️ Ejecución

1. Clonar el repositorio
2. Abrir en IDE
3. Ejecutar `Main.java`

## 📚 Documentación

- `/docs/stage2_learning_notes.md` - Notas de aprendizaje Etapa 2
- `/docs/stage3_learning_notes.md` - Notas de aprendizaje Etapa 3
- `/docs/business_rules.md` - Reglas de negocio
- `/diagrams/` - Diagramas UML y modelo relacional

## 🏷️ Tags de Versión

- `v1.0-stage1` - Etapa 1: Clases por entidad
- `v2.0-stage2` - Etapa 2: Separación de responsabilidades
- `v3.0-stage3` - Etapa 3: Repositorios e interfaces

## 📝 Notas

Este proyecto es parte de un roadmap de aprendizaje Java Backend. El enfoque es **progresivo**: cada etapa introduce mejoras sobre la anterior, permitiendo entender *por qué* se aplican ciertos patrones.

**Restricciones intencionales:**
- ❌ Sin frameworks (Spring, etc.)
- ❌ Sin base de datos
- ✅ Colecciones en memoria
- ✅ Java puro

> "Este candidato entiende POO, no solo sintaxis."
