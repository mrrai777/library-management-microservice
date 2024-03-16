package com.epam.library.service.api;

import java.util.List;

import com.epam.library.dto.UserDTO;

public interface LibraryUserService {
	
	List<UserDTO> getUsers();

	UserDTO getUser(String username);

	UserDTO createUser(UserDTO userDto);

	UserDTO updateUser(String username, UserDTO userDto);

	Void deleteUser(String username);

	void decrementUsersBookCount(List<String> users);
}
