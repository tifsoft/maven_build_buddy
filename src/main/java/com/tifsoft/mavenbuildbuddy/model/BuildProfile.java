package com.tifsoft.mavenbuildbuddy.model;

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
}
