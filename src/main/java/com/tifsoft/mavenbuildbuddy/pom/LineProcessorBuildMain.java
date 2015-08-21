package com.tifsoft.mavenbuildbuddy.pom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.processmanager.LineProcessorBaseClass;

public class LineProcessorBuildMain extends LineProcessorBaseClass {

	static final Logger LOG = LoggerFactory.getLogger(LineProcessorBuildMain.class);

	boolean printing = false;

	boolean buildFailure = false;

	@Override
	public void process(String line) {
		if (line.startsWith("[INFO] Reactor")) {
			printLine("");
			printLine("------------------------------------------------------------------------");
			printLine("");

			this.printing = true;
		}
		if (this.printing) {
			printLine(line);
		}
		
		if (line.startsWith("[INFO] ---")) {
			if (this.printing) {
				this.printing = false;
				printLine("");
			}
		} else if (line.startsWith("[INFO] Build")) {
			if (!line.startsWith("[INFO] Building jar:")) {
				if (!line.startsWith("[INFO] Building war:")) {
					printLine(line);
				}
			}
		} else if (line.startsWith("[INFO] BUILD")) {
			printLine("");
			printLine(line);
			if (line.contains("BUILD FAILURE")) {
				this.buildFailure = true;
			}

			//printLine("");
		} else if (line.startsWith("[INFO] Total")) {
			printLine(line);
			printLine("");
		} else if (line.startsWith("[ERROR]")) {
			printLine(line);
		} else if (line.contains("permutation")) {
			printLine(line);
		}
	}

	private static void printLine(String line) {
		if (line.startsWith("[ERROR]")) {
			String localLine = line.replace("[ERROR] ", "");
			LOG.error(localLine);
		} else {
			String localLine = line.replace("[INFO] ", "");
			LOG.info(localLine);
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
	}
}
