package com.tifsoft.mavenbuildbuddy.line;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.model.BuildModule;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;
import com.tifsoft.mavenbuildbuddy.model.BuildStage;
import com.tifsoft.mavenbuildbuddy.model.TestingStage;
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
			if (lastBuildModule != null) {
				lastBuildModule.setWarningCount(lastBuildModule.getWarningCount() + 1);
			}
		} else if (line.startsWith("[ERR")) {
			color = Color.red;
			if (lastBuildModule != null) {
				lastBuildModule.setErrorCount(lastBuildModule.getErrorCount() + 1);
			}
		} else if (line.startsWith("Tests run: ")) {
			if (line.contains("Time elapsed")) {
				if (lastBuildModule != null) {
					this.lastBuildModule.setTestingStage(TestingStage.TESTING_STAGE_TESTING_FINISHED);
					int indexOfCommaTotal = line.indexOf(",");
					int total = Integer.parseInt(line.substring(11, indexOfCommaTotal));
					this.lastBuildModule.getLabelTotal().setText(" " + total);
					int indexOfFailures = line.indexOf("Failures");
					int indexOfFailuresComma= line.indexOf(",", indexOfFailures);
					int failures = Integer.parseInt(line.substring(indexOfFailures + 10, indexOfFailuresComma));
					this.lastBuildModule.getLabelFail().setText(" " + failures);
					int indexOfErrors = line.indexOf("Errors");
					int indexOfErrorsComma = line.indexOf(",", indexOfErrors);
					int errors = Integer.parseInt(line.substring(indexOfErrors + 8, indexOfErrorsComma));
					this.lastBuildModule.getLabelErrors().setText(" " + errors);
					int indexOfSkipped = line.indexOf("Skipped");
					int indexOfSkippedComma = line.indexOf(",", indexOfSkipped);
					int skipped = Integer.parseInt(line.substring(indexOfSkipped + 9, indexOfSkippedComma));
					this.lastBuildModule.getLabelSkip().setText(" " + skipped);
				}
			}
		} else if (line.startsWith("[INFO] --- maven-")) {
			int beginIndex = line.indexOf(':'); 
			int beginIndex2 = line.indexOf(':', beginIndex + 1) + 1; 
			int endIndex = line.indexOf(' ', beginIndex2);
			String text = line.substring(beginIndex2, endIndex);
			//LOG.info(MBBMarkers.MBB, "***** Found "+text+"*******");
			this.lastBuildModule.setBuildStageText(text);
			if ("test".equals(text)) {
				this.lastBuildModule.setTestingStage(TestingStage.TESTING_STAGE_TESTING_STARTED);
			//} else if ("shade".equals(text)) {
				//finishTestingIfTestingStarted();
			} else {
				finishTestingIfTestingStarted();
			}
			color = new Color(0xFF30B030);
		} else if (line.startsWith("[INFO] Tests are skipped")) {
			this.lastBuildModule.setTestingStage(TestingStage.TESTING_STAGE_SKIPPED);
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
			finishTestingIfTestingStarted();
			this.lastBuildModule = null;
		}
	}

	private void finishTestingIfTestingStarted() {
		if (this.lastBuildModule.getTestingStage() == TestingStage.TESTING_STAGE_TESTING_FINISHED) {
			this.lastBuildModule.setTestingStage(TestingStage.TESTING_STAGE_TESTED);
		} else if (this.lastBuildModule.getTestingStage() == TestingStage.TESTING_STAGE_TESTING_STARTED) {
			this.lastBuildModule.setTestingStage(TestingStage.TESTING_STAGE_NO_TESTS_FOUND);
		}
	}

	private static void appendToPane(final JTextPane tp, final String msg, final Color c) {
		final StyleContext sc = StyleContext.getDefaultStyleContext();
		//AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		MutableAttributeSet aset = tp.getInputAttributes();
		StyleConstants.setForeground(aset, c);
		//aset = sc.addAttribute(aset, StyleConstants.FontFamily, );
		
		//MavenBuildBuddy.gui.preferencesPanel.;

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
