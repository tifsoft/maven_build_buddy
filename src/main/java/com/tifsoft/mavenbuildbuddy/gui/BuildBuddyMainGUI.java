package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;

public class BuildBuddyMainGUI extends JPanel {
	
	private static final long serialVersionUID = 1;

	public static final JCheckBox CHECKBOX_CLEAN = new JCheckBox("Clean first", true);
	public static final JCheckBox CHECKBOX_RESUME = new JCheckBox("Resume from", true);
	public static final JCheckBox CHECKBOX_SKIP_TESTS = new JCheckBox("Skip tests", false);

	public final ControlButtonsForBuilds controlButtonsForCompleteBuilds = new ControlButtonsForBuilds();
	public JTextPane textPane = new JTextPane();	
	private static final Color BACKGROUND_COLOR = new Color(0xFFF8F8F8);
	public final JFrame frame = new JFrame("Maven Build Buddy");
	public JPanel panelOptionsPanel = new JPanel(new BorderLayout());
	public JTabbedPane bigTabbedPane;
	//public static final String SHOW_CONSOLE_OUTPUT = "Show console output";
	static final Logger LOG = LoggerFactory.getLogger(BuildBuddyMainGUI.class);

	public void setUpGUI() {
		final JPanel mainTestArray = new JPanel();
		final JPanel buildArray = new JPanel();
		final JPanel panelForCustomerDatabase = new JPanel();
		final JPanel panelForMisc = new JPanel();
		final JPanel panelForUsers = new JPanel();
		final JPanel bottomPanel = new JPanel();
		final JPanel topPanel = new JPanel(new BorderLayout());
		
		final JPanel panelMainCenter = new JPanel();
		panelMainCenter.setBackground(BACKGROUND_COLOR);
		final JPanel panelMainSouth = new JPanel();	
		panelMainSouth.setBackground(BACKGROUND_COLOR);
		
		final JPanel panelBuilds = new JPanel();	
		final JPanel panelRegenerateEnvironment = new JPanel();	
		
		panelMainCenter.setLayout(new BoxLayout(panelMainCenter, BoxLayout.PAGE_AXIS));
		panelMainSouth.setLayout(new BoxLayout(panelMainSouth, BoxLayout.PAGE_AXIS));
		
		mainTestArray.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		
		bottomPanel.setLayout(new BorderLayout());
		final GridBagConstraints c = new GridBagConstraints();

		int row = 0;
		c.gridy = row++;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(2, 2, 2, 2);
		int column = 0;

		c.gridx = column++;
		final JLabel label1 = new JLabel("ID");
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		c.gridx = column++;
	
		JTabbedPane bottomMiscTabbedPane = new JTabbedPane();
		
		final JPanel panelOnTheRightHandSide = new JPanel();
		panelOnTheRightHandSide.setLayout(new BoxLayout(panelOnTheRightHandSide, BoxLayout.PAGE_AXIS));
		
		panelOnTheRightHandSide.add(panelForCustomerDatabase);		

		bottomMiscTabbedPane.setOpaque(true);

		panelMainSouth.add(bottomMiscTabbedPane);
		panelMainSouth.setBackground(BACKGROUND_COLOR);

		mainTestArray.add(panelMainCenter,BorderLayout.CENTER);
		mainTestArray.add(panelMainSouth,BorderLayout.SOUTH);

		this.bigTabbedPane = new JTabbedPane();
		
		panelOptionsPanel = new JPanel();
		panelOptionsPanel.add(CHECKBOX_CLEAN);
		panelOptionsPanel.add(CHECKBOX_RESUME);
		panelOptionsPanel.add(CHECKBOX_SKIP_TESTS);
		
		//JTextArea textArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret)textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textPane.setBackground(Color.black);
		textPane.setForeground(Color.green);
		textPane.setEditable(false);
		//textPane.set
		
		//JTextPane textPane = new JTextPane();
		JPanel noWrapPanel = new JPanel( new BorderLayout() );
		noWrapPanel.add( textPane );
		JScrollPane bottomJSP = new JScrollPane( noWrapPanel );
		
		//JScrollPane bottomJSP = new JScrollPane(textPane);		
		textPane.setVisible(true);
		JScrollPane jsp = ControlButtonsForBuilds.setUp(buildArray);
		
		topPanel.add(jsp, BorderLayout.CENTER);
		topPanel.add(panelOptionsPanel, BorderLayout.SOUTH);
		//JPanel botPanel = new JPanel();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomJSP);
		splitPane.setDividerLocation(250);
		this.bigTabbedPane.addTab("Build", null, splitPane, "Build using Maven");
		this.add(this.bigTabbedPane,BorderLayout.CENTER);
		this.bigTabbedPane.validate();
		this.bigTabbedPane.setVisible(true);
		this.validate();
		this.setVisible(true);
	}
	
	public static TitledBorder createTitledBorder(String title) {
		final Border loweredbevel = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		final Font titleFont = new Font("Arial", Font.BOLD, 14);
		final TitledBorder titlePanel = BorderFactory.createTitledBorder(loweredbevel, title);
		titlePanel.setTitleFont(titleFont);
		titlePanel.setTitleJustification(TitledBorder.CENTER);
		return titlePanel;
	}

	// Returns an ImageIcon, or null if the path was invalid. 
	protected static ImageIcon createImageIcon(final String path) {
		final URL imgURL = MavenBuildBuddy.class.getResource(path);	//import java.net.URL; 
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		}

		LOG.warn(MBBMarkers.MBB, "Couldn't find image in system: " + path);
		return null;
	}

	//Create the GUI and show it.  
	public void createGUI() {
		//Have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		this.frame.setLayout(new GridBagLayout());

		//Create and set up the content pane.
		setUpGUI();

		//content panes must be opaque
		this.setOpaque(true);
		this.setBackground(BACKGROUND_COLOR);

		this.frame.setContentPane(this);

		//Display the window.
		this.frame.pack();
		this.setSize(new Dimension(1200,600));
		this.frame.setVisible(true);

		setRedFrameIcon();
	}

	public void setRedFrameIcon() {
		FrameStatusIconRendering.setRedFrameIcon(this.frame);
	}

	public void setGreyFrameIcon() {
		FrameStatusIconRendering.setGreyFrameIcon(this.frame);
	}

	public void setOrangeFrameIcon() {
		FrameStatusIconRendering.setOrangeFrameIcon(this.frame);
	}

	public void setGreenFrameIcon() {
		FrameStatusIconRendering.setGreenFrameIcon(this.frame);
	}
}

class MyWindowListener extends WindowAdapter {
	static final Logger LOG = LoggerFactory.getLogger(MyWindowListener.class.getName());
}
