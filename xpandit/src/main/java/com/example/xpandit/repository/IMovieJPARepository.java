package com.example.xpandit.repository;

import com.example.xpandit.model.Movie;
import org.springframework.data.repository.CrudRepository;


public interface IMovieJPARepository extends CrudRepository <Movie, Long> {

	Movie findMovieByTitle(String movieTitle);
	
	boolean existsByTitle(String title);

}
