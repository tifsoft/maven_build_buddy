package com.tifsoft.mavenbuildbuddy.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BuildProfile {
	
	public static final String DEFAULT_PROFILE = "DefaultProfile";
	public String name;

	@Override
	public String toString() {
		return "BuildProfile [moduleList=" + this.moduleList + ", profile=" + this.name + "]";
	}
	public List<BuildModule> moduleList = new ArrayList<BuildModule>();
	
	public String getName() {
		return this.name;
	}
	//public void setName(String name) {
		//this.name = name;
	//}
	//public ArrayList<String> moduleList = new ArrayList<String>();
	public BuildProfile(String name) {
		super();
		this.name = name;
	}
	
	public BuildModule getModuleFromName(String module) {
		for (BuildModule buildModule : this.moduleList) {
			if (module.equals(buildModule.getName())) {
				return buildModule;
			}
		}
		return null;
	}
	
	public void scrubModule(BuildModule buildModule, boolean resume) {
		boolean scrubbing = false;
		for (BuildModule thisBuildModule : this.moduleList) {
			thisBuildModule.setWarningCount(0);
			thisBuildModule.setErrorCount(0);
			if (buildModule == thisBuildModule) {
				scrubbing = true;
			}
			if (scrubbing) {
				thisBuildModule.setBackground(Color.red);
				thisBuildModule.setBuildStage(BuildStage.BUILD_STAGE_CLEAN);
				thisBuildModule.setTestingStage(TestingStage.TESTING_STAGE_UNTESTED);
			}
			if (!resume) {
				scrubbing = false;
			}
		}
	}
}
