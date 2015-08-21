package com.tifsoft.processmanager;

import java.io.Closeable;
import java.util.ArrayList;

/* A monitoring thread for each line processor (thread may not really be needed)
 * Also groups the lineProcessor and its associated process together */
public class LineProcessorWithMonitorThread extends Thread implements Closeable {

	String[] exec;

	private LineProcessorBaseClass lineProcessor;

	public TestCommand testCommand;

	public LineProcessorWithMonitorThread(final String exec, final LineProcessorBaseClass lineProcessor) {
		this.exec = exec.split(" ");
		this.lineProcessor = lineProcessor;
		this.lineProcessor.setExecutionThread(this);
	}

	public LineProcessorWithMonitorThread(final LineProcessorBaseClass lineProcessor, String... exec) {
		this.exec = exec;
		this.lineProcessor = lineProcessor;
		this.lineProcessor.setExecutionThread(this);
	}

	@Override
	public void run() {
		this.testCommand = new TestCommand();
		this.testCommand.setKeyPressRegularly(false);
		//LOG.info(PMMarkers.EXECUTE, "Script execution started");
		final ArrayList<String> out = this.testCommand.testCommand(this.lineProcessor, this.exec);
		// LOG.info("Script execution ended");

		if (out == null) {
			// LOG.info("Script aborted");
		} else {
			// LOG.info("Script exit");
		}
	}

	public void killProcess() {
		if (this.testCommand != null) {
			this.testCommand.setKilled(true);
		}
	}

	public void setSayHi(final boolean b) {
		if (this.testCommand != null) {
			this.testCommand.setSayHi(b);
		}
	}

	public LineProcessorBaseClass getLineProcessor() {
		return this.lineProcessor;
	}

	public void setLineProcessor(LineProcessorBaseClass lineProcessor) {
		this.lineProcessor = lineProcessor;
	}
	
	@Override
	public void close() {
		if (this.testCommand != null) {
			this.testCommand.close();
		}
	}
}
