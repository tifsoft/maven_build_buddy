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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.model.BuildModule;
import com.tifsoft.mavenbuildbuddy.model.BuildPOM;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;
import com.tifsoft.mavenbuildbuddy.model.BuildStage;
import com.tifsoft.mavenbuildbuddy.model.TestingStage;
import com.tifsoft.mavenbuildbuddy.pom.BuildPOMContentsLister;
import com.tifsoft.mavenbuildbuddy.utils.LaunchBuildProcesses;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;

public class ControlButtonsForBuilds {
	static final Logger LOG = LoggerFactory.getLogger(ControlButtonsForBuilds.class.getName());
	
	public static JScrollPane setUp(JPanel buildArray, String path) {		
		JScrollPane jsp = new JScrollPane(); 
		buildArray.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		BuildPOM buildPOM = null;
		try {
			buildPOM = BuildPOMContentsLister.parsePOM(path);
			MavenBuildBuddy.pomMap.put(BuildPOM.DEFAULT_POM, buildPOM);
			for (BuildProfile buildProfile : buildPOM.profileList.values()) {
				con.gridx = 0;
				con.gridy++;
				con.anchor = GridBagConstraints.CENTER;
				con.fill = GridBagConstraints.HORIZONTAL;
				con.gridwidth = 8; //GridBagConstraints.REMAINDER;
				con.ipady = 10;
				final String profile = buildProfile.getName();
				JLabel labelProfile = new JLabel(profile,SwingConstants.CENTER);
				labelProfile.setOpaque(true);
				final Font font = new Font("Arial", Font.BOLD, 18);
				labelProfile.setFont(font);
				labelProfile.setBackground(new Color(0xFF000060));
				labelProfile.setForeground(Color.white);
				buildArray.add(labelProfile, con);
				
				for (final BuildModule module : buildProfile.moduleList) {
					con.gridy++;
					con.gridx = 0;
					con.anchor = GridBagConstraints.LINE_START;
					con.gridwidth = 1;
					con.ipady = 0;
					String processedModule = module.getName();
					if (module.getName().contains("/")) {
						int i = module.getName().indexOf('/');
						processedModule = module.getName().substring(i + 1);
					}
					JLabel labelModule = new JLabel(processedModule + " ",SwingConstants.LEFT);
					buildArray.add(labelModule, con);
					con.gridx++;
					
					JLabel labelStatus = new JLabel(" Unknown ",SwingConstants.CENTER);
					labelStatus.setOpaque(true);
					labelStatus.setBackground(Color.red);
					buildArray.add(labelStatus, con);
					//BuildPOM pom = MavenBuildBuddy.pomMap.get(BuildPOM.DEFAULT_POM);
					//BuildProfile buildProfile = pom.profileList.get(profile);
					module.setComponent(labelStatus);
					con.gridx++;
					
					JLabel labelWarnings = new JLabel(" 0 ",SwingConstants.CENTER);
					labelWarnings.setOpaque(true);
					//labelStatus.setBackground(Color.red);
					buildArray.add(labelWarnings, con);
					//BuildPOM pom = MavenBuildBuddy.pomMap.get(BuildPOM.DEFAULT_POM);
					//BuildProfile buildProfile = pom.profileList.get(profile);
					module.setComponentWarnings(labelWarnings);
					module.showWarningCounts();
					con.gridx++;

					JLabel labelTesting = new JLabel(" ? ",SwingConstants.CENTER);
					labelTesting.setOpaque(true);
					//labelStatus.setBackground(Color.red);
					buildArray.add(labelTesting, con);
					//BuildPOM pom = MavenBuildBuddy.pomMap.get(BuildPOM.DEFAULT_POM);
					//BuildProfile buildProfile = pom.profileList.get(profile);
					module.setComponentTesting(labelTesting);
					module.setTestingStage(TestingStage.TESTING_STAGE_UNTESTED);
					//module.showWarningCounts();
					con.gridx++;

					JLabel labelTotal = new JLabel(" -",SwingConstants.CENTER);
					module.setLabelTotal(labelTotal);
					labelTotal.setOpaque(true);
					buildArray.add(labelTotal, con);
					con.gridx++;

					JLabel labelFail = new JLabel(" -",SwingConstants.CENTER);
					module.setLabelFail(labelFail);
					labelFail.setOpaque(true);
					buildArray.add(labelFail, con);
					con.gridx++;

					JLabel labelErrors = new JLabel(" -",SwingConstants.CENTER);
					module.setLabelErrors(labelErrors);
					labelErrors.setOpaque(true);
					buildArray.add(labelErrors, con);
					con.gridx++;

					JLabel labelSkip = new JLabel(" -",SwingConstants.CENTER);
					module.setLabelSkip(labelSkip);
					labelSkip.setOpaque(true);
					buildArray.add(labelSkip, con);
					con.gridx++;

					for (BuildStage bs : BuildStage.set) {
						addBuildLifecycleButton(buildArray, con, profile, processedModule, bs);						
					}
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
			final String module, BuildStage buildStage) {
		JButton buttonInstallOnly = new JButton(buildStage.getLabel());
		buttonInstallOnly.setMargin(new Insets(1, 1, 1, 1));
		buildArray.add(buttonInstallOnly, con);
		addMavenActions(buttonInstallOnly, profile, module, buildStage);
		con.gridx++;
	}
	
	private static void addMavenActions(JButton button, String profile, String module, BuildStage buildStage) {
		button.setToolTipText("Maven build - profile <"+profile+"> - module <"+module+">");
		ActionSettings as = new ActionSettings(profile, module, "", buildStage);
		as.addMVNListener(button);
	}

	static class ActionSettings {
		private String profile;
		private String module;
		String cleanOption;
		BuildStage buildStage;
		//boolean resume;
		
		public ActionSettings(String profile, String module, String cleanOption, BuildStage buildStage) {
			this.profile = profile;
			this.module = module;
			this.cleanOption = cleanOption;
			this.buildStage = buildStage;
			//this.resume = resume;
		}
		
		void addMVNListener(JButton button) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!OptionsPanel.building) {
						String title = "Incremental build of profile <"+ActionSettings.this.profile+"> starting from module <"+ActionSettings.this.module+">";
						LOG.info(MBBMarkers.BUILD, title);
						boolean resume = MavenBuildBuddy.gui.optionsPanel.CHECKBOX_RESUME.isSelected();
						boolean clean = MavenBuildBuddy.gui.optionsPanel.CHECKBOX_CLEAN.isSelected();
						//String actualAction = (clean ? "clean " : "") + ActionSettings.this.actionForMaven;
						BuildStage buildStage = ActionSettings.this.buildStage;
						LaunchBuildProcesses.mvnExecute(title, buildStage, ActionSettings.this.profile, ActionSettings.this.module, clean, resume);
					} else {
						JOptionPane.showMessageDialog(null, "Build in progress - and concurrent builds are prohibited");
					}
				}
			});										
		}
	}
}
