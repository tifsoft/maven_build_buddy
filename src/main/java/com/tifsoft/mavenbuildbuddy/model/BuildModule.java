package com.tifsoft.mavenbuildbuddy.model;

import java.awt.Color;

import javax.swing.JLabel;

public class BuildModule {
	public String name;
	public JLabel label;
	private JLabel labelModule;
	
	public BuildModule(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setComponent(JLabel labelModule) {
		this.labelModule = labelModule;
	}

	public void setBackground(Color color) {
		this.labelModule.setBackground(color);
	}
	
	public void setForeground(Color color) {
		this.labelModule.setForeground(color);
	}

	@Override
	public String toString() {
		return "BuildModule [name=" + name + ", labelModule=" + labelModule + "]";
	}

	public void setBuildStage(BuildStage buildStage) {
		this.labelModule.setText(" " + buildStage.getStatus() + " ");
	}
}
