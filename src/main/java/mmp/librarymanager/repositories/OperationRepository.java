package mmp.librarymanager.repositories;

import mmp.librarymanager.dto.BookInstanceDTO;
import mmp.librarymanager.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select o.bookInstance.id from Operation o " +
            "where o.returnDate is null and o.bookInstance.book.isbn = ?1")
    Set<Long> getNotEnded(String isbn);
}