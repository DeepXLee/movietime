package com.max.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.max.BaseTest;
import com.max.entity.MovieInfo;

public class MovieDaoTest extends BaseTest {

	@Autowired
	private MovieDao movieDao;
	
	@Test
	@Ignore
	public void addMovieTest() {
		MovieInfo movieInfo1 = new MovieInfo();
		movieInfo1.setFilmName("复联3.mp4");
		movieInfo1.setUploadTime(new Date());
		movieInfo1.setSize(1000);
		movieInfo1.setFileType("mp4");
		movieDao.addMovie(movieInfo1);
	}
	
	@Test
	@Ignore
	public void queryMovie() {
		List<MovieInfo> movieInfos = movieDao.queryMovie(5, 0, null, "size", "asc");
		int count = movieDao.queryMovieCount(null);
		System.out.println(movieInfos);
		System.out.println("movie:"+movieInfos.size());
		System.out.println("count:" + count);
		
	}
	
	@Test
	@Ignore
	public void updateMovie() {
		MovieInfo movieInfo1 = new MovieInfo();
		movieInfo1.setFilmName("黑衣人4.mp4");
		movieInfo1.setHotKey("欧美");
		movieInfo1.setDownTimes(5);
		movieInfo1.setPlayTimes(20);
		movieDao.updateMovie(movieInfo1);
	}
	
	@Test
	@Ignore
	public void queryMovieByName() {
		MovieInfo movieInfo = movieDao.queryMovieByName("黑衣人4.mp4");
		System.out.println("movie:"+movieInfo);
	}
	
	
}
