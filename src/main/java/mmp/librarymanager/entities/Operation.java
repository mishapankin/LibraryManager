package mmp.librarymanager.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reader reader;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name="due_date", nullable = false)
    private Date dueDate;

    @Column(name="return_date")
    @Setter
    private Date returnDate;

    public Operation() {}

    public Operation(BookInstance bookInstance, Reader reader, Date date, Date dueDate) {
        this.bookInstance = bookInstance;
        this.reader = reader;
        this.date = date;
        this.dueDate = dueDate;
    }
}