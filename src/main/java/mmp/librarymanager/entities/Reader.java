package mmp.librarymanager.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "reader")
@Getter
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reader_seq")
    @SequenceGenerator(name="reader_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="address", nullable = false)
    private String address;

    public Reader() {}
    public Reader(String name, String address) {
        this.name = name;
        this.address = address;
    }
}