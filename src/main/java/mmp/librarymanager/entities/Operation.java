package mmp.librarymanager.entities;

import lombok.Getter;
import mmp.librarymanager.OperationType;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "operation")
@Getter
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private OperationType type;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Reader reader;

    @Column(name = "date", nullable = false)
    private Date date;

    public Operation() {}

    public Operation(OperationType type, Book book, Reader reader, Date date) {
        this.type = type;
        this.book = book;
        this.reader = reader;
        this.date = date;
    }
}