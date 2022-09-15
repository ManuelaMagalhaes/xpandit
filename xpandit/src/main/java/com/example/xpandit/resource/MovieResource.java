package com.example.xpandit.resource;

import java.text.ParseException;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import com.example.xpandit.dto.MovieDTO;
import com.example.xpandit.dto.Response;
import com.example.xpandit.model.Movie;
import com.example.xpandit.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieResource {
	private final MovieService service;

	@Autowired
	public MovieResource(MovieService service) {
		this.service = service;
	}

	@GetMapping("/{title}")
	public ResponseEntity<Object> findMovie(@PathVariable String title) {
		try {
			Movie movie = service.findMovieByTitle(title);
			return new ResponseEntity<>(movie, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Object> createMovie(@RequestBody MovieDTO newMovie) {
		try {
			return new ResponseEntity<>(service.createMovie(newMovie), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<Object> updateOrCreateMovie(@RequestBody MovieDTO movie) {
		Movie movieUpdated;
		try {
			movieUpdated = service.updateOrCreateMovie(movie);

			return new ResponseEntity<>(movieUpdated, HttpStatus.OK);
		} catch (InvalidAttributeValueException | NumberFormatException | ParseException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_MODIFIED);
		}
	}

	// Atualiza
	@PatchMapping
	public ResponseEntity<Object> updateMovie(@RequestBody MovieDTO movie) {
		Movie movieUpdated;
		try {
			movieUpdated = service.updateMovie(movie);
			return new ResponseEntity<>(movieUpdated, HttpStatus.OK);
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_MODIFIED);
		}
	}

	@GetMapping(params = { "date" })
	public ResponseEntity<Object> findMovieFilteredByDate(@RequestParam String date) {
		try {
			Set<Movie> movie = service.findMovieByDate(date);
			return new ResponseEntity<>(movie, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable String id) {
		try {
			service.deleteMovieByID(id);
			
			return new ResponseEntity<>("Movie deleted with sucess", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
}
