package mmp.librarymanager.repositories;

import mmp.librarymanager.dto.OperationDTO;
import mmp.librarymanager.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select o.bookInstance.id from Operation o " +
            "where o.returnDate is null and o.bookInstance.book.isbn = ?1")
    Set<Long> getNotEndedByBookISBN(String isbn);

    @Query("select new mmp.librarymanager.dto.OperationDTO(" +
            "o.id, o.bookInstance.book.isbn, " +
            "o.date, o.dueDate, o.returnDate, " +
            "o.reader.name) " +
            "from Operation o")
    Page<OperationDTO> getBy(Pageable p);
}