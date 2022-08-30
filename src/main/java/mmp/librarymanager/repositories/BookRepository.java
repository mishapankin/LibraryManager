package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b " +
            "from Book b join fetch b.author a join fetch b.publisher p where " +
            "(lower(' ' || a.name) like ('% ' || ?1 || '%')) and" +
            "(lower(' ' || b.title) like ('% ' || ?2 || '%')) and" +
            "(b.isbn like (?3 || '%'))")
    List<Book> getFiltered(String name, String title, String isbn);
}