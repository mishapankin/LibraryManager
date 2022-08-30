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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name="book_seq")
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

    public Book() {}

    public Book(String isbn, Author author, Publisher publisher, String title) {
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
    }
}