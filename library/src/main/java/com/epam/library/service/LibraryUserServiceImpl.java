package com.epam.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.library.dto.BookDTO;
import com.epam.library.dto.UserDTO;
import com.epam.library.feign.BookFeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.repository.LibraryRepository;
import com.epam.library.service.api.LibraryUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryUserServiceImpl implements LibraryUserService {

	private final UserFeign userFeign;
	private final LibraryRepository libraryRepository;
	private final BookFeign bookFeign;

	@Override
	public List<UserDTO> getUsers() {
		log.info("Calling (UserFeign --> getUsers)");
		return userFeign.getUsers().getBody();
	}

	@Override
	public UserDTO getUser(String username) {
		return userFeign.getUser(username).getBody();
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {
		log.info("Calling (UserFeign --> createUser), User : {}", userDto);
		return userFeign.createUser(userDto).getBody();
	}

	@Override
	public UserDTO updateUser(String username, UserDTO userDto) {
		log.info("Calling (UserFeign --> updateUser), UserName : {}, UserDTO : {}", username, userDto);
		return userFeign.updateUser(username, userDto).getBody();
	}

	@Override
	public Void deleteUser(String username) {
		log.info("Finding all bookId with username : {}", username);
		List<Integer> bookIds = libraryRepository.findAllBookIdByUsername(username);

		increaseBooksQuantity(bookIds);

		log.info("Calling (LibraryRepository --> deleteAllByUsername), Username : {}", username);
		libraryRepository.deleteAllByUserName(username);

		log.info("Calling (UserFeign --> deleteUser), Username : {}", username);
		return userFeign.deleteUser(username).getBody();
	}

	private void increaseBooksQuantity(List<Integer> bookIds) {
		for (Integer bookId : bookIds) {
			log.info("Calling (BookFeign --> getBook), BookId : {}", bookId);
			BookDTO book = bookFeign.getBook(bookId).getBody();
			book.setBookQuantity(book.getBookQuantity() + 1);

			log.info("Calling (BookFeign--> updateBook), BookId : {}, BookDTO : {}", bookId, book);
			bookFeign.updateBook(bookId, book);
		}
	}

	@Override
	public void decrementUsersBookCount(List<String> users) {
		log.info("Calling (UserFeing --> decrementUsersBookCount), Users : {}", users);
		userFeign.decrementUsersBookCount(users);
	}
}
