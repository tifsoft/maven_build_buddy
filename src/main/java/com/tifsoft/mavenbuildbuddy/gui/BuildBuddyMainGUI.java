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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SizeRequirements;
import javax.swing.SizeRequirements;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Element;
import javax.swing.text.Element;
import javax.swing.text.GlyphView;
import javax.swing.text.GlyphView;
import javax.swing.text.ParagraphView;
import javax.swing.text.ParagraphView;
import javax.swing.text.View;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.InlineView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;

public class BuildBuddyMainGUI extends JPanel {
	
	private static final long serialVersionUID = 1;

	public final ControlButtonsForBuilds controlButtonsForCompleteBuilds = new ControlButtonsForBuilds();
	public CustomTextPane textPane = new CustomTextPane();	
	private static final Color BACKGROUND_COLOR = new Color(0xFFF8F8F8);
	public final JFrame frame = new JFrame("Maven Build Buddy");
	public OptionsPanel optionsPanel = new OptionsPanel();
	public JTabbedPane tabbedPaneForPOMs;
	public JSplitPane splitPane;

	final JPanel topPanel = new JPanel(new BorderLayout());
	final JPanel buildArray = new JPanel();
	
	JPanel noWrapPanel = new JPanel( new BorderLayout() );
	JScrollPane mainScrollPane = new JScrollPane( noWrapPanel );

	public PreferencesPanel preferencesPanel = new PreferencesPanel();

	//public static final String SHOW_CONSOLE_OUTPUT = "Show console output";
	static final Logger LOG = LoggerFactory.getLogger(BuildBuddyMainGUI.class);

	public void setUpGUI() {
		final JPanel mainTestArray = new JPanel();
		final JPanel panelForCustomerDatabase = new JPanel();
		final JPanel bottomPanel = new JPanel();
		
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);
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

		this.tabbedPaneForPOMs = new JTabbedPane();
		
		DefaultCaret caret = (DefaultCaret)textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textPane.setBackground(Color.black);
		textPane.setForeground(Color.green);
		textPane.setEditable(false);
		
		noWrapPanel.add( textPane );
		
		textPane.setVisible(true);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel("temp"), mainScrollPane);
		splitPane.setBackground(Color.darkGray);

		String path = PreferencesPanel.POM_PATH_TEXTFIELD.getText();
		makeNewPom(path);
		splitPane.setDividerLocation(250);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		this.tabbedPaneForPOMs.addTab("POM", null, splitPane, "Build using Maven");
		this.tabbedPaneForPOMs.addTab("Preferences", null, this.preferencesPanel , "Preferences");
		this.add(this.tabbedPaneForPOMs,BorderLayout.CENTER);
		this.tabbedPaneForPOMs.validate();
		this.tabbedPaneForPOMs.setVisible(true);
		this.validate();
		this.setVisible(true);
	}

	public void makeNewPom(String path) {
		buildArray.removeAll(); // ToDo - rethink this...
		topPanel.removeAll(); // ToDo - rethink this...
		JScrollPane jsp = ControlButtonsForBuilds.setUp(buildArray, path);
		this.topPanel.add(jsp, BorderLayout.CENTER);
		this.topPanel.add(this.optionsPanel, BorderLayout.SOUTH);
		this.splitPane.setTopComponent(this.topPanel);
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
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
