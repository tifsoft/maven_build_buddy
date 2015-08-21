package com.tifsoft.processmanager;

import java.io.Closeable;
import java.io.OutputStream;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tifsoft.mavenbuildbuddy.utils.MBBMarkers;

public class TestCommand implements Closeable {
	private static int threadSleepDelay = 2000;
	private int timeoutTime = 0;
	private boolean keyPressRegularly = true;
	private volatile boolean killed = false;
	private volatile boolean sayHi = false;
	
	Process process = null;

	static final Logger LOG = LoggerFactory.getLogger(TestCommand.class);

	LineProcessorLogger logger = string -> LOG.info(MBBMarkers.EXECUTE, string);

	public ArrayList<String> testCommand(final LineProcessor processor, final String command) {
		final String[] commands = command.split(" ");
		return testCommand(processor, commands);
	}

	public ArrayList<String> testCommand(final LineProcessor processor, final String... commands) {
		// LOG.info(PMMarkers.EXECUTE, "Executing the script using args: " +
		// Arrays.toString(commands));
		final ArrayList<String> resultsList = null;
		StreamGobbler outputGobbler = null;
		StreamGobbler errorGobbler = null;
		//OutputStream keycommands = null;

		// final long t1 = System.currentTimeMillis();
		// final StringBuilder sb = new StringBuilder("");
		try {
			// int returnValue;
			final int timeoutInSeconds = this.timeoutTime;

			// final Runtime rt = Runtime.getRuntime();
			// final Process process = rt.exec(command);
			// Todo use: Use
			// http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html

			// String[] commands = command.split(" ");

			final ProcessBuilder processBuilder = new ProcessBuilder(commands);
			this.process = processBuilder.start();
			// get its output (your input) stream

			// any error message?
			errorGobbler = new StreamGobbler(this.process.getErrorStream(), processor, "ERROR");

			// any output?
			outputGobbler = new StreamGobbler(this.process.getInputStream(), processor, "OUTPUT");

			try (OutputStream keycommands = this.process.getOutputStream()) {
	
				// kick them off
				errorGobbler.start();
				outputGobbler.start();
	
				final long now = System.currentTimeMillis();
				final long timeoutInMillis = 1000L * timeoutInSeconds;
				final long finish = now + timeoutInMillis;
				do {
					try {
						if (this.keyPressRegularly) {
							keycommands.write(32); // press space?
							keycommands.flush();
							// System.out.println("space pressed!");
						}
					} catch (final Throwable e) {
						this.logger.log("Error: " + e.toString());
					}
	
					if (this.sayHi) {
						// this.sayHi = false;
						this.logger.log("Thread says HI in loop");
						this.logger.log("this.killed: " + this.killed);
						this.logger.log("this.isAlive(proc): " + isAlive(this.process));
						this.logger.log("this.hasTimedOut(finish): " + hasTimedOut(finish));
					}
	
					Thread.sleep(threadSleepDelay);
				} while (isAlive(this.process) && !hasTimedOut(finish) && !this.killed);
	
				this.logger.log("Exit of this process here");
	
				if (this.killed) {
					// LOG.info("Call to proc.destroy");
					this.process.destroy();
					Thread.sleep(400);
				}
	
				if (isAlive(this.process)) {
					if (!this.killed) {
						final String message = "Process timeout out after " + timeoutInSeconds
								+ " seconds";
						this.logger.log(message);
						processor.exit();
						throw new InterruptedException(message);
					}
				}
	
				// processor.exit();
	
				return resultsList;
			}
		} catch (final Throwable t) {
			this.logger.log("Exit of this process was caused by the error: " + t.toString());
			LOG.info("Exit of this process was caused by the error: ", t);
		} finally {
			if (errorGobbler != null) {
				errorGobbler.close();
			}
			if (outputGobbler != null) {
				outputGobbler.close();
			}
		}

		// processor.exit();
		if (!processor.isExited()) {
			processor.setExited(true);
			// processor.exit();
		}

		return resultsList;
	}

	private boolean hasTimedOut(final long finish) {
		if (this.timeoutTime == 0) {
			return false;
		}

		if (System.currentTimeMillis() < finish) {
			return false;
		}

		return true;
	}

	public static boolean isAlive(final Process process) {
		try {
			process.exitValue();
		} catch (final IllegalThreadStateException e) {
			return true;
		}

		return false;
	}

	public void setKeyPressRegularly(final boolean keyPressRegularly) {
		this.keyPressRegularly = keyPressRegularly;
	}

	public void setKilled(final boolean killed) {
		this.killed = killed;
	}

	public void setTimeoutTime(final int timeoutTime) {
		this.timeoutTime = timeoutTime;
	}

	public void setSayHi(boolean sayHi) {
		this.sayHi = sayHi;
	}

	public static void setThreadSleepDelay(int threadSleepDelay) {
		TestCommand.threadSleepDelay = threadSleepDelay;
	}

	public void setLogger(LineProcessorLogger logger) {
		this.logger = logger;
	}
	
	@Override
	public void close() {
		this.process.destroy();
		this.process.destroyForcibly();
	}
}
