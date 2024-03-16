package com.epam.library.utility;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.entity.Library;

@Mapper
public interface LibraryMapper {

	LibraryMapper INSTANCE = Mappers.getMapper(LibraryMapper.class);
	
	LibraryDTO convertToLibraryDTO(Library library);
	
	Library convertToLibrary(LibraryDTO libraryDto);

}
