package com.tifsoft.mavenbuildbuddy.model;

import java.util.HashMap;
import java.util.Map;

public class BuildPOM {
	public static final String DEFAULT_POM = "DefaultPOM";

	public String name;
	public Map<String, BuildProfile> profileList = new HashMap<String, BuildProfile>();

	public BuildPOM(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "BuildPOM [name=" + name + ", profileList=" + profileList + "]";
	}
}
