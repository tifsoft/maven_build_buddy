package com.tifsoft.mavenbuildbuddy;

import java.io.File;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.gui.BuildBuddyMainGUI;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;
import com.tifsoft.mavenbuildbuddy.utils.PathFinder;

import ch.qos.logback.classic.Level;

public class MavenBuildBuddy {

	/*
	 * ToDo
	 * ====
	 * Simple progress indicator 
	 * Options panel: Skip tests?!? (extra options)
	 * Interrupt task! - destroy
	 * Support for multiple POM files in multiple tabs
	 * Count warnings and errors!!!
	 * Print top summary
	 * Print bottom summary
	 * Use fixed-width font
	 * Clean project
	 * Configurable path to Maven
	 * Support windows
	 * Put on GitHub
	 * Put on Tifsoft
	 * Better multi-tasking for build process (lower priority?)
	 * Create shaded jar file...
	 * 
	 * Done
	 * ====
	 * -Options panel: clean / resume
	 * -Support all maven build stages
	 */
	
	static Logger LOG;
	
	public static BuildBuddyMainGUI gui;
	
	public static final String rootPath = PathFinder.getPathToMainPOMFile();

	public static void main(final String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
		LOG = LoggerFactory.getLogger(MavenBuildBuddy.class);
		LOG.info(MBBMarkers.MBB, "Starting Maven Build Buddy");
		gui = new BuildBuddyMainGUI();
		gui.createGUI();
		LOG.info(MBBMarkers.MBB, "Maven Build Buddy set-up completed");
	}
	
	public static boolean isHeadless() {
		//return true;
		return "/".equals(File.separator);
	}

	public static boolean isOnLinux() {
		return "/".equals(File.separator);
	}
}
