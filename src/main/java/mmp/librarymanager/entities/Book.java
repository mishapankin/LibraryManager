package mmp.librarymanager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "book")
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="isbn", nullable = false)
    private String isbn;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Publisher publisher;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="total", nullable=false)
    private int total;

    @Column(name="available", nullable=false)
    private int available;

    public Book() {}

    public Book(String isbn, Author author, Publisher publisher, String title, int total, int available) {
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
        this.total = total;
        this.available = available;
    }
}