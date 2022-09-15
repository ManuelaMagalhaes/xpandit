package com.example.xpandit.repository;

import java.util.Calendar;
import java.util.Set;

import com.example.xpandit.model.Movie;
import org.springframework.data.repository.CrudRepository;


public interface IMovieRepository{

	Movie findMovieByTitle(String movieTitle);

	Movie createMovie(String title, Calendar date, Integer rank, Long revenue);

	boolean containsMovieWithTitle(String title);

	Movie saveMovie(Movie movieCopy);

	void deleteMovieByID(Long id);

	Set<Movie> findMovieByDate(Calendar date);


}
