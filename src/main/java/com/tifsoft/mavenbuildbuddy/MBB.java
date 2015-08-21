package com.tifsoft.mavenbuildbuddy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;

/** Launches MBB - while adding a brief splash screen */
public class MBB {
	
	private static SplashScreen mySplash = null;
	private static Double splashTextArea;
	private static Double splashProgressArea;
	private static Graphics2D splashGraphics;
	private static Font font;
	
	public static void main(final String[] args) {
		splashInit(); // initialize splash overlay drawing parameters
		appInit();
		System.out.println("Start main");
		MavenBuildBuddy.main(args);
		System.out.println("Stop main");
        if (mySplash != null) { // Check if we really had a spash screen
            mySplash.close();   // If so we're now done with it
        }
	}
	
	/**
     * Prepare the global variables for the other splash functions
     */
    private static void splashInit() {		
    	if (MavenBuildBuddy.isHeadless()) {
    		return;
    	}
    	
    	if (MavenBuildBuddy.isOnLinux()) {
    		return;
    	}
    	
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {
        	// If there are any problems displaying the splash this will be null
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            // Stake out some area for our status information
            splashTextArea = new Rectangle2D.Double(15., height*0.77, width * .45, 32.);
            splashProgressArea = new Rectangle2D.Double(width * .55, height*0.77, width*.4, 12 );

            // Create the Graphics environment for drawing status info
            splashGraphics = mySplash.createGraphics();
            font = new Font("Dialog", Font.PLAIN, 14);
            splashGraphics.setFont(font);
            
            // Initialize the status info
            splashText("Starting");
            splashProgress(0);
        } else {
        	System.out.println("mySplash = NULL!!!");
        }
    }
    
    /**
     * Display text in status area of Splash.  Note: no validation it will fit.
     * @param str - text to be displayed
     */
    public static void splashText(String str) {
        if (mySplash != null && mySplash.isVisible()) {   // important to check here so no other methods need to know if there
            // There really is a Splash being displayed

            // Erase the last status text
            splashGraphics.setPaint(Color.WHITE);
            splashGraphics.fill(splashTextArea);

            // Draw the new text
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));

            // Make sure it's displayed
            mySplash.update();
        }
    }
    
    /**
     * Display a (very) basic progress bar
     * @param pct how much of the progress bar to display 0-100
     */
    public static void splashProgress(int pct) {
        if (mySplash != null && mySplash.isVisible()) {

            // Note: 3 colors are used here to demonstrate steps
            // Erase the old one
            splashGraphics.setPaint(Color.WHITE);
            splashGraphics.fill(splashProgressArea);

            // Draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct*wid/100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

            // Fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

            // Make sure it's displayed
            mySplash.update();
        }
    }
    
    /**
     * A stub to simulate a long initialization task that updates
     * the text and progress parts of the status in the SplashScreen
     */
    private static void appInit() {
        for(int i=1;i<=5;i++) {
            int pctDone = i * 20;
            splashText("Start-up task #" + i);
            splashProgress(pctDone);
            try {
                Thread.sleep(80);
            } catch (InterruptedException ex) {
                // Ignore it
            }
        }
    }
}
