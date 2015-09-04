package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.utils.LaunchBuildProcesses;

public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(OptionsPanel.class);
	public static final JCheckBox CHECKBOX_CLEAN = new JCheckBox("Clean first", true);
	public static final JCheckBox CHECKBOX_RESUME = new JCheckBox("Resume from", true);
	public static final JCheckBox CHECKBOX_SKIP_TESTS = new JCheckBox("Skip tests", false);
	public static final JCheckBox CHECKBOX_WRAP = new JCheckBox("Wrap", false);
	public static final JButton BUTTON_ABORT = new JButton("Abort");
	public static final JButton BUTTON_CLEAR = new JButton("Clear");

	public OptionsPanel() {
		super();
		add(this.CHECKBOX_CLEAN);
		add(this.CHECKBOX_RESUME);
		add(this.CHECKBOX_SKIP_TESTS);
		add(this.BUTTON_ABORT);
		add(this.BUTTON_CLEAR);
		add(this.CHECKBOX_WRAP);
//		LOG.info("Create button to kill thread");
		
    	ActionListener wwal = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean wrap = !CHECKBOX_WRAP.isSelected();
				LOG.info("Wrap: " + wrap);
				BuildBuddyMainGUI gui = MavenBuildBuddy.gui;
		        if (wrap == true) {
		            gui.mainScrollPane.setViewportView(gui.noWrapPanel);
		            gui.noWrapPanel.add(gui.textPane);
		        } else {
		            gui.mainScrollPane.setViewportView(gui.textPane);
		        }
			}
		};
       CHECKBOX_WRAP.addActionListener(wwal);
		
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
