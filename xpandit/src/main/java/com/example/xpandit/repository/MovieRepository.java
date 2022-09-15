package com.example.xpandit.repository;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import com.example.xpandit.model.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("movie")
public class MovieRepository implements IMovieRepository {

	IMovieJPARepository jpaRepository;

	@Autowired
	public MovieRepository(IMovieJPARepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Movie findMovieByTitle(String movieTitle) throws NullPointerException {

		return jpaRepository.findMovieByTitle(movieTitle);

	}

	@Override
	public Movie createMovie(String title, Calendar date, Integer rank, Long revenue) {
		Movie movieToSave = new Movie(title, date, rank, revenue);
		Movie movie = jpaRepository.save(movieToSave);
		return movie;
	}

	@Override
	public boolean containsMovieWithTitle(String title) {
		return jpaRepository.existsByTitle(title);
	}

	@Override
	public Movie saveMovie(Movie movieCopy) {
		return jpaRepository.save(movieCopy);
	}

	@Override
	public void deleteMovieByID(Long id) {
		jpaRepository.deleteById(id);
	}



	@Override
	public Set<Movie> findMovieByDate(Calendar date) {
		Iterator<Movie> allMovies = jpaRepository.findAll().iterator();
		
		Set<Movie> moviesSet = new HashSet<>();
		while(allMovies.hasNext()) {
			Movie movie = allMovies.next();
			if(date.equals(movie.getDate())) {
				moviesSet.add(movie);				
			}
		}
		return moviesSet;
	}

}
