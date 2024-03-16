package com.epam.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.library.entity.Library;

import jakarta.transaction.Transactional;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

	@Query("select l.userName from Library l WHERE l.bookId = :bookId")
	List<String> findAllUsernameByBookId(Integer bookId);

	@Query("select l.bookId from Library l WHERE l.userName = :userName")
	List<Integer> findAllBookIdByUsername(String userName);

	@Transactional
	void deleteAllByBookId(Integer bookId);

	@Transactional
	void deleteAllByUserName(String userName);

	Library findByUserNameAndBookId(String userName, Integer bookId);

	@Transactional
	void deleteByUserNameAndBookId(String userName, Integer bookId);
}
