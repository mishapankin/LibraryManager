package mmp.librarymanager.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
public class Book {
    @Id
    @Column(name="isbn", columnDefinition = "char(13)", nullable = false)
    private String isbn;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Publisher publisher;

    @Column(name="title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "book")
    private Set<BookInstance> bookInstances;

    public Book() {}

    public Book(String isbn, Author author, Publisher publisher, String title) {
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
    }
}