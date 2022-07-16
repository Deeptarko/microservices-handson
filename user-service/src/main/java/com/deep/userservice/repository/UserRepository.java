package com.deep.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deep.userservice.entity.User;
import com.google.common.base.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User>findByUsername(String username);
}
