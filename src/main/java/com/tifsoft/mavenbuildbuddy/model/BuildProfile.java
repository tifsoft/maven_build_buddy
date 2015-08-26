package com.tifsoft.mavenbuildbuddy.model;

import java.util.ArrayList;
import java.util.List;

public class BuildProfile {
	//public String name;
	public List<BuildModule> moduleList = new ArrayList<BuildModule>();
	
	public String profile;
	//public ArrayList<String> moduleList = new ArrayList<String>();
	public BuildProfile(String profile) {
		super();
		this.profile = profile;
	}

}
