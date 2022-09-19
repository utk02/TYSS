package com.te.flight.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.te.flight.entity.dto.UserDto;
import com.te.flight.response.GeneralResponse;
import com.te.flight.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/save")
	public ResponseEntity<GeneralResponse> saveUser(@RequestBody UserDto userDto) {
		userDto = userService.saveUser(userDto);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user").toUriString());
		return ResponseEntity.created(uri)
				.body(new GeneralResponse(HttpStatus.CREATED, null, "User saved succesfully", userDto));
	}
}
