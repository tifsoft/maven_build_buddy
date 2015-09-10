package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;

public class PreferencesPanel extends JPanel {
	public static final JTextField POM_PATH_TEXTFIELD = new JTextField("../PS/knowledgeHub/" + "pom.xml",50);
	private static final long serialVersionUID = 1L;
	static final Logger LOG = LoggerFactory.getLogger(PreferencesPanel.class);
	
	public static final String[] fontSizeStrings = { "7", "8", "9", "10", "11", "12", "13", "14", "16", "17", "18", "19", "20", "21", "22", "23", "24" };
	public static final JComboBox fontSizeList = new JComboBox(fontSizeStrings);
	public static final String[] fontNameStrings = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	//public static final String[] fontNameStrings = {Font.MONOSPACED, Font.SERIF, Font.SANS_SERIF};
	public static final JComboBox fontNameOption = new JComboBox(fontNameStrings);
	public static final String[] fontWeightStrings = { "PLAIN", "BOLD", "ITALIC" };
	public static final JComboBox fontWeightOption = new JComboBox(fontWeightStrings);
	public static final JTextField pathToMaven = new JTextField("/usr/local/bin/mvn", 50);
	
	JPanel panelFont = new JPanel();
	JPanel panelCommand = new JPanel();
	JPanel panelPom = new JPanel();
		//	"/usr/local/bin/mvn"
	
	public PreferencesPanel() {
		//fontSizeList.setSelectedIndex(4);
		
		panelFont.add(new JLabel("Font: "));
		panelFont.add(fontNameOption);
		panelFont.add(fontSizeList);
		panelFont.add(fontWeightOption);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(panelFont);
		panelCommand.add(new JLabel("Maven command: "));
		panelCommand.add(pathToMaven);
		this.add(panelCommand);
		panelPom.add(new JLabel("POM: "));
		panelPom.add(POM_PATH_TEXTFIELD);
		JButton go = new JButton("Load new POM");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MavenBuildBuddy.gui.makeNewPom(POM_PATH_TEXTFIELD.getText());
			}
		});
		panelPom.add(go);
		this.add(panelPom);
		
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		};
		fontNameOption.setSelectedItem("Monospaced");
		fontSizeList.setSelectedItem("16");
		
		fontNameOption.addActionListener(al);
		fontSizeList.addActionListener(al);
		fontWeightOption.addActionListener(al);
		//updateFont();
	}

	public void updateFont() {
		String name = fontNameOption.getSelectedItem().toString();
		int size = Integer.parseInt(fontSizeList.getSelectedItem().toString());
		String fws = fontWeightOption.getSelectedItem().toString();
		int fw = fws.equals("BOLD") ? Font.BOLD : fws.equals("ITALIC") ? Font.ITALIC : Font.PLAIN;
		Font font = new Font(name,  fw, size);
		setJTextPaneFont(MavenBuildBuddy.gui.textPane, font);
	}
	
    /**
     * Utility method for setting the font and color of a JTextPane. The
     * result is roughly equivalent to calling setFont(...) and 
     * setForeground(...) on an AWT TextArea.
     */
    public static void setJTextPaneFont(JTextPane jtp, Font font) {
        // Start with the current input attributes for the JTextPane. This
        // should ensure that we do not wipe out any existing attributes
        // (such as alignment or other paragraph attributes) currently
        // set on the text area.
        MutableAttributeSet attrs = new SimpleAttributeSet(); // jtp.getInputAttributes();

        // Set the font family, size, and style, based on properties of
        // the Font object. Note that JTextPane supports a number of
        // character attributes beyond those supported by the Font class.
        // For example, underline, strike-through, super- and sub-script.
        setAttrs(font, attrs);

        // Set the font color
        //StyleConstants.setForeground(attrs, c);

        // Retrieve the pane's document object
        StyledDocument doc = jtp.getStyledDocument();

        // Replace the style for the entire document. We exceed the length
        // of the document by 1 so that text entered at the end of the
        // document uses the attributes.
        doc.setCharacterAttributes(0, Integer.MAX_VALUE, attrs, false);
    }

    public static void setAttrs(Font font, MutableAttributeSet attrs) {
		StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
        StyleConstants.setItalic(attrs, (font.getStyle() & Font.ITALIC) != 0);
        StyleConstants.setBold(attrs, (font.getStyle() & Font.BOLD) != 0);
	}
}
