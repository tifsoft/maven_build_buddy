package com.tifsoft.mavenbuildbuddy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildProfile {
	//public String name;
	public Map<String, BuildModule> moduleList = new HashMap<String, BuildModule>();
	
	public String profile;
	//public ArrayList<String> moduleList = new ArrayList<String>();
	public BuildProfile(String profile) {
		super();
		this.profile = profile;
	}

}
