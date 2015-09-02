package com.tifsoft.mavenbuildbuddy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.gui.BuildBuddyMainGUI;
import com.tifsoft.mavenbuildbuddy.model.BuildPOM;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;
import com.tifsoft.mavenbuildbuddy.utils.PathFinder;

import ch.qos.logback.classic.Level;

public class MavenBuildBuddy {

	/*
	 * ToDo
	 * ====
	 * Command line - allow POM specification.
	 * Preferences: Fixed-width font for text output 
	 * Preferences: Font name, size and Color 
	 * Preferences: Configurable path to Maven
	 * Preferences: Word wrap option
	 * Options panel: mvn command prefix
	 * Options panel: ignore modules/profiles
	 * Options panel: "refresh POM" button 
	 * Support multiple POM files in multiple tabs
	 * Support multiple profiles in multiple tabs
	 * Print top summary: command executed
	 * Display count of warnings and errors in augmented module summary at end
	 * Better support for Windows
	 * Put on Tifsoft
	 * Better multi-tasking for build process (lower priority thread?)
	 * Create shaded jar file...
	 * Create archive of build history
	 * View build history
	 * 
	 * Done
	 * ====
	 * -Clean project
	 * -Put on GitHub
	 * -Options panel: clean / resume
	 * -Support all maven build stages
	 -* Simple progress indicator 
	 -* Interrupt task - destroy mvn process
	 */
	
	static Logger LOG;
	
	public static BuildBuddyMainGUI gui;
	
	public static final String rootPath = PathFinder.getPathToMainPOMFile();
	
	public static Map<String, BuildPOM> pomMap = new HashMap<String, BuildPOM>();

	public static void main(final String[] args) {
		final ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
		pomMap.put(BuildPOM.DEFAULT_POM, new BuildPOM(BuildPOM.DEFAULT_POM));
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
