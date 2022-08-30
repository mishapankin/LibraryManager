package mmp.librarymanager.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "book_instance")
@Getter
public class BookInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_instance_seq")
    @SequenceGenerator(name="book_instance_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    Book book;
}