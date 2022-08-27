package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b from Book b JOIN FETCH b.author a JOIN FETCH b.publisher p")
    List<Book> getBooksJoined();

    @Query("SELECT b from Book b JOIN FETCH b.author a JOIN FETCH b.publisher p WHERE a.name LIKE ?1")
    List<Book> getBooksJoinedAuthor(String name);
}