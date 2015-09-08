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
	 * Preferences: Configurable path to Maven.
	 * Preferences: Add new POM.
	 * Design cool dock icon for OSX users.
	 * Command line - allow POM specification.
	 * Add persistent per-section colors.
	 * Button to jump to relevant module.
	 * Enable and disable main launch buttons according to state.
	 * Options panel: mvn command prefix.
	 * Options panel: ignore modules/profiles.
	 * Options panel: "refresh POM" button.
	 * Support multiple POM files in multiple tabs.
	 * Support multiple profiles in multiple tabs.
	 * Print top summary: command executed.
	 * Display count of warnings and errors in 'augmented' module summary at end.
	 * Better support for Windows.
	 * OSX - include task bar icon and instructions.
	 * Put on main web site.
	 * Preferences: Allow color configuration.
	 * Preferences: Add default for word-wrap option.
	 * Preferences: Persist preferences on disk.
	 * Better multi-tasking for build process (lower priority thread?).
	 * Create shaded jar file.
	 * Deploy using web start - http://stackoverflow.com/questions/20051727/changing-java-webstart-hover-text-over-osx-mavericks-dock-icon
	 * Create a monitor for unit test pass, skips and failures.
	 * Create archive of build history.
	 * Visualise build history.
	 * 
	 * Done
	 * ====
	 * -Preferences: Fixed-width font for text output 
	 * -Preferences: Font name, size and Color 
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
    	System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Maven Build Buddy");
		//com.apple.eawt.Application.getApplication().setAboutHandler();//arg0);setDockIconBadge("Maven Build Buddy");
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
