package com.datapine.service;

import com.datapine.domain.User;

public interface UserService {

	User register(String email, String password);

	User updatePassword(Long userId, String oldPassword, String newPassword);

	// add more methods here

}
