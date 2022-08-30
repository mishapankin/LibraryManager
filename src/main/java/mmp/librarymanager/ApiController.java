package mmp.librarymanager;

import mmp.librarymanager.dto.BookInfo;
import mmp.librarymanager.entities.Book;
import mmp.librarymanager.entities.Reader;
import mmp.librarymanager.repositories.AuthorRepository;
import mmp.librarymanager.repositories.BookRepository;
import mmp.librarymanager.repositories.PublisherRepository;
import mmp.librarymanager.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping("/api/get/book_info")
    public Iterable<BookInfo> getBookInfo(@RequestParam String author,
                                          @RequestParam String title,
                                          @RequestParam String isbn) {
        return bookRepository.getBookInfo(author.toLowerCase(), title.toLowerCase(), isbn);
    }

    @GetMapping("/api/get/readers")
    public Iterable<Reader> getReaders(@RequestParam String name, @RequestParam String id) {
        return readerRepository.getFiltered(name.toLowerCase(), id);
    }

    @PostMapping("/api/post/reader")
    public Reader postReader(@RequestBody Reader reader) {
        readerRepository.save(reader);
        return reader;
    }
}
