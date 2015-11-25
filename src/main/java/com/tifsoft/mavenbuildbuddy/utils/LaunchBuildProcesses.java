package com.tifsoft.mavenbuildbuddy.utils;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.gui.OptionsPanel;
import com.tifsoft.mavenbuildbuddy.gui.PreferencesPanel;
import com.tifsoft.mavenbuildbuddy.line.LineProcessorBuild;
import com.tifsoft.mavenbuildbuddy.model.BuildModule;
import com.tifsoft.mavenbuildbuddy.model.BuildPOM;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;
import com.tifsoft.mavenbuildbuddy.model.BuildStage;
import com.tifsoft.processmanager.LineProcessorWithMonitorThread;

public class LaunchBuildProcesses {
	public static final String DEFAULT_PROFILE = "DefaultProfile";
	static final Logger LOG = LoggerFactory.getLogger(LaunchBuildProcesses.class.getName());

	public static LineProcessorWithMonitorThread executionClientThread;
	static void executeClientScript(final String exec, final LineProcessorBuild lineProcessor) {
		 executionClientThread = new LineProcessorWithMonitorThread(exec, lineProcessor);
		executionClientThread.start();
	}

	public static void mvnExecute(String title, BuildStage buildStage, String profile, String module, boolean clean, boolean resume) {
		String moduleOption = resume ? " -rf" : " -pl";
		String pathToMainPOMFile = PathFinder.getPathToMainPOMFile();
		String profileSwitch = profile.equals(DEFAULT_PROFILE) ? "" : " -P " + profile;
		boolean skipTests = OptionsPanel.CHECKBOX_SKIP_TESTS.isSelected();
		String extraOptions = skipTests ? " -DskipTests=true" : "";
		extraOptions += OptionsPanel.CHECKBOX_QUIET.isSelected() ? " -q" : "";
		extraOptions += OptionsPanel.CHECKBOX_VERBOSE.isSelected() ? " -X" : "";
		extraOptions += OptionsPanel.CHECKBOX_OFFLINE.isSelected() ? " -o" : "";	
		extraOptions += OptionsPanel.CHECKBOX_FORCE.isSelected() ? " -U" : "";	
		String actualAction = (clean ? "clean " : "") + buildStage.getAction();
		String path = PreferencesPanel.pathToMaven.getText();
		String execString = path + " " + actualAction + " -f " + pathToMainPOMFile + extraOptions + profileSwitch + moduleOption+" "+module;
		LOG.info("Exec: " + execString);
		BuildPOM pom = MavenBuildBuddy.pomMap.get(BuildPOM.DEFAULT_POM);
		//LOG.info("pom: " + pom);
		//LOG.info("looking for: " + profile);
		BuildProfile buildProfile = pom.getProfileByName(profile);
		BuildModule buildModule = buildProfile.getModuleFromName(module);
		buildProfile.scrubModule(buildModule, resume);
		//Set<String>stringSet = pom.profileList.keySet();
		//for (String string : stringSet) {
			//LOG.info("String: " + string);			
		//}
		//LOG.info("buildProfile: " + buildProfile);
		LineProcessorBuild lineProcessor = new LineProcessorBuild(buildProfile, buildStage);
		MavenBuildBuddy.gui.textPane.setText("");
		MavenBuildBuddy.gui.preferencesPanel.updateFont();
		OptionsPanel.disableLaunchActions();
		executeClientScript(execString, lineProcessor);
	}

	/*public static void findBugs(String profile, String module) {
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
	}*/

	private static String getSuffix(String version) {
		String suffix = "";
		
		return suffix;
	}

	/*public static void runJConsole() {
		LOG.info(MBBMarkers.EXECUTE, "Run JConsole");
		String jdkHome = System.getenv("JAVA_HOME");
		String execString = jdkHome + "/bin/JConsole.exe";	
		LineProcessorBuild lineProcessor = new LineProcessorBuild();
		executeClientScript(execString, lineProcessor);
	}*/
}
