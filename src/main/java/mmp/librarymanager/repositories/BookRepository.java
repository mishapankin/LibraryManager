package mmp.librarymanager.repositories;

import mmp.librarymanager.dto.BookDTO;
import mmp.librarymanager.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select new mmp.librarymanager.dto.BookDTO(b.isbn, b.title, a.name, p.name, b.bookInstances.size) " +
            "from Book b join b.author a join b.publisher p where " +
            "is_same(:author, a.name)=1 and " +
            "is_same(:title, b.title)=1 and " +
            "(b.isbn like (:isbn || '%'))")
    Page<BookDTO> getBookInfo(String author, String title, String isbn, Pageable p);

    @Query("select distinct b.isbn from Book b")
    Iterable<String> getIsbns();

    @Query("select distinct b.title from Book b")
    Iterable<String> getTitles();
}