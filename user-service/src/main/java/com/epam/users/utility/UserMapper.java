package com.epam.users.utility;

import java.util.List;

import org.mapstruct.Mapper;

import com.epam.users.dto.UserDTO;
import com.epam.users.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO convertToUserDTO(User user);

	User convertToUser(UserDTO userDto);

	List<UserDTO> convertToUserDTOs(List<User> users);

}
