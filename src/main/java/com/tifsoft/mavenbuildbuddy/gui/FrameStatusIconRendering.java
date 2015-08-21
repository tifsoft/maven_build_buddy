package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class FrameStatusIconRendering extends Thread {
	public static void setRedFrameIcon(JFrame frame) {
		Image image = frame.createImage(29, 29);
		if (image != null) {
			Graphics g = image.getGraphics();
			outerRectangles(g, Color.black, Color.red);
	
			int max = 14;
			for (int i = 0; i < max; i++) {
				int temp = (max - i) * 17;
				Color c = new Color(temp << 16);
				g.setColor(c);
				g.drawRect(i, i, 28 - i - i, 28 - i - i);
			}
	
			frame.setIconImage(image);
		}
	}

	public static void setOrangeFrameIcon(JFrame frame) {
		Image image = frame.createImage(29, 29);
		Graphics g = image.getGraphics();
		outerRectangles(g, Color.black, Color.orange);

		int max = 14;
		for (int i = 0; i < max; i++) {
			int temp = (max - i) * 17;
			Color c = new Color((temp << 8) | (temp << 16));
			g.setColor(c);
			g.drawRect(i, i, 28 - i - i, 28 - i - i);
		}

		frame.setIconImage(image);
	}

	public static void setGreyFrameIcon(JFrame frame) {
		Image image = frame.createImage(29, 29);
		Graphics g = image.getGraphics();
		outerRectangles(g, Color.black, Color.magenta);

		int max = 14;
		for (int i = 0; i < max; i++) {
			int temp = (max - i) * 17;
			Color c = new Color((temp << 16) | temp);
			g.setColor(c);
			g.drawRect(i, i, 28 - i - i, 28 - i - i);
		}

		frame.setIconImage(image);
	}
	
	public static void setGreenFrameIcon(JFrame frame) {
		Image image = frame.createImage(29, 29);
		Graphics g = image.getGraphics();
		outerRectangles(g, Color.black, Color.green);

		int max = 14;
		for (int i = 0; i < max; i++) {
			int temp = (max - i) * 17;
			Color c = new Color(temp << 8);
			//Color c = new Color((temp << 8) | (temp ^ 0xFF));
			g.setColor(c);

			g.drawRect(i, i, 28 - i - i, 28 - i - i);
		}

		frame.setIconImage(image);
	}
	
	public static void setTestFrameIcon(JFrame frame) {
		Image image = frame.createImage(29, 29);
		Graphics g = image.getGraphics();
		outerRectangles(g, Color.black, Color.green);

		int max = 14;
		for (int i = 0; i < max; i += 7) {
			int temp = (max - i) * 18;
			Color c = new Color(temp | (temp << 8) | (temp << 16));
			g.setColor(c);
			g.drawRect(i, i, 28 - i - i, 28 - i - i);
		}

		frame.setIconImage(image);
	}



	private static void outerRectangles(Graphics g, Color outside, Color inside) {
		g.setColor(outside);
		g.fillRect(0, 0, 30, 30);
		g.setColor(inside);
		g.drawRect(0, 0, 28, 28);
	}
}
