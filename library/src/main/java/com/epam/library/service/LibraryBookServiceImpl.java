package com.epam.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.library.dto.BookDTO;
import com.epam.library.feign.BookFeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.repository.LibraryRepository;
import com.epam.library.service.api.LibraryBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryBookServiceImpl implements LibraryBookService {

	private final BookFeign bookFeign;
	private final UserFeign userFeign;
	private final LibraryRepository libraryRepository;

	@Override
	public List<BookDTO> getBooks(Integer pageNumber, Integer pageSize) {
		log.info("Calling (BookFeign --> getBooks), PageNumber : {}, PageSize : {}", pageNumber, pageSize);
		return bookFeign.getBooks(pageNumber, pageSize).getBody();
	}

	@Override
	public BookDTO getBook(Integer id) {
		log.info("Calling (BookFeign --> getBook), BookId : {}", id);
		return bookFeign.getBook(id).getBody();
	}

	@Override
	public BookDTO createBook(BookDTO bookDto) {
		log.info("Calling (BookFeign --> createBook), Book : {}", bookDto);
		return bookFeign.createBook(bookDto).getBody();
	}

	@Override
	public BookDTO updateBook(Integer id, BookDTO bookDto) {
		log.info("Calling (BookFeign --> updateBook), BookID : {}, BookDTO : {}", id, bookDto);
		return bookFeign.updateBook(id, bookDto).getBody();
	}

	@Override
	public Void deleteBook(Integer id) {
		log.info("Finding all usernames with bookID : {}", id);
		List<String> users = libraryRepository.findAllUsernameByBookId(id);

		log.info("Calling (userFeign --> decrementUsersBookCount), Users : {}", users);
		userFeign.decrementUsersBookCount(users);

		log.info("Calling (LibraryRepository --> deleteAllByBookId), BookID : {}", id);
		libraryRepository.deleteAllByBookId(id);

		log.info("Calling (BookFeign --> deleteBook), BookID : {}", id);
		return bookFeign.deleteBook(id).getBody();
	}
}
