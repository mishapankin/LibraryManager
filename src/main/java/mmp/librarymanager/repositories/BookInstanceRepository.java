package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
    @Query("select b.id " +
            "from BookInstance b where " +
            "b.book.isbn = ?1")
    Set<Long> findByBookISBN(String isbn);
}