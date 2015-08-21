package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.gui.ControlButtonsForBuilds.ActionSettings;
import com.tifsoft.mavenbuildbuddy.pom.BuildPOMContentsLister;
import com.tifsoft.mavenbuildbuddy.pom.LaunchBuildProcesses;
import com.tifsoft.mavenbuildbuddy.pom.BuildXMLProcessor.BuildProfile;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;

public class ControlButtonsForBuilds {
	static final Logger LOG = LoggerFactory.getLogger(ControlButtonsForBuilds.class.getName());

	private JButton buttonStart;
	private JButton buttonBuildGUI;
	private JButton buttonBuildAnalysisAssistant;
	private JButton buttonBuildAnalysisGUI;
	private JButton buttonBuildAnalysisAll;
	private JButton buttonBuildAll;

	public void addPanel(final JPanel panel) {
	}
	
	public static JScrollPane setUp(JPanel buildArray) {		
		JScrollPane jsp = new JScrollPane(); 
		buildArray.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		ArrayList<BuildProfile> profileList = null;
		try {
			profileList = BuildPOMContentsLister.parsePOM();
		
			for (BuildProfile buildProfile : profileList) {
				con.gridx = 0;
				con.gridy++;
				con.anchor = GridBagConstraints.CENTER;
				con.fill = GridBagConstraints.HORIZONTAL;
				con.gridwidth = 8; //GridBagConstraints.REMAINDER;
				con.ipady = 10;
				final String profile = buildProfile.profile;
				JLabel labelProfile = new JLabel(profile,SwingConstants.CENTER);
				labelProfile.setOpaque(true);
				final Font font = new Font("Arial", Font.BOLD, 18);
				labelProfile.setFont(font);
				labelProfile.setBackground(new Color(0xFF000060));
				labelProfile.setForeground(Color.white);
				buildArray.add(labelProfile, con);
				
				for (final String module : buildProfile.moduleList) {
					con.gridy++;
					con.gridx = 0;
					con.anchor = GridBagConstraints.LINE_START;
					con.gridwidth = 1;
					con.ipady = 0;
					String processedModule = module;
					if (module.contains("/")) {
						int i = module.indexOf('/');
						processedModule = module.substring(i + 1);
					}
					JLabel labelModule = new JLabel(processedModule,SwingConstants.LEFT);
					buildArray.add(labelModule, con);
					con.gridx++;
					addBuildLifecycleButton(buildArray, con, profile, module, "Clean", "clean");
					addBuildLifecycleButton(buildArray, con, profile, module, "Validate", "validate");
					addBuildLifecycleButton(buildArray, con, profile, module, "Compile", "compile");
					addBuildLifecycleButton(buildArray, con, profile, module, "Test", "test");
					addBuildLifecycleButton(buildArray, con, profile, module, "Package", "package");
					addBuildLifecycleButton(buildArray, con, profile, module, "Integration", "integration-test");
					addBuildLifecycleButton(buildArray, con, profile, module, "Verify", "verify");
					addBuildLifecycleButton(buildArray, con, profile, module, "Install", "install");
					addBuildLifecycleButton(buildArray, con, profile, module, "Deploy", "deploy");
					
					//validate - validate the project is correct and all necessary information is available
					//compile - compile the source code of the project
					//test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
					//package - take the compiled code and package it in its distributable format, such as a JAR.
					//integration-test - process and deploy the package if necessary into an environment where integration tests can be run
					//verify - run any checks to verify the package is valid and meets quality criteria
					//install - install the package into the local repository, for use as a dependency in other projects locally
					//deploy - done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.
					
				}
			}
			
			jsp.setPreferredSize(new Dimension(800, 554));
	
			jsp.setViewportView(buildArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsp;
	}

	private static void addBuildLifecycleButton(JPanel buildArray, GridBagConstraints con, final String profile,
			final String module, String title, String command) {
		JButton buttonInstallOnly = new JButton(title);
		buttonInstallOnly.setMargin(new Insets(1, 1, 1, 1));
		buildArray.add(buttonInstallOnly, con);
		addMavenActions(buttonInstallOnly, profile, module, command);
		con.gridx++;
	}
	
	private static void addMavenActions(JButton button, String profile, String module, String actionForMaven) {
		button.setToolTipText("Maven build - profile <"+profile+"> - module <"+module+">");
		ActionSettings as = new ActionSettings(profile, module, "", actionForMaven);
		as.addMVNListener(button);
	}

	static class ActionSettings {
		private String profile;
		private String module;
		String cleanOption;
		String actionForMaven;
		//boolean resume;
		
		public ActionSettings(String profile, String module, String cleanOption, String actionForMaven) {
			this.profile = profile;
			this.module = module;
			this.cleanOption = cleanOption;
			this.actionForMaven = actionForMaven;
			//this.resume = resume;
		}
		
		void addMVNListener(JButton button) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String title = "Incremental build of profile <"+ActionSettings.this.profile+"> starting from module <"+ActionSettings.this.module+">";
					LOG.info(MBBMarkers.BUILD, title);
					boolean resume = MavenBuildBuddy.gui.CHECKBOX_RESUME.isSelected();
					boolean clean = MavenBuildBuddy.gui.CHECKBOX_CLEAN.isSelected();
					String actualAction = (clean ? "clean " : "") + ActionSettings.this.actionForMaven;
					LaunchBuildProcesses.mvnExecute(title, actualAction, ActionSettings.this.profile, ActionSettings.this.module, resume);
				}
			});										
		}
	}
}
