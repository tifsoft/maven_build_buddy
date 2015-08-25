package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.pom.LaunchBuildProcesses;

public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(OptionsPanel.class);
	public static final JCheckBox CHECKBOX_CLEAN = new JCheckBox("Clean first", true);
	public static final JCheckBox CHECKBOX_RESUME = new JCheckBox("Resume from", true);
	public static final JCheckBox CHECKBOX_SKIP_TESTS = new JCheckBox("Skip tests", false);
	public static final JButton BUTTON_ABORT = new JButton("Abort");
	public static final JButton BUTTON_CLEAR = new JButton("Clear");

	public OptionsPanel() {
		super();
		add(this.CHECKBOX_CLEAN);
		add(this.CHECKBOX_RESUME);
		add(this.CHECKBOX_SKIP_TESTS);
		add(this.BUTTON_ABORT);
		add(this.BUTTON_CLEAR);
//		LOG.info("Create button to kill thread");
		
		ActionListener alAbort = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOG.info("Kill thread");
				LaunchBuildProcesses.executionClientThread.close();
				BUTTON_ABORT.setEnabled(false);
			}
		};
		BUTTON_ABORT.addActionListener(alAbort);
		BUTTON_ABORT.setEnabled(false);

		ActionListener alClear = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOG.info("Clear");
				MavenBuildBuddy.gui.textPane.setText("");
			}
		};
		BUTTON_CLEAR.addActionListener(alClear);
	}
}
