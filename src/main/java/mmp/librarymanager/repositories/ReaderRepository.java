package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Reader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    @Query("select r from Reader r where " +
            "is_same(:name, r.name)=1 and " +
            "(cast(r.id as text) like (:id || '%')) order by r.id desc")
    Iterable<Reader> getFiltered(String name, String id);
}