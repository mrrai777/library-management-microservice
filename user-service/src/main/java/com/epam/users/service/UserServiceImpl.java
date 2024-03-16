package com.epam.users.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.users.dto.UserDTO;
import com.epam.users.entity.User;
import com.epam.users.exception.BookException;
import com.epam.users.repository.UserRepository;
import com.epam.users.service.api.UserService;
import com.epam.users.utility.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper mapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
		this.userRepository = userRepository;
		this.mapper = mapper;
	}

	@Override
	public List<UserDTO> findAllUser() {
		log.info("UserServiceImpl (getAllUser())");
		List<User> users = userRepository.findAll();
		return mapper.convertToUserDTOs(users);
	}

	@Override
	public UserDTO findUserByUsername(String username) throws BookException {
		log.info("UserServiceImpl (getByUsername()) Username : {}", username);
		User user = userRepository.findById(username).orElseThrow(() -> new BookException("Username not Found"));
		return mapper.convertToUserDTO(user);
	}

	@Override
	public UserDTO createUser(UserDTO userDto) {
		log.info("UserServiceImpl (createUser()) : {}", userDto);
		User user = userRepository.save(mapper.convertToUser(userDto));
		return mapper.convertToUserDTO(user);
	}

	@Override
	public UserDTO updateUser(String username, UserDTO userDto) {
		log.info("UserServiceImpl (updateUser()) : {}", userDto);
		userDto.setUsername(username);
		User user = userRepository.save(mapper.convertToUser(userDto));
		return mapper.convertToUserDTO(user);
	}

	@Override
	public void deleteUser(String username) {
		log.info("UserServiceImpl (deleteUser) Username : {}", username);
		userRepository.deleteById(username);
	}

	@Override
	public void decrementBookCount(List<String> users) {
		log.info("UserServiceImpl (decrementBookCount) Users : {} ", users);
		userRepository.decrementBookBorrowedByUsernames(users);
	}

}
