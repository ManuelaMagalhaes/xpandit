package com.example.xpandit.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import com.example.xpandit.dto.MovieDTO;
import com.example.xpandit.repository.MovieRepository;
import com.example.xpandit.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
public class MovieResourceTest {

	@Autowired
	WebApplicationContext webApplicationContext;

	private MockMvc mvc;
	
	@Autowired
	private MovieRepository repository;
	
	@BeforeAll
	private void setUp() throws ParseException {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		repository.createMovie("test",Utils.convertToCalendar("2022-09-12"), 8, 10000L);
		repository.createMovie("test1",Utils.convertToCalendar("2022-09-12"), 5, 10000L);
		repository.createMovie("test2",Utils.convertToCalendar("2022-09-14"), 10, 10000L);
	}

	@Test
	@DisplayName("Get Movie - OK")
	void findMovie() throws Exception, Exception {
		// arrange
		final String title = "test";
		String date = "2022-09-11T23:00:00.000+00:00";
		String rank = "8";
		String revenue = "10000";
		final String uri = "/movie/" + title;

		// act/assert

		mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(date))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(rank))
				.andExpect(MockMvcResultMatchers.jsonPath("$.revenue").value(revenue));
	}
	
	@Test
	@DisplayName("Get Movie - NOK")
	void findMovieNOK() throws Exception, Exception {
		// arrange
		final String title = "testNOK";
		final String uri = "/movie/" + title;

		// act/assert

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andDo(MockMvcResultHandlers.print()).andReturn();

		assertEquals("Invalid movie with title: " + title, result.getResponse().getContentAsString());

	}

	@Test
	@DisplayName("Get Movie Date - OK")
	void findMovieDate() throws Exception, Exception {
		// arrange
		final String uri = "/movie";

		// act/assert

		mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("date", "2022-09-12"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
	}
	
	@Test
	@DisplayName("Create Movie - OK")
	void createMovie() throws Exception, Exception {
		// arrange
		final String title = "newMovie";
		String date = "2022-09-12";
		String rank = "9";
		String revenue = "15000";

		final String uri = "/movie";

		MovieDTO newMovie = new MovieDTO(title, date, rank, revenue);

		// act/assert
		mvc.perform(MockMvcRequestBuilders.post(uri).content(asJsonString(newMovie))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(rank))
				.andExpect(MockMvcResultMatchers.jsonPath("$.revenue").value(revenue));
	}

	@Test
	@DisplayName("Create Movie - NotOK")
	void createMovieNOK() throws Exception, Exception {
		// arrange
		final String title = "newMovie";
		String date = "2022-09-12";
		String rank = "9";
		String revenue = "15000";

		final String uri = "/movie";

		MovieDTO newMovie = new MovieDTO(title, date, rank, revenue);

		// act/assert
		mvc.perform(MockMvcRequestBuilders.post(uri).content(asJsonString(newMovie))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Create Movie - InvalidTitle")
	void createMovieInvalidTitle() throws Exception, Exception {
		// arrange
		final String title = "";
		String date = "2022-09-12";
		String rank = "9";
		String revenue = "15000";

		final String uri = "/movie";

		MovieDTO newMovie = new MovieDTO(title, date, rank, revenue);

		// act/assert
		 MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).content(asJsonString(newMovie))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andDo(MockMvcResultHandlers.print()).andReturn();

		assertEquals("Title invalid", result.getResponse().getContentAsString());

	}
	
	@Test
	@DisplayName("UpdateOrCreate Movie - OK")
	void updateOrCreateMovie() throws Exception, Exception {
		// arrange
		final String title = "newMovie";
		String date = "2022-09-12";
		String rank = "9";
		String revenue = "15000";

		final String uri = "/movie";

		MovieDTO newMovie = new MovieDTO(title, date, rank, revenue);

		// act/assert
		mvc.perform(MockMvcRequestBuilders.put(uri).content(asJsonString(newMovie))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rank").value(rank))
				.andExpect(MockMvcResultMatchers.jsonPath("$.revenue").value(revenue));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
