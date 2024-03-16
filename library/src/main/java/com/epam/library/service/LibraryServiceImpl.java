package com.epam.library.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.epam.library.dto.BookDTO;
import com.epam.library.dto.LibraryDTO;
import com.epam.library.dto.UserDTO;
import com.epam.library.entity.Library;
import com.epam.library.exception.LibraryException;
import com.epam.library.repository.LibraryRepository;
import com.epam.library.service.api.LibraryBookService;
import com.epam.library.service.api.LibraryService;
import com.epam.library.service.api.LibraryUserService;
import com.epam.library.utility.LibraryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

	private final LibraryBookService libraryBookService;
	private final LibraryUserService libraryUserService;
	private final LibraryRepository libraryRepository;

	@Override
	public LibraryDTO issueBook(String username, Integer bookId) throws LibraryException {

		UserDTO user = getVerifiedUser(username);
		BookDTO book = getVerifiedBookId(bookId);
		verifyAlreadyIssued(username, bookId);

		increaseUserBorrowCount(username, user);
		decreaseBookQuantity(bookId, book);

		Library library = Library.builder().bookId(bookId).userName(username).build();
		log.info("Calling (LibraryRepository --> save), LibraryDTO : {}", library);

		library = libraryRepository.save(library);

		return LibraryMapper.INSTANCE.convertToLibraryDTO(library);
	}

	private void decreaseBookQuantity(Integer bookId, BookDTO book) {
		book.setBookQuantity(book.getBookQuantity() - 1);
		log.info("Calling (LibraryBookService --> updateBook), BookId : {}, BookDTO : {}", bookId, book);
		libraryBookService.updateBook(bookId, book);
	}

	private void increaseUserBorrowCount(String username, UserDTO user) {
		user.setBookBorrowed(user.getBookBorrowed() + 1);
		log.info("Calling (LibraryUserService --> updateUser), Username : {}, UserDTO : {}", username, user);
		libraryUserService.updateUser(username, user);
	}

	private void verifyAlreadyIssued(String username, Integer bookId) throws LibraryException {
		Library lib = libraryRepository.findByUserNameAndBookId(username, bookId);
		if (Objects.nonNull(lib)) {
			throw new LibraryException("User already have this book! Cann't Issue same book again.");
		}
	}

	BookDTO getVerifiedBookId(Integer bookId) throws LibraryException {
		BookDTO bookDto = libraryBookService.getBook(bookId);

		if (Objects.isNull(bookDto) || bookDto.getBookQuantity() < 1) {
			throw new LibraryException("Book not available");
		}

		return bookDto;
	}

	UserDTO getVerifiedUser(String username) throws LibraryException {
		UserDTO userDto = libraryUserService.getUser(username);
		if (Objects.isNull(userDto)) {
			throw new LibraryException("Invalid User name");
		}

		if (userDto.getBookBorrowed() > 2) {
			throw new LibraryException("You have reached you maximum limit you can not take extra book");
		}
		return userDto;
	}

	@Override
	public void releaseBook(String username, Integer bookId) {

		Library library = libraryRepository.findByUserNameAndBookId(username, bookId);
		if (Objects.nonNull(library)) {
			increaseBookQuantity(bookId);

			decreaseUserBorrowedCount(username);
		}
		libraryRepository.deleteByUserNameAndBookId(username, bookId);
	}

	private void decreaseUserBorrowedCount(String username) {
		UserDTO userDto = libraryUserService.getUser(username);
		userDto.setBookBorrowed(userDto.getBookBorrowed() - 1);
		log.info("Calling (LibraryUserService --> updateUser), Username : {}, UserDTO : {}", username, userDto);
		libraryUserService.updateUser(username, userDto);
	}

	private void increaseBookQuantity(Integer bookId) {
		BookDTO bookDto = libraryBookService.getBook(bookId);
		bookDto.setBookQuantity(bookDto.getBookQuantity() + 1);
		log.info("Calling (LibraryBookService --> updateBook), BookId : {}, BookDTO : {}", bookId, bookDto);
		libraryBookService.updateBook(bookId, bookDto);
	}
}
