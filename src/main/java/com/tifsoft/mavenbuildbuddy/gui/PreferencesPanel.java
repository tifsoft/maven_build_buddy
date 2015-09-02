package com.tifsoft.mavenbuildbuddy.gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreferencesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(PreferencesPanel.class);
	//AL = new ActionListener
	//public static final JOpCheckBox CHECKBOX_CLEAN = new JCheckBox("Clean first", true);
	
	
	public static final String[] fontSizeStrings = { "10", "12", "13", "14", "16" , "17" , "18" , "19" };

	public static final JComboBox fontSizeList = new JComboBox(fontSizeStrings);
	
	public PreferencesPanel() {
		fontSizeList.setSelectedIndex(4);
		//fontSizeList.addActionListener(this);
	}
}
