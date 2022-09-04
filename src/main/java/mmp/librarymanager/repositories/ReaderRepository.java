package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    @Query("select r from Reader r where " +
            "is_same(:name, r.name)=1 and " +
            "(cast(r.id as text) like (:id || '%')) order by r.id desc")
    Page<Reader> getFiltered(String name, String id, Pageable p);

    @Query("select r.name from Reader r where r.id = :id")
    Iterable<String> getNamesById(Long id);
}