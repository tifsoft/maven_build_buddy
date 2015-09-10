package com.tifsoft.mavenbuildbuddy.model;

import java.awt.Color;

import javax.swing.JLabel;

public class BuildModule {
	public String name;
	
	/* Maybe should not be here */
	private JLabel labelModule;
	/* Maybe should not be here */
	private JLabel labelWarnings;	
	/* Maybe should not be here */
	private JLabel labelTesting;	
	/* Maybe should not be here */
	int warningCount;
	/* Maybe should not be here */
	int errorCount;

	private TestingStage testingStage;

	private JLabel labelSkip;
	private JLabel labelFail;
	private JLabel labelErrors;
	private JLabel labelTotal;
	
	public BuildModule(final String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setComponent(final JLabel labelModule) {
		this.labelModule = labelModule;
	}

	public void setComponentWarnings(final JLabel labelWarnings) {
		this.labelWarnings = labelWarnings;
	}
	
	public void setComponentTesting(final JLabel labelTesting) {
		this.labelTesting = labelTesting;
	}
	
	public void setBackground(final Color color) {
		this.labelModule.setBackground(color);
	}
	
	public void setForeground(final Color color) {
		this.labelModule.setForeground(color);
	}

	@Override
	public String toString() {
		return "BuildModule [name=" + this.name + ", labelModule=" + this.labelModule + "]";
	}

	public void setBuildStage(final BuildStage buildStage) {
		setBuildStageText(buildStage.getStatus());
	}

	public void setBuildStageText(final String text) {
		final String ic = text.substring(0, 1);
		final String rest = text.substring(1);
		this.labelModule.setText(" " + ic.toUpperCase() + rest + " ");
	}
	
	public int getWarningCount() {
		return this.warningCount;
	}

	public void setWarningCount(final int warningCount) {
		this.warningCount = warningCount;
		showWarningCounts();
	}

	public int getErrorCount() {
		return this.errorCount;
	}

	public void setErrorCount(final int errorCount) {
		this.errorCount = errorCount;
		showWarningCounts();
	}

	public void showWarningCounts() {
		final Color color;
		final Color bgColor;
		final int value;
		if (this.errorCount > 0) {
			value = this.errorCount;
			color = Color.red;
			bgColor = Color.black;
			setBackground(Color.red);
		} else if (this.warningCount > 0) {
			value = this.warningCount;
			color = Color.orange;
			bgColor = Color.black;
		} else {
			value = 0;
			color = Color.white;
			bgColor = Color.black;
		}
		String rep = value == 0 ? "-" : "" + value;
		this.labelWarnings.setText(" " + rep + " ");
		this.labelWarnings.setForeground(color);
		this.labelWarnings.setBackground(bgColor);
	}

	public void setTestingStage(TestingStage testingStage) {
		this.testingStage = testingStage;
		this.labelTesting.setText(" " + testingStage.getLabel() + " ");
		this.labelTesting.setBackground(testingStage.getColor());
	}

	public TestingStage getTestingStage() {
		return this.testingStage;
	}

	public void setLabelSkip(JLabel labelSkip) {
		this.labelSkip = labelSkip;
	}

	public void setLabelFail(JLabel labelFail) {
		this.labelFail = labelFail;
	}

	public void setLabelTotal(JLabel labelTotal) {
		this.labelTotal = labelTotal;
	}
	
	public void setLabelErrors(JLabel labelErrors) {
		this.labelErrors = labelErrors;
	}

	public JLabel getLabelSkip() {
		return labelSkip;
	}

	public JLabel getLabelFail() {
		return labelFail;
	}

	public JLabel getLabelTotal() {
		return labelTotal;
	}
	
	public JLabel getLabelErrors() {
		return labelErrors;
	}
}
