package com.epam.users.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.users.dto.UserDTO;
import com.epam.users.entity.User;
import com.epam.users.exception.BookException;
import com.epam.users.repository.UserRepository;
import com.epam.users.utility.UserMapper;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper mapper;

	@InjectMocks
	private UserServiceImpl service;

	@Test
	void validGetAllUser() {
		User user = mock(User.class);
		UserDTO userDto = mock(UserDTO.class);
		when(userRepository.findAll()).thenReturn(List.of(user));
		when(mapper.convertToUserDTOs(any())).thenReturn(List.of(userDto));

		service.findAllUser();

		verify(userRepository).findAll();
	}

	@Test
	void validGetByUsername() throws BookException {
		User user = mock(User.class);
		UserDTO userDto = mock(UserDTO.class);
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
		when(mapper.convertToUserDTO(any())).thenReturn(userDto);
		service.findUserByUsername("user");
		verify(userRepository).findById("user");
		verify(mapper).convertToUserDTO(user);
	}

	@Test
	void invalidGetByUsername() throws BookException {
		when(userRepository.findById(any())).thenReturn(Optional.empty());
		Assertions.assertThrowsExactly(BookException.class, () -> service.findUserByUsername("demo"));
	}
	
	@Test
	void validCreateUser() {
		User user = mock(User.class);
		UserDTO userDto = mock(UserDTO.class);
		when(userRepository.save(any())).thenReturn(user);
		when(mapper.convertToUser(any())).thenReturn(user);
		when(mapper.convertToUserDTO(any())).thenReturn(userDto);
		
		service.createUser(userDto);
		
		verify(userRepository).save(user);
	}
	
	@Test
	void validUpdateUser() {
		User user = mock(User.class);
		UserDTO userDto = mock(UserDTO.class);
		when(userRepository.save(user)).thenReturn(user);
		when(mapper.convertToUser(any())).thenReturn(user);
		when(mapper.convertToUserDTO(any())).thenReturn(userDto);
		
		service.updateUser("demo", userDto);
		
		verify(userRepository).save(user);
	}
	
	@Test
	void validDeleteUser() {
		doNothing().when(userRepository).deleteById(any());
		service.deleteUser("demo");
		verify(userRepository).deleteById("demo");
	}
}
