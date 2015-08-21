package com.tifsoft.processmanager;

public interface LineProcessor {
	void process(String line);
	void exit();
	boolean isExited();
	void setExited(boolean b);
}
