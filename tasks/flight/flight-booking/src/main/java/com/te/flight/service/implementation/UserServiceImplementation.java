package com.te.flight.service.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.te.flight.entity.User;
import com.te.flight.entity.dto.UserDto;
import com.te.flight.repository.UserRepository;
import com.te.flight.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
	private final UserRepository userRepository;

	@Override
	public UserDto saveUser(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user = userRepository.save(user);
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

}
