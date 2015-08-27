package com.tifsoft.mavenbuildbuddy.model;

import java.util.ArrayList;
import java.util.List;

public enum BuildStage {
	BUILD_STAGE_CLEAN("Clean", "clean", "Cleaned"),
	BUILD_STAGE_VALIDATE("Validate", "validate","Validated"),
	BUILD_STAGE_COMPILE("Compile", "compile", "Compiled"),
	BUILD_STAGE_TEST("Test", "test", "Tested"),
	BUILD_STAGE_PACKAGE("Package", "package", "Packaged"),
	BUILD_STAGE_INTEGRATION_TEST("Integration", "integration-test", "Integrated"),
	BUILD_STAGE_VERIFY("Verify", "verify", "Verified"),
	BUILD_STAGE_INSTALL("Install", "install", "Installed"),
	BUILD_STAGE_DEPLOY("Deploy", "deploy","Deployed");			

	private String label;
	private String action;
	private String status;
	
	public static List<BuildStage> set;
		
	BuildStage(final String label, final String action, final String status) {
		this.label = label;
		this.action = action;
		this.status = status;
		setup();
	}

	private void setup() {
		if (set == null) {
			set = new ArrayList<BuildStage>();
		}
		set.add(this);
	}

	public String getLabel() {
		return this.label;
	}

	public String getAction() {
		return this.action;
	}

	public String getStatus() {
		return this.status;
	}
}
