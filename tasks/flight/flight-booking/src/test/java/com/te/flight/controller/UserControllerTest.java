package com.te.flight.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.te.flight.entity.dto.UserDto;
import com.te.flight.response.GeneralResponse;

public class UserControllerTest {

	private UserController userController = mock(UserController.class);

	@Test
	public void saveUser_test() {
		UserDto userDto = new UserDto("user001", "user001", "user1@abc.com", "9877654123");
		ResponseEntity<GeneralResponse> responseEntity = new ResponseEntity<>(GeneralResponse.builder()
				.status(HttpStatus.CREATED).error(false).message("user saved successfully").data(userDto).build(),
				HttpStatus.CREATED);

		when(userController.saveUser(userDto)).thenReturn(responseEntity);
		ResponseEntity<GeneralResponse> response = userController.saveUser(userDto);
		assertThat(response).isNotNull();
		assertEquals(responseEntity, response);
	}

}
