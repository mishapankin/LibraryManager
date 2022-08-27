package mmp.librarymanager.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reader")
@Getter
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    public Reader() {}
    public Reader(String name) { this.name = name; }
}