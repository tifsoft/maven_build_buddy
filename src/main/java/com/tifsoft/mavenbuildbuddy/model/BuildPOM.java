package com.tifsoft.mavenbuildbuddy.model;

import java.util.HashMap;
import java.util.Map;

public class BuildPOM {
	public String name;
	public Map<String, BuildProfile> profileList = new HashMap<String, BuildProfile>();
}
