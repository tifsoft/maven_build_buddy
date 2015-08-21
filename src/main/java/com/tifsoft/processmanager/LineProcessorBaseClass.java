package com.tifsoft.processmanager;


public abstract class LineProcessorBaseClass implements LineProcessorWithThread, LineProcessor {

	protected LineProcessorWithMonitorThread executionThread = null;
	boolean isExited = false;

	@Override
	public abstract void process(String line);

	@Override
	public void setExecutionThread(LineProcessorWithMonitorThread executionThread) {
		this.executionThread = executionThread;
	}

	@Override
	public void exit() {
		// ...
	}

	@Override
	public boolean isExited() {
		return this.isExited;
	}

	@Override
	public void setExited(boolean b) {
		this.isExited = b;
	}
}
