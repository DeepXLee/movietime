package com.max.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.max.dao.MovieDao;
import com.max.entity.MovieInfo;
import com.max.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieDao movieDao;
	
	@Override
	public List<MovieInfo> queryMovie(int limit, int offset, String filmName, String sort, String order) {
		if("uploadTime".equals(sort)) {
			sort = "upload_time";
		}
		if("hotWord".equals(sort)) {
			sort = "hot_word";
		}
		if("playTimes".equals(sort)) {
			sort = "play_times";
		}
		if("downloadTimes".equals(sort)) {
			sort = "download_times";
		}
		return movieDao.queryMovie(limit, offset, filmName, sort, order);
	}

	@Override
	public int queryMovieCount(String filmName) {
		return movieDao.queryMovieCount(filmName);
	}

	@Override
	public int addMovie(MovieInfo movieInfo) {
		return movieDao.addMovie(movieInfo);
	}

	@Override
	public int deleteMovie(String filmName) {
		return movieDao.deleteMovie(filmName);
	}

	@Override
	public int updateMovie(MovieInfo movieInfo) {
		return movieDao.updateMovie(movieInfo);
	}

	@Override
	public MovieInfo queryMovieByName(String filmName) {
		return movieDao.queryMovieByName(filmName);
	}

}
