package com.tifsoft.mavenbuildbuddy.line;

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
import com.tifsoft.mavenbuildbuddy.model.BuildModule;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;
import com.tifsoft.mavenbuildbuddy.model.BuildStage;
import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;
import com.tifsoft.processmanager.LineProcessorBaseClass;

public class LineProcessorBuild extends LineProcessorBaseClass {

	static final Logger LOG = LoggerFactory.getLogger(LineProcessorBuild.class.getName());
	
	static Color lastColor = Color.green;
	
	BuildModule lastBuildModule;
	BuildProfile buildProfile;
	BuildStage buildStage;

	public LineProcessorBuild(final BuildProfile buildProfile, BuildStage buildStage) {
		super();
		this.buildProfile = buildProfile;
		this.buildStage = buildStage;
	}

	@Override
	public void process(final String line) {
		printLine(line);
	}

	private void printLine(final String line) {
		final JTextPane textPane = MavenBuildBuddy.gui.textPane;
		final Document doc = textPane.getDocument();
		final int startIdx = doc.getLength();
		// Level level = Level.INFO;
		LOG.info(MBBMarkers.EXECUTE, line);
		// System.out.println(line);
		// textPane.append(line+"\n");
		Color color = lastColor;
		if (line.startsWith("[WARN")) {
			color = Color.yellow;
		} else if (line.startsWith("[ERR")) {
			color = Color.red;
		} else if (line.startsWith("[INFO] --- maven-")) {
			color = new Color(0xFF30B030);
			int beginIndex = line.indexOf(':'); 
			int beginIndex2 = line.indexOf(':', beginIndex + 1) + 1; 
			int endIndex = line.indexOf(' ', beginIndex2);
			String text = line.substring(beginIndex2, endIndex);
			//LOG.info(MBBMarkers.MBB, "***** Found "+text+"*******");
			this.lastBuildModule.setBuildStageText(text);
		} else if (line.startsWith("[INFO] ---")) {
			color = new Color(0xFF00C000);
		} else if (line.startsWith("[INFO] Building ")) {
			color = new Color(0xFF66FF66);
			for (final BuildModule buildModule : this.buildProfile.moduleList) {
				if (line.startsWith("[INFO] Building " + buildModule.name)) {
					//LOG.info(MBBMarkers.MBB, "***** Found "+buildModule.name+"*******");
					buildModule.setBackground(Color.yellow);
					finishLastModule();
					this.lastBuildModule = buildModule;
				}
			}
		} else if (line.startsWith("[INFO] Includ")) {
			color = new Color(0xFF00A000);
		} else if (line.startsWith("[INFO] BUILD SUCCESS")) {
			color = Color.green;
			finishLastModule();
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

	private void finishLastModule() {
		if (this.lastBuildModule != null) {
			this.lastBuildModule.setBackground(Color.green);
			this.lastBuildModule.setBuildStage(buildStage);
			this.lastBuildModule = null;
		}
	}

	private static void appendToPane(final JTextPane tp, final String msg, final Color c) {
		final StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		// aset = sc.addAttribute(aset, StyleConstants.Alignment,
		// StyleConstants.ALIGN_JUSTIFIED);

		final int len = tp.getDocument().getLength();
		final Document doc = tp.getDocument();
		try {
			doc.insertString(doc.getLength(), msg, aset);
		} catch (final BadLocationException e) {
			e.printStackTrace();
		}
	}
}
