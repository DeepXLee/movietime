package com.max.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");

	public static String getMovieBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/movie";
		} else {
			basePath = "/home/movie";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}

}
