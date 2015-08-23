package com.tifsoft.mavenbuildbuddy.model;

import javax.swing.JLabel;

public class BuildModule {
	public String name;
	public JLabel label;
	public BuildModule(String name, JLabel label) {
		super();
		this.name = name;
		this.label = label;
	}
}
