package com.tifsoft.mavenbuildbuddy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHookThread extends Thread {
	static final Logger LOG = LoggerFactory.getLogger(ShutdownHookThread.class.getName());

	public ShutdownHookThread() {
		//this.masterControlButtons = masterControlButtons;
	}

	@Override
	public void run() {
		LOG.info(MBBMarkers.MBB, "Shutting down as gracefully as possible");
		LOG.info(MBBMarkers.MBB, "=======================================");
		
		try {
			// give it some time to die...
			Thread.sleep(500); // how long to wait...?
			LOG.info(MBBMarkers.ERROR, "Shut down process completed");
		} catch (final InterruptedException e) {
			LOG.info(MBBMarkers.MBB, "", e);
		}
	}
}
