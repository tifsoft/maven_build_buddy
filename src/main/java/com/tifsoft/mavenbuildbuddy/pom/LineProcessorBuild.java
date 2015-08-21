package com.tifsoft.mavenbuildbuddy.pom;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;
import com.tifsoft.processmanager.LineProcessorBaseClass;

public class LineProcessorBuild extends LineProcessorBaseClass {

	static final Logger LOG = LoggerFactory.getLogger(LineProcessorBuild.class.getName());
	
	static Color lastColor = Color.green;

	@Override
	public void process(String line) {
		printLine(line);
	}

	private static void printLine(String line) {
		JTextPane textPane = MavenBuildBuddy.gui.textPane;
		Document doc = textPane.getDocument();
		int startIdx = doc.getLength();
		// Level level = Level.INFO;
		LOG.info(MBBMarkers.EXECUTE, line);
		// System.out.println(line);
		// textPane.append(line+"\n");
		Color color = lastColor;
		if (line.startsWith("[WARN")) {
			color = Color.yellow;
		} else if (line.startsWith("[ERR")) {
			color = Color.red;
		} else if (line.startsWith("[INFO] ---")) {
			color = new Color(0xFF00C000);
		} else if (line.startsWith("[INFO] Building")) {
			color = new Color(0xFF66FF66);
		} else if (line.startsWith("[INFO] Includ")) {
			color = new Color(0xFF00A000);
		} else if (line.startsWith("[INFO]")) {
			color = Color.green;
		} else if (line.startsWith("---------")) {
			color = new Color(0xFFB0B0FF);
		//} else if (line.startsWith("T E S T S")) {
			//color = Color.magenta;
		}
		
		lastColor = color;
		// if (line.startsWith("[WARN")) {
		// if (line.startsWith("[WARN")) {
		// Highlighter h = textArea.getHighlighter();
		// try {
		// DefaultHighlightPainter highlightPainterWarn = new
		// DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
		// h.addHighlight(startIdx, startIdx + line.length(),
		// highlightPainterWarn);
		// } catch (BadLocationException e) {
		// e.printStackTrace();
		// }
		// textArea.setHighlighter(h);
		// }
		// Document doc = textArea.getDocument();
		appendToPane(textPane, line + "\n", color);
	}

	private static void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		// aset = sc.addAttribute(aset, StyleConstants.Alignment,
		// StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		Document doc = tp.getDocument();
		try {
			doc.insertString(doc.getLength(), msg, aset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
