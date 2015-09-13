package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SystemTrayIcon {
	public static TrayIcon trayIcon;
	public static Image imageBusy;
	public static Image imageOK;

	static void createAndShowGUI() {
		try {
			if (!SystemTray.isSupported()) {
				System.out.println("SystemTray is not supported");
				return;
			}
			final PopupMenu popup = new PopupMenu();
			imageBusy = createImage("mbb_busy_status_icon.png", "tray icon");
			imageOK = createImage("mbb_ok_status_icon.png", "tray icon");
			trayIcon = new TrayIcon(imageOK);
			setOK();
			final SystemTray tray = SystemTray.getSystemTray();

			final MenuItem aboutItem = new MenuItem("About");
			final MenuItem exitItem = new MenuItem("Exit");

			popup.add(aboutItem);
			popup.addSeparator();
			popup.add(exitItem);
			trayIcon.setPopupMenu(popup);
			tray.add(trayIcon);
			aboutItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Maven Build Buddy - a GUI front end for Maven");
				}
			});

			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					tray.remove(trayIcon);
					System.exit(0);
				}
			});
		} catch (final Exception e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}
	}

	public static void setBusy() {
		trayIcon.setImage(imageBusy);
		trayIcon.setToolTip("Maven Build Buddy - building");
	}

	public static void setOK() {
		trayIcon.setImage(imageOK);
		trayIcon.setToolTip("Maven Build Buddy - waiting");
	}

	private static Image createImage(final String path, final String description) {
		final URL imageURL = SystemTrayIcon.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		}
		return (new ImageIcon(imageURL, description)).getImage();
	}
}
