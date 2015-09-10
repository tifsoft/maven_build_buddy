package com.tifsoft.mavenbuildbuddy.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum TestingStage {
	TESTING_STAGE_UNTESTED("Not run", Color.red),
	TESTING_STAGE_TESTING_STARTED("Running", Color.yellow),
	TESTING_STAGE_TESTING_FINISHED("Finished", Color.blue),
	TESTING_STAGE_TESTED("Success", Color.green),
	TESTING_STAGE_FAILURE("Failure", Color.green),
	TESTING_STAGE_SKIPPED("Skipped", Color.gray),
	TESTING_STAGE_NO_TESTS_FOUND("Not found", Color.magenta);

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
