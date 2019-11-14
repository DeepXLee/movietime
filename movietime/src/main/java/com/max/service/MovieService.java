package com.max.service;

import java.util.List;

import com.max.entity.MovieInfo;

public interface MovieService {
	// 分页查询所有电影
	List<MovieInfo> queryMovie(int limit, int offset, String filmName, String sort, String order);

	// 查询所有电影数量
	int queryMovieCount(String filmName);

	// 新增电影
	int addMovie(MovieInfo movieInfo);

	// 根据名字删除电影
	int deleteMovie(String filmName);

	// 更新电影信息
	int updateMovie(MovieInfo movieInfo);

	// 根据电影名字查询电影信息
	MovieInfo queryMovieByName(String filmName);

}
