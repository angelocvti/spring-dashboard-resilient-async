# Spring Dashboard — Resilient & Asynchronous Example

Este é um projeto simples em **Spring Boot 3 (Java 21)** que demonstra como construir uma API **resiliente**, **assíncrona** e com **boas práticas de código**.

---

## Objetivo

O projeto expõe um endpoint que agrega informações de três "serviços" distintos (usuário, pedidos e reviews) de forma **concorrente**, com **tratamento de falhas** e **timeouts configurados**.

Mesmo que um serviço falhe, a resposta ainda é retornada com os dados disponíveis (graceful degradation).

---

## Stack utilizada

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database (em memória)**
- **CompletableFuture + Virtual Threads (Project Loom)**

---

## Endpoint principal

GET /api/dashboard/{userId}

Exemplo de resposta

```json
{
  "user": {
    "id": 1,
    "name": "Alice"
  },
  "orders": [
    {
      "id": 1,
      "createdAt": "2025-10-29T13:40:19.281266"
    },
    {
      "id": 2,
      "createdAt": "2025-10-29T13:40:19.281266"
    },
    {
      "id": 3,
      "createdAt": "2025-10-29T13:40:19.281266"
    }
  ],
  "reviewCount": 2
}
```

Mesmo se um dos serviços (User, Order ou Review) falhar, o sistema responde com dados parciais e valores padrão (default).

---

## Conceitos aplicados

✅ Programação assíncrona com CompletableFuture\
✅ Timeouts configurados com .orTimeout()\
✅ Fallbacks via .exceptionally()\
✅ Virtual Threads (Java 21 - Loom)
