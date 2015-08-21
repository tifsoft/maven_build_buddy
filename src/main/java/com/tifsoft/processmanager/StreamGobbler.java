package com.tifsoft.processmanager;

import java.io.BufferedReader;
import java.io.Closeable;
/**
 * Help grab streams from process to prevent problems with process crashing.
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class StreamGobbler extends Thread implements Closeable {
	InputStream is;
	String type;
	LineProcessor processor;

	private final ArrayList<String> lines = new ArrayList<String>();

	StreamGobbler(InputStream is, LineProcessor processor, String type) {
		this.is = is;
		this.type = type;
		this.processor = processor;
	}

	@Override
	public void run() {
		try (InputStreamReader isr = new InputStreamReader(this.is); BufferedReader br = new BufferedReader(isr)) {
			String line = null;
			while ((line = br.readLine()) != null) {
				this.processor.process(line);

				// [INFO] BUILD
				// System.out.print(type + ">" + line + "\n");
				this.lines.add(line);
				// sb.append("\n");
			}
		} catch (final IOException ioe) {
			ioe.printStackTrace();
			if (!this.processor.isExited()) {
				this.processor.setExited(true);
				this.processor.exit();
			}
		}
		if (!this.processor.isExited()) {
			this.processor.setExited(true);
			this.processor.exit();
		}
	}

	public ArrayList<String> getResults() {
		return this.lines; // sb.toString();
	}

	@Override
	public void close() {
		try {
			if (this.is != null) {
				this.is.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
