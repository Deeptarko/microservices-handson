package com.deep.userservice.service;

import com.deep.userservice.entity.User;
import com.google.common.base.Optional;

public interface UserService {
	
	public User saveUser(User user);
	public Optional<User> findByUsername(String username);

}
