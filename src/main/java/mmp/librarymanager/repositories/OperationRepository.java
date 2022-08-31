package mmp.librarymanager.repositories;

import mmp.librarymanager.dto.OperationDTO;
import mmp.librarymanager.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select o.bookInstance.id from Operation o " +
            "where o.returnDate is null and o.bookInstance.book.isbn = ?1")
    Set<Long> getNotEndedByBookISBN(String isbn);

    @Query("select new mmp.librarymanager.dto.OperationDTO(" +
            "o.id, o.bookInstance.book.title, " +
            "o.date, o.dueDate, o.returnDate, " +
            "o.reader.name, o.reader.id, o.bookInstance.id) " +
            "from Operation o where " +
                "is_same(:isbn, o.bookInstance.book.isbn)=1 and " +
                "is_same(:title, o.bookInstance.book.title)=1 and " +
                "(cast(o.reader.id as text) like (:reader_id || '%')) and " +
                "is_same(:reader_name, o.reader.name)=1 and " +
                "(cast(o.bookInstance.id as text) like (:book_instance_id || '%')) and " +
                "((:not_returned = FALSE) or (o.returnDate is null))")
    Page<OperationDTO> getFiltered(String isbn,
                                   String title,
                                   String reader_id,
                                   String reader_name,
                                   String book_instance_id,
                                   boolean not_returned,
                                   Pageable p);
}