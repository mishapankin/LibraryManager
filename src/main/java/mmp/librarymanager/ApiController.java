package mmp.librarymanager;

import mmp.librarymanager.entities.Author;
import mmp.librarymanager.entities.Book;
import mmp.librarymanager.entities.Publisher;
import mmp.librarymanager.repositories.AuthorRepository;
import mmp.librarymanager.repositories.BookRepository;
import mmp.librarymanager.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/api/get/authors")
    public Iterable<Author> getAuthors() {
        return authorRepository.authors();
    }

    @GetMapping("/api/get/books")
    public Iterable<Book> getBooks(@RequestParam String author,
                                   @RequestParam String title,
                                   @RequestParam String isbn) {
        Pageable page = PageRequest.of(0, 1000);
        return bookRepository.getFiltered(author.toLowerCase(), title.toLowerCase(), isbn, page);
    }

    @GetMapping("/api/get/publishers")
    public Iterable<Publisher> getPublishers() {
        return publisherRepository.publishers();
    }
}
