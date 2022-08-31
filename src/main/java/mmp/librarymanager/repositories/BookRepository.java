package mmp.librarymanager.repositories;

import mmp.librarymanager.dto.BookDTO;
import mmp.librarymanager.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select new mmp.librarymanager.dto.BookDTO(b.isbn, b.title, a.name, p.name, b.bookInstanceList.size) " +
            "from Book b join b.author a join b.publisher p where" +
            "(lower(' ' || a.name) like ('% ' || ?1 || '%')) and" +
            "(lower(' ' || b.title) like ('% ' || ?2 || '%')) and" +
            "(b.isbn like (?3 || '%'))")
    Iterable<BookDTO> getBookInfo(String author, String title, String isbn);
}