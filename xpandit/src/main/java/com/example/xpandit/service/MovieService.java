package com.example.xpandit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.management.InvalidAttributeValueException;

import com.example.xpandit.dto.MovieDTO;
import com.example.xpandit.dto.Response;
import com.example.xpandit.model.Movie;
import com.example.xpandit.repository.IMovieRepository;
import com.example.xpandit.repository.MovieRepository;
import com.example.xpandit.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MovieService {

	private final IMovieRepository repository;

	@Autowired
	public MovieService(IMovieRepository repository) {
		this.repository = repository;
	}

	public Movie findMovieByTitle(String movieTitle) throws NullPointerException {
		Movie movie = repository.findMovieByTitle(movieTitle);

		if (movie == null) {
			throw new NullPointerException("Invalid movie with title: " + movieTitle);
		}
		return movie;
	}

	public Movie createMovie(MovieDTO newMovie) throws Exception {

		if (repository.containsMovieWithTitle(newMovie.getTitle())) {
			throw new Exception("There is already a movie with this title");
		}
		titleIsValid(newMovie.getTitle());
		rankIsValid(newMovie.getRank());

		return repository.createMovie(newMovie.getTitle(), Utils.convertToCalendar(newMovie.getDate()),
				Integer.valueOf(newMovie.getRank()), Long.valueOf(newMovie.getRevenue()));
	}

	private void titleIsValid(String title) throws InvalidAttributeValueException {
		if (title.isBlank()) {
			throw new InvalidAttributeValueException("Title invalid");
		}
	}

	private void rankIsValid(String rank) throws InvalidAttributeValueException {
		String rankRegex = "^[0-9]$";

		Pattern pat = Pattern.compile(rankRegex);
		if (rank != null && !pat.matcher(rank).matches()) {

			throw new InvalidAttributeValueException("Rank invalid");
		}

	}

	public Movie updateOrCreateMovie(MovieDTO movieDto)
			throws InvalidAttributeValueException, NumberFormatException, ParseException {
		titleIsValid(movieDto.getTitle());
		rankIsValid(movieDto.getRank());
		Movie movie = repository.findMovieByTitle(movieDto.getTitle());

		if (movie == null) {

			repository.createMovie(movieDto.getTitle(), Utils.convertToCalendar(movieDto.getDate()),
					Integer.valueOf(movieDto.getRank()), Long.valueOf(movieDto.getRevenue()));
		}
		Movie movieCopy = copyMovie(movie);
		return repository.saveMovie(movieCopy);

	}

	private Movie copyMovie(Movie movie) {
		Movie newMovie = new Movie();
		newMovie.setId(movie.getId());
		if (movie.getTitle() != null) {
			newMovie.setTitle(movie.getTitle());
		}
		if (movie.getRank() != null) {
			newMovie.setRank(movie.getRank());
		}
		if (movie.getRevenue() != null) {
			newMovie.setRevenue(movie.getRevenue());
		}
		return newMovie;
	}

	public Movie updateMovie(MovieDTO movieDto) throws InvalidAttributeValueException {
		titleIsValid(movieDto.getTitle());
		rankIsValid(movieDto.getRank());
		Movie movie = repository.findMovieByTitle(movieDto.getTitle());

		if (movie == null) {
			throw new NullPointerException("Movie don't exist");
		}
		Movie movieCopy = copyMovie(movie);
		return repository.saveMovie(movieCopy);
	}

	public void deleteMovieByID(String id) {
		repository.deleteMovieByID(Long.valueOf(id));
	}

	public Set<Movie> findMovieByDate(String date) throws ParseException {
		return repository.findMovieByDate(Utils.convertToCalendar(date));
	}

}
