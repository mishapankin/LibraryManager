package mmp.librarymanager;

import mmp.librarymanager.dto.*;
import mmp.librarymanager.entities.Operation;
import mmp.librarymanager.repositories.*;
import mmp.librarymanager.entities.Reader;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    private OperationRepository operationRepository;

    @GetMapping("/api/get/book_info")
    public Iterable<BookDTO> getBookInfo(@RequestParam String author,
                                         @RequestParam String title,
                                         @RequestParam String isbn) {
        return bookRepository.getBookInfo(author, title, isbn);
    }

    @GetMapping("/api/get/book_instances/by_id")
    public Iterable<BookReducedInstanceDTO> getBookInstancesReduced(@RequestParam Long id) {
        return bookInstanceRepository.getReducedInfoById(id);
    }

    private record IsAvailable(boolean is_available) {}
    @GetMapping("/api/get/book_instance_available")
    public IsAvailable getBookInstanceAvailable(@RequestParam Long id) {
        return new IsAvailable(
                operationRepository.getNotReturnedByInstanceId(id).size() != 1
        );
    }

    @GetMapping("/api/get/book_instances")
    public Iterable<BookInstanceDTO> getBookInstances(@RequestParam String isbn) {
        Set<Long> all = bookInstanceRepository.findByBookISBN(isbn);
        Set<Long> notEnded = operationRepository.getNotEndedByBookISBN(isbn);

        return all.stream()
                .map(v -> new BookInstanceDTO(v, !notEnded.contains(v)))
                .collect(Collectors.toSet());
    }

    @GetMapping("/api/get/reader/by_id")
    public Iterable<String> getReadersById(@RequestParam Long id) {
        return readerRepository.getNamesById(id);
    }

    @GetMapping("/api/get/readers")
    public Iterable<Reader> getReaders(@RequestParam String name, @RequestParam String id) {
        return readerRepository.getFiltered(name, id);
    }

    @PostMapping("/api/post/reader")
    public Reader postReader(@RequestBody Reader reader) {
        readerRepository.save(reader);
        return reader;
    }

    @GetMapping("/api/get/operations")
    public Iterable<OperationDTO> getOperations(@RequestParam int page,
                                                @RequestParam int pageSize,
                                                @RequestParam String isbn,
                                                @RequestParam String title,
                                                @RequestParam String reader_id,
                                                @RequestParam String reader_name,
                                                @RequestParam String book_instance_id,
                                                @RequestParam boolean not_returned) {
        Pageable p = PageRequest.of(page, pageSize);
        return operationRepository.getFiltered(isbn,
                title,
                reader_id,
                reader_name,
                book_instance_id,
                not_returned, p);
    }

    @PostMapping("/api/post/operation")
    public String postOperation(@RequestBody OperationPostDTO body) {
        // TODO: add data validation

        Date today = new Date();

        final int OPERATION_DAYS = 14;
        Operation op = new Operation(
                bookInstanceRepository.getReferenceById(body.book_instance_id()),
                readerRepository.getReferenceById(body.reader_id()),
                today,
                DateUtils.addDays(today, OPERATION_DAYS)
        );

        operationRepository.save(op);
        return "{}";
    }
}
