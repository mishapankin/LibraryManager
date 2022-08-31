package mmp.librarymanager.dto;

import java.util.Date;

public record OperationDTO(Long id,
                           String title,
                           Date date,
                           Date dueDate,
                           Date returnDate,
                           String name,
                           Long reader_id,
                           Long book_instance_id) { }
