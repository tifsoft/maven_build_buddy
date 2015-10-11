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
	public static final JCheckBox CHECKBOX_QUIET = new JCheckBox("Quiet", false);
	public static final JCheckBox CHECKBOX_VERBOSE = new JCheckBox("Verbose", false);
	public static final JCheckBox CHECKBOX_OFFLINE = new JCheckBox("Offline", false);
	public static final JButton BUTTON_ABORT = new JButton("Abort");
	public static final JButton BUTTON_CLEAR = new JButton("Clear");
	public static boolean building = false;

	public OptionsPanel() {
		super();
		add(OptionsPanel.CHECKBOX_CLEAN);
		add(OptionsPanel.CHECKBOX_RESUME);
		add(OptionsPanel.CHECKBOX_SKIP_TESTS);
		add(OptionsPanel.CHECKBOX_VERBOSE);
		add(OptionsPanel.CHECKBOX_QUIET);
		add(OptionsPanel.CHECKBOX_OFFLINE);
		add(OptionsPanel.BUTTON_ABORT);
		add(OptionsPanel.BUTTON_CLEAR);
		add(OptionsPanel.CHECKBOX_WRAP);
//		LOG.info("Create button to kill thread");
		
    	final ActionListener wwal = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final boolean wrap = !CHECKBOX_WRAP.isSelected();
				LOG.info("Wrap: " + wrap);
				final BuildBuddyMainGUI gui = MavenBuildBuddy.gui;
		        if (wrap == true) {
		            gui.mainScrollPane.setViewportView(gui.noWrapPanel);
		            gui.noWrapPanel.add(gui.textPane);
		        } else {
		            gui.mainScrollPane.setViewportView(gui.textPane);
		        }
			}
		};
       CHECKBOX_WRAP.addActionListener(wwal);
		
		final ActionListener alAbort = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				LOG.info("Kill thread");
				LaunchBuildProcesses.executionClientThread.close();
				enableLaunchActions();
			}
		};
		BUTTON_ABORT.addActionListener(alAbort);
		BUTTON_ABORT.setEnabled(false);

		final ActionListener alClear = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				LOG.info("Clear");
				MavenBuildBuddy.gui.textPane.setText("");
			}
		};
		BUTTON_CLEAR.addActionListener(alClear);
	}
	
	public static void disableLaunchActions() {
		BUTTON_ABORT.setEnabled(true);
		building = true;
		SystemTrayIcon.setBusy();
	}
	
	public static void enableLaunchActions() {
		BUTTON_ABORT.setEnabled(false);
		building = false;
		SystemTrayIcon.setOK();
	}
}
