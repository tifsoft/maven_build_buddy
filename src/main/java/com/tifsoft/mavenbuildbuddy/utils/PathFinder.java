package com.tifsoft.mavenbuildbuddy.utils;

public class PathFinder {
	public static String getPathToMainPOMFile() {
		String pomPath = System.getenv("pom_path");
		pomPath = pomPath == null ? "/Users/timtyler/PS/knowledgeHub/pom.xml" : pomPath;
		return pomPath;
	}
}
