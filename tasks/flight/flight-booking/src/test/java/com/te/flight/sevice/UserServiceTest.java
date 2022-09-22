package com.te.flight.sevice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.te.flight.entity.dto.UserDto;
import com.te.flight.service.UserService;

public class UserServiceTest {

	private UserService userService = mock(UserService.class);

	@Test
	public void saveUser_test() {
		UserDto userDto = new UserDto("user001", "user001", "user1@abc.com", "9877654123");
		when(userService.saveUser(userDto)).thenReturn(userDto);
		UserDto saveUser = userService.saveUser(userDto);
		assertThat(saveUser).isNotNull();
		assertEquals(userDto, saveUser);
	}

}
