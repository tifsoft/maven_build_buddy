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
		this.labelModule.setOpaque(true);
		
	}

	public void setColor(Color color) {
		this.labelModule.setBackground(color);
		//this.labelModule.setForeground(Color.red);
		this.labelModule.setVisible(true);
	}

	@Override
	public String toString() {
		return "BuildModule [name=" + name + ", labelModule=" + labelModule + "]";
	}
}
