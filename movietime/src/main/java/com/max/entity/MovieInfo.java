package com.max.entity;

import java.util.Date;

public class MovieInfo {
	private int id;
	private String filmName;
	private Date uploadTime;
	private long size;
	private String fileType;
	private String hotWord;
	private int playTimes;
	private int downloadTimes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getHotKey() {
		return hotWord;
	}

	public void setHotKey(String hotKey) {
		this.hotWord = hotKey;
	}

	public int getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(int playTimes) {
		this.playTimes = playTimes;
	}

	public int getDownTimes() {
		return downloadTimes;
	}

	public void setDownTimes(int downTimes) {
		this.downloadTimes = downTimes;
	}

	@Override
	public String toString() {
		return "movieInfo [id=" + id + ", filmName=" + filmName + ", uploadTime=" + uploadTime + ", size=" + size
				+ ", fileType=" + fileType + ", hotKey=" + hotWord + ", playTimes=" + playTimes + ", downTimes="
				+ downloadTimes + "]";
	}

}
