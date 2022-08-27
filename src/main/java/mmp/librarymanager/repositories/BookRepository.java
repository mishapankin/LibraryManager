package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.author a join fetch b.publisher p where " +
            "(lower(a.name) like concat(?1, '%') or lower(a.name) like concat('% ', ?1, '%')) and" +
            "(lower(b.title) like concat(?2, '%') or lower(b.title) like concat('% ', ?2, '%'))")
    List<Book> getFiltered(String name, String title, Pageable p);
}