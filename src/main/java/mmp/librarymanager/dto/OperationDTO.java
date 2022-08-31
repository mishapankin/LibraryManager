package mmp.librarymanager.dto;

import java.util.Date;

public record OperationDTO(Long id, String isbn, Date date, Date dueDate, Date returnDate, String name, Long reader_id) { }
