package com.tifsoft.mavenbuildbuddy.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildPOM {
	public static final String DEFAULT_POM = "DefaultPOM";

	public String name;
	public List<BuildProfile> profileList = new ArrayList<BuildProfile>();

	public BuildPOM(String name) {
		super();
		this.name = name;
	}
	
	public BuildProfile getProfileByName(String name) {
		for (Iterator iterator = profileList.iterator(); iterator.hasNext();) {
			BuildProfile buildProfile = (BuildProfile) iterator.next();
			if (buildProfile.name.equals(name)) {
				return buildProfile;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "BuildPOM [name=" + name + ", profileList=" + profileList + "]";
	}
}
