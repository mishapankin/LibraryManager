package mmp.librarymanager.repositories;

import mmp.librarymanager.entities.Author;
import mmp.librarymanager.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query("SELECT a from Publisher a")
    List<Publisher> publishers();
}