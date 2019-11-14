package com.max.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.max.entity.MovieInfo;

public interface MovieDao {
	// 分页查询所有电影
	List<MovieInfo> queryMovie(@Param("limit")int limit, @Param("offset")int offset, @Param("filmName")String filmName, @Param("sort")String sort, @Param("order")String order);

	// 查询所有电影数量
	int queryMovieCount(@Param("filmName")String filmName);

	// 新增电影
	int addMovie(MovieInfo movieInfo);

	// 根据名字删除电影
	int deleteMovie(String filmName);
	
	//更新电影信息
	int updateMovie(MovieInfo movieInfo);
	
	//根据电影名字查询电影信息
	MovieInfo queryMovieByName(String filmName);
}
