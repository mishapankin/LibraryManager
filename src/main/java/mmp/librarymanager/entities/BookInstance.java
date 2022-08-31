package mmp.librarymanager.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

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
    @JoinColumn(name="book_isbn", nullable = false)
    Book book;

    @OneToMany
    Set<Operation> operations;
}