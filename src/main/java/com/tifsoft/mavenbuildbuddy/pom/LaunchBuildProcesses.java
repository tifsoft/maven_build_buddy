package com.tifsoft.mavenbuildbuddy.pom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;
import com.tifsoft.mavenbuildbuddy.utils.PathFinder;
import com.tifsoft.processmanager.LineProcessorWithMonitorThread;

public class LaunchBuildProcesses {
	public static final String DEFAULT_PROFILE = "DefaultProfile";
	static final Logger LOG = LoggerFactory.getLogger(LaunchBuildProcesses.class.getName());

	public static LineProcessorWithMonitorThread executionClientThread;
	static void executeClientScript(final String exec, final LineProcessorBuild lineProcessor) {
		 executionClientThread = new LineProcessorWithMonitorThread(exec, lineProcessor);
		executionClientThread.start();
	}

	public static void mvnExecute(String title, String action, String profile, String module, boolean resume) {
		String moduleOption = resume ? " -rf" : " -pl";
		String pathToMainPOMFile = PathFinder.getPathToMainPOMFile();
		String profileSwitch = profile.equals(DEFAULT_PROFILE) ? "" : " -P " + profile;
		boolean skipTests = MavenBuildBuddy.gui.optionsPanel.CHECKBOX_SKIP_TESTS.isSelected();
		String extraOptions = skipTests ? " -DskipTests=true" : "";
		String execString = "/usr/local/bin/mvn "+action+" -f "+pathToMainPOMFile+extraOptions+profileSwitch+moduleOption+" "+module;
		LOG.info("Exec: " + execString);
		LineProcessorBuild lineProcessor = new LineProcessorBuild();
		MavenBuildBuddy.gui.textPane.setText("");
		MavenBuildBuddy.gui.optionsPanel.BUTTON_ABORT.setEnabled(true);
		executeClientScript(execString, lineProcessor);
	}

	public static void findBugs(String profile, String module) {
		String title = "Find Bugs in profile " + profile;
		String pomPath = PathFinder.getPathToMainPOMFile();
		String moduleOption = "";
		if (module != null) {
			moduleOption = " -pl="+module;
			title += ", module " + module;
		}
		String execString = "/usr/local/bin/mvn findbugs:check -f "+pomPath+" -P="+profile+moduleOption;
		LineProcessorBuild lineProcessor = new LineProcessorBuild();
		executeClientScript(execString, lineProcessor);		
	}

	private static String getSuffix(String version) {
		String suffix = "";
		
		return suffix;
	}

	public static void runJConsole() {
		LOG.info(MBBMarkers.EXECUTE, "Run JConsole");
		String jdkHome = System.getenv("JAVA_HOME");
		String execString = jdkHome + "/bin/JConsole.exe";	
		LineProcessorBuild lineProcessor = new LineProcessorBuild();
		executeClientScript(execString, lineProcessor);
	}
}
