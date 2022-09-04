package mmp.librarymanager;

import mmp.librarymanager.dto.*;
import mmp.librarymanager.entities.Operation;
import mmp.librarymanager.repositories.*;
import mmp.librarymanager.entities.Reader;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/get/book_info")
    public Page<BookDTO> getBookInfo(@RequestParam String author,
                                         @RequestParam String title,
                                         @RequestParam String isbn,
                                         @RequestParam int page,
                                         @RequestParam int pageSize) {
        Pageable p = PageRequest.of(page, pageSize);
        return bookRepository.getBookInfo(author, title, isbn, p);
    }

    @GetMapping("/get/authors")
    public Iterable<String> getAuthors() {
      return authorRepository.getAuthors();
    };

    @GetMapping("/get/isbns")
    public Iterable<String> getIsbns() {
        return bookRepository.getIsbns();
    }

    @GetMapping("/get/titles")
    public Iterable<String> getTitles() {
        return bookRepository.getTitles();
    }

    @GetMapping("/get/publishers")
    public Iterable<String> getPublishers() {
        return publisherRepository.getPublishers();
    }

    @GetMapping("/get/book_instances/by_id")
    public Iterable<BookReducedInstanceDTO> getBookInstancesReduced(@RequestParam Long id) {
        return bookInstanceRepository.getReducedInfoById(id);
    }

    private record IsAvailable(boolean is_available) {}
    @GetMapping("/get/book_instance_available")
    public IsAvailable getBookInstanceAvailable(@RequestParam Long id) {
        return new IsAvailable(
                operationRepository.getNotReturnedByInstanceId(id).size() != 1
        );
    }

    @GetMapping("/get/book_instances")
    public Iterable<BookInstanceDTO> getBookInstances(@RequestParam String isbn) {
        Set<Long> all = bookInstanceRepository.findByBookISBN(isbn);
        Set<Long> notEnded = operationRepository.getNotEndedByBookISBN(isbn);

        return all.stream()
                .map(v -> new BookInstanceDTO(v, !notEnded.contains(v)))
                .collect(Collectors.toSet());
    }

    @GetMapping("/get/reader/by_id")
    public Iterable<String> getReadersById(@RequestParam Long id) {
        return readerRepository.getNamesById(id);
    }

    @GetMapping("/get/readers")
    public Page<Reader> getReaders(@RequestParam String name,
                                       @RequestParam String id,
                                       @RequestParam int page,
                                       @RequestParam int pageSize) {
        if (page < 0) {
            page = 1;
        }
        if (pageSize <= 0) {
            pageSize = 1;
        }
        Pageable p = PageRequest.of(page, pageSize);
        return readerRepository.getFiltered(name, id, p);
    }

    private record PostReaderError(boolean name, boolean address, boolean phone, boolean email) {}
    @PostMapping("/post/reader")
    public ResponseEntity<Object> postReader(@RequestBody Reader reader) {
        boolean nameErr = reader.getName().equals("");
        boolean addressErr = false;
        boolean phoneErr = false;
        boolean emailErr = false;

        if (nameErr || addressErr || phoneErr || emailErr) {
            return new ResponseEntity<>(new PostReaderError(nameErr, addressErr, phoneErr, emailErr),
                HttpStatus.BAD_REQUEST
            );
        }

        readerRepository.save(reader);
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    @GetMapping("/get/operations")
    public Iterable<OperationDTO> getOperations(@RequestParam int page,
                                                @RequestParam int pageSize,
                                                @RequestParam String isbn,
                                                @RequestParam String title,
                                                @RequestParam String reader_id,
                                                @RequestParam String reader_name,
                                                @RequestParam String book_instance_id,
                                                @RequestParam boolean not_returned) {
        if (page < 0) {
            page = 1;
        }
        if (pageSize <= 0) {
            pageSize = 1;
        }
        Pageable p = PageRequest.of(page, pageSize);
        return operationRepository.getFiltered(isbn,
                title,
                reader_id,
                reader_name,
                book_instance_id,
                not_returned, p);
    }

    @PostMapping("/post/operation")
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

    private record OperationId(Long id) {}
    @PostMapping("/update/operation")
    public String updateOperation(@RequestBody OperationId id) {
        Optional<Operation> operation = operationRepository.findById(id.id());
        if (operation.isPresent()) {
            Operation o = operation.get();
            o.setReturnDate(new Date());
            operationRepository.save(o);
        }
        return "{}";
    }
}
