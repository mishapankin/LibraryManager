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

    @ManyToOne
    @JoinColumn(name = "book_instance_id", nullable = false)
    private BookInstance bookInstance;

    @ManyToOne
    private Reader reader;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name="due_date", nullable = false)
    private Date dueDate;

    @Column(name="return_date", nullable = true)
    private Date returnDate;

    public Operation() {}

    public Operation(Long id, BookInstance bookInstance, Reader reader, Date date, Date dueDate) {
        this.id = id;
        this.bookInstance = bookInstance;
        this.reader = reader;
        this.date = date;
        this.dueDate = dueDate;
    }
}