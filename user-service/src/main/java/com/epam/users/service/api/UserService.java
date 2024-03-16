package com.epam.users.service.api;

import java.util.List;

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.BookException;

public interface UserService {

	List<UserDTO> findAllUser();

	UserDTO findUserByUsername(String username) throws BookException;

	UserDTO createUser(UserDTO userDto);

	UserDTO updateUser(String username, UserDTO userDto);

	void deleteUser(String username);

	void decrementBookCount(List<String> users);

}
