package com.tifsoft.mavenbuildbuddy.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BuildProfile {
	
	public static final String DEFAULT_PROFILE = "DefaultProfile";

	@Override
	public String toString() {
		return "BuildProfile [moduleList=" + moduleList + ", profile=" + name + "]";
	}
	public List<BuildModule> moduleList = new ArrayList<BuildModule>();
	
	public String name;
	public String getName() {
		return name;
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
		for (BuildModule buildModule : moduleList) {
			if (module.equals(buildModule.getName())) {
				return buildModule;
			}
		}
		return null;
	}
	
	public void scrubModule(BuildModule buildModule, boolean resume) {
		boolean scrubbing = false;
		for (BuildModule thisBuildModule : moduleList) {
			if (buildModule == thisBuildModule) {
				scrubbing = true;
			}
			if (scrubbing) {
				thisBuildModule.setBackground(Color.red);
				thisBuildModule.setBuildStage(BuildStage.BUILD_STAGE_CLEAN);
			}
			if (!resume) {
				scrubbing = false;
			}
		}
	}
}
