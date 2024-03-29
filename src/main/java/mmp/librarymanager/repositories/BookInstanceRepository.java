package mmp.librarymanager.repositories;

import mmp.librarymanager.dto.BookReducedInstanceDTO;
import mmp.librarymanager.entities.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
    @Query("select b.id " +
            "from BookInstance b where " +
            "b.book.isbn = :isbn")
    Set<Long> findByBookISBN(String isbn);

    @Query("select new mmp.librarymanager.dto.BookReducedInstanceDTO(b.book.author.name, " +
            "b.book.title) " +
            "from BookInstance b where b.id = :id")
    Iterable<BookReducedInstanceDTO> getReducedInfoById(Long id);
}