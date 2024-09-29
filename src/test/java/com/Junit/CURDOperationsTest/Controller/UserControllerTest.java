package com.Junit.CURDOperationsTest.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.Junit.CURDOperationsTest.Entity.User;
import com.Junit.CURDOperationsTest.Repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	void testGetUserSuccess() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("Rahul");
		user.setEmail("rahul@gmail.com");

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Rahul"))
				.andExpect(jsonPath("$.email").value("rahul@gmail.com"));
	}

	@Test
	void testGetUserNotFound() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/users/1")).andExpect(status().isNotFound());
	}

	@Test
	void testCreateUserSuccess() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("Raj");
		user.setEmail("raj@gmail.com");

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Raj\",\"email\":\"raj@gmail.com\"}")).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Raj"))
				.andExpect(jsonPath("$.email").value("raj@gmail.com"));
	}

	@Test
	void testUpdateUserSuccess() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("Rahul");
		user.setEmail("rahul@gmail.com");

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Updated Name\",\"email\":\"updated@example.com\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Name"))
				.andExpect(jsonPath("$.email").value("updated@example.com"));
	}

	@Test
	void testUpdateUserNotFound() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Updated Name\",\"email\":\"updated@example.com\"}"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteUserSuccess() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("Rahul");
		user.setEmail("rahul@gmail.com");

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
	}

	@Test
	void testDeleteUserNotFound() throws Exception {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(delete("/users/1")).andExpect(status().isNotFound());
	}

	@Test
	void testCreateUserValidationFailure() throws Exception {
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"\",\"email\":\"invalid email\"}"))
		        .andExpect(status().isBadRequest());
	}
}
