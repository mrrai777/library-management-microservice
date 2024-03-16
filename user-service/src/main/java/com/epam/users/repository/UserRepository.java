package com.epam.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.users.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, String> {
	List<User> findAll();

	@Modifying
    @Transactional
    @Query("UPDATE User u SET u.bookBorrowed = u.bookBorrowed - 1 WHERE u.username IN :users")
	void decrementBookBorrowedByUsernames(List<String> users);
}
