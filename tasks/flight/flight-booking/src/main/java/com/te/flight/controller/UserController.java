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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<GeneralResponse> saveUser(@RequestBody UserDto userDto) {
		log.trace("In the UserController saveUser api.");
		userDto = userService.saveUser(userDto);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user").toUriString());
		return ResponseEntity.created(uri)
				.body(new GeneralResponse(HttpStatus.CREATED, false, "User saved succesfully", userDto));
	}
}
