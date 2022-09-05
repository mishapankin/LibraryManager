package mmp.librarymanager;

import mmp.librarymanager.entities.Book;
import mmp.librarymanager.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersistenceTests {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookInstanceRepository bookInstanceRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    ReaderRepository readerRepository;

    @Test
    void contextLoads() {
        assertThat(authorRepository).isNotNull();
        assertThat(bookInstanceRepository).isNotNull();
        assertThat(operationRepository).isNotNull();
        assertThat(publisherRepository).isNotNull();
        assertThat(readerRepository).isNotNull();
    }

    @Test
    void authorsNames() {
        int cnt = 0;
        for (String el: authorRepository.getAuthors()) {
            cnt += 1;
        }
        Assertions.assertEquals(cnt, 10);
    }

    @Test
    void bookInstances() {
        for (int i = 0; i < 10; ++i) {
            Book b = bookRepository.findAll().get(i);

            Set<Long> all = bookInstanceRepository.findByBookISBN(b.getIsbn());
            Set<Long> notEnded = operationRepository.getNotEndedByBookISBN(b.getIsbn());

            assert(all.size() >= notEnded.size());
        }
    }


    @Test
    void deleteReader() {
        Long id = 1L;
        readerRepository.deleteById(id);
        assertThat(readerRepository.getNamesById(id)).containsOnly();
        Pageable p = PageRequest.of(0, 1000);
        assertThat(operationRepository
                .getFiltered("", "", id + "!", "", "", false, p))
                .containsOnly();
    }
}
