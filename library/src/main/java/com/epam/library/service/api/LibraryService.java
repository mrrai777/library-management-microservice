package com.epam.library.service.api;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.LibraryException;

public interface LibraryService {

	LibraryDTO issueBook(String username, Integer bookId) throws LibraryException;

	void releaseBook(String username, Integer bookId);

}
