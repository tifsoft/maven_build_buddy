package com.tifsoft.processmanager;


public interface LineProcessorWithThread {

	LineProcessorWithMonitorThread executionThread = null;
	
	void setExecutionThread(LineProcessorWithMonitorThread executionClientThread);	
}
