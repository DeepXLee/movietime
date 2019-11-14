package com.max.service;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.max.BaseTest;
import com.max.entity.MovieInfo;

public class MovieServiceTest extends BaseTest{
	@Autowired
	private MovieService movieService;
	
	@Test
	@Ignore
	public void addMovieTest() {
		MovieInfo movieInfo1 = new MovieInfo();
		movieInfo1.setFilmName("新文3.txt");
		movieInfo1.setUploadTime(new Date());
		movieInfo1.setSize(1000);
		movieInfo1.setFileType("txt");
		movieService.addMovie(movieInfo1);
	}
	
	@Test
	public void queryMovieByNameTest() {
		MovieInfo movieInfo = movieService.queryMovieByName("黑衣人4.mp4");
		System.out.println(movieInfo);
	}
	
}
