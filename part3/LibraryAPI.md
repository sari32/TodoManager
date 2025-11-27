
# Library API

---

## Endpoints

## Endpoints – Books

| Method | Endpoint                      | Description                              |
|--------|-------------------------------|------------------------------------------|
| GET    | /books                        | List all books                            |
| GET    | /books/{id}                   | Get book by ID                            |
| POST   | /books                        | Add a new book                            |
| PUT    | /books/{id}                   | Update book details                       |
| DELETE | /books/{id}                   | Delete a book                             |
| GET    | /books/search?query={query}   | Search books by title or author          |
| GET    | /books/available              | List all available books                  |

## Endpoints – Customers

| Method | Endpoint           | Description                |
|--------|------------------|----------------------------|
| GET    | /customers        | List all customers         |
| GET    | /customers/{id}   | Get customer by ID         |
| POST   | /customers        | Add a new customer         |
| PUT    | /customers/{id}   | Update customer details    |
| DELETE | /customers/{id}   | Delete a customer          |

## Endpoints – Loans

| Method | Endpoint          | Description                       |
|--------|-----------------|-----------------------------------|
| GET    | /loans           | List all loans                    |
| GET    | /loans/{id}      | Get loan by ID                    |
| POST   | /loans           | Add a new loan                    |
| PUT    | /loans/{id}      | Update loan details               |
| DELETE | /loans/{id}      | Delete a loan                     |
| GET    | /loans/customer/{id} | List all loans for a specific customer |



## BookDTO

```java
public class BookDTO {
    private int id;             // required
    private String title;       // required
    private String author;      // required
    private int yearPublished;  // optional
    private boolean available;  // true = available for loan, false = borrowed
}
```
## CustomerDTO

```java
public class CustomerDTO {
    private int id;             // required
    private String name;        // required      
    private String email;       // required
    private String phone;       // required
    private String password;    // required
    private String address;     // optional
}
```

## LoanDTO

```java
public class LoanDTO {
    private int id;             // required
    private int customerId;     // required
    private Book[] books;       // required
    private LocalDate Date;     // required
    private String status;      // required
}
```

