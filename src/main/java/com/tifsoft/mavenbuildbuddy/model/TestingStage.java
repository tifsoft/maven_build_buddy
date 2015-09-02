package com.tifsoft.mavenbuildbuddy.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum TestingStage {
	TESTING_STAGE_UNTESTED("Untested", Color.red),
	TESTING_STAGE_TESTING("Testing", Color.yellow),
	TESTING_STAGE_TESTED("Tested", Color.green);

	private String label;
	private Color color;
	
	public static List<TestingStage> set;
		
	TestingStage(final String label, Color color) {
		this.label = label;
		this.color = color;
		setup();
	}

	private void setup() {
		if (set == null) {
			set = new ArrayList<TestingStage>();
		}
		set.add(this);
	}

	public String getLabel() {
		return this.label;
	}

	public Color getColor() {
		return this.color;
	}
}
