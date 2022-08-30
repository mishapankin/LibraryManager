package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Reader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    @Query("select r from Reader r where " +
            "(lower(' ' || r.name) like ('% ' || ?1 || '%')) and" +
            "(cast(r.id as text) like (?2 || '%'))")
    Iterable<Reader> getFiltered(String name, String id);
}