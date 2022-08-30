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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_seq")
    @SequenceGenerator(name="operation_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private OperationType type;

    @ManyToOne
    private BookInstance bookInstance;

    @ManyToOne
    private Reader reader;

    @Column(name = "date", nullable = false)
    private Date date;

    public Operation() {}

    public Operation(OperationType type, BookInstance bookInstance, Reader reader, Date date) {
        this.type = type;
        this.bookInstance = bookInstance;
        this.reader = reader;
        this.date = date;
    }
}