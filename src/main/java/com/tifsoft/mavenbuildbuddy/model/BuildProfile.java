package com.tifsoft.mavenbuildbuddy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildProfile {
	//public String name;
	//public Map<String, Module> profileList = new HashMap<String, Module>();
	
	public String profile;
	public ArrayList<String> moduleList = new ArrayList<String>();
	public BuildProfile(String profile) {
		super();
		this.profile = profile;
	}

}
