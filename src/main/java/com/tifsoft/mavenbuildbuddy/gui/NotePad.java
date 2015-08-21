package com.tifsoft.mavenbuildbuddy.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NotePad {
	JFrame mainFrame;
	JMenuBar menuBar;
	JMenu file;
	JMenu edit;
	JMenu format;
	JMenu view;
	JMenu help;
	JMenuItem finew;
	JMenuItem open;
	JMenuItem save;
	JMenuItem saveAs;
	JMenuItem pageSetup;
	JMenuItem print;
	JMenuItem exit;
	JMenuItem undo;
	JMenuItem cut;
	JMenuItem copy;
	JMenuItem paste;
	JMenuItem delete;
	JMenuItem find;
	JMenuItem findNext;
	JMenuItem replace;
	JMenuItem goTo;
	JMenuItem selectAll;
	JMenuItem timeDate;
	JMenuItem wordRap;
	JMenuItem font;
	JMenuItem statusBar;
	JMenuItem helpTopics;
	JMenuItem aboutNotepad;
	JScrollPane scrollPane;
	JTextArea textArea;
	JFileChooser fileChooser;
	File pathToJDK;

	public NotePad(String s) {
		this.mainFrame = new JFrame(s + " - Notepad");
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.menuBar = new JMenuBar();
		this.file = new JMenu("File");
		this.edit = new JMenu("Edit");
		this.format = new JMenu("Format");
		this.view = new JMenu("View");
		this.help = new JMenu("Help");
		this.finew = new JMenuItem("New");
		this.open = new JMenuItem("Open...");
		this.save = new JMenuItem("Save");
		this.saveAs = new JMenuItem("Save As...");
		this.pageSetup = new JMenuItem("Page Setup...");
		this.print = new JMenuItem("Print...");
		this.exit = new JMenuItem("Exit");
		this.undo = new JMenuItem("Undo");
		this.cut = new JMenuItem("Cut");
		this.copy = new JMenuItem("Copy");
		this.paste = new JMenuItem("Paste");
		this.delete = new JMenuItem("Delete");
		this.find = new JMenuItem("Find...");
		this.findNext = new JMenuItem("Find Next");
		this.replace = new JMenuItem("Replace...");
		this.goTo = new JMenuItem("Go To...");
		this.selectAll = new JMenuItem("Select All");
		this.timeDate = new JMenuItem("Time//Date");
		this.wordRap = new JMenuItem("Word Wrap");
		this.font = new JMenuItem("Font...");
		this.statusBar = new JMenuItem("Status Bar");
		this.helpTopics = new JMenuItem("Help Topics");
		this.aboutNotepad = new JMenuItem("About Notepad");
		this.textArea = new JTextArea();
		this.scrollPane = new JScrollPane(this.textArea);
		this.pathToJDK = new File("C:/"); // where to start looking...
		this.fileChooser = new JFileChooser(this.pathToJDK);
		this.file.add(this.finew);
		this.file.add(this.open);
		this.file.add(this.save);
		this.file.add(this.saveAs);
		this.file.addSeparator();
		this.file.add(this.pageSetup);
		this.file.add(this.print);
		this.file.addSeparator();
		this.file.add(this.exit);
		this.edit.add(this.undo);
		this.edit.addSeparator();
		this.edit.add(this.cut);
		this.edit.add(this.copy);
		this.edit.add(this.paste);
		this.edit.add(this.delete);
		this.edit.addSeparator();
		this.edit.add(this.find);
		this.edit.add(this.findNext);
		this.edit.add(this.replace);
		this.edit.add(this.goTo);
		this.edit.addSeparator();
		this.edit.add(this.selectAll);
		this.edit.add(this.timeDate);
		this.menuBar.add(this.file);
		this.menuBar.add(this.edit);
		this.menuBar.add(this.format);
		this.menuBar.add(this.view);
		this.menuBar.add(this.help);
		this.format.add(this.wordRap);
		this.format.add(this.font);
		this.view.add(this.statusBar);
		this.help.add(this.helpTopics);
		this.help.addSeparator();
		this.help.add(this.aboutNotepad);
		this.mainFrame.add(this.menuBar, BorderLayout.NORTH);
		this.mainFrame.add(this.scrollPane, BorderLayout.CENTER);
		this.mainFrame.setSize(760, 540);
		this.mainFrame.setVisible(true);
		final Open op = new Open(this);
		this.open.addActionListener(op);
		final Save sa = new Save(this);
		this.save.addActionListener(sa);
		final SaveAs saas = new SaveAs(this);
		this.saveAs.addActionListener(saas);
		final Exit ex = new Exit(this);
		this.exit.addActionListener(ex);
	}

	public void loadNamedFile(File myFile) throws IOException {
		try (FileInputStream fin = new FileInputStream(myFile)) {
			try (BufferedReader din = new BufferedReader(new InputStreamReader(fin))) {
				String s = "";
				while (s != null) {
					s = din.readLine();
					// System.out.println(s);
					this.textArea.append(s + "\n");
					// ba.textArea.setText(s);
				}
			}
		}
	}

	public static void main(String[] args) {
		final NotePad notePad = new NotePad("Untitled");

		notePad.setText("Test\nText");
	}

	public void setText(String output) {
		this.textArea.setText(output);
	}
}

class Open implements ActionListener {
	NotePad op;

	Open(NotePad op) {
		this.op = op;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final int returnVal = this.op.fileChooser
				.showOpenDialog(this.op.mainFrame);
		final File file = this.op.fileChooser.getSelectedFile();
		try {
			loadFile(returnVal, file);
		} catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (final IOException e1) {
			e1.printStackTrace();
		}
	}

	private void loadFile(int returnVal, File file)
			throws IOException {
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.op.loadNamedFile(file);
		} else if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Saving Canceled");
		}
		System.out.println("returnVal = " + returnVal
				+ " and ba.fileChooser.APPROVE_OPTION = "
				+ JFileChooser.APPROVE_OPTION);
	}
}

class Save implements ActionListener {
	NotePad sa;

	Save(NotePad sa) {
		this.sa = sa;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final int returnVal = this.sa.fileChooser
				.showSaveDialog(this.sa.mainFrame);
		try {
			final File file = this.sa.fileChooser.getSelectedFile();
			final String name = file.toString();
			try (FileOutputStream fout = new FileOutputStream(name, false)) {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fout.write("________________________________________HEADER______________________________________________\n"
							.getBytes());
					fout.write(("\""
							+ this.sa.pathToJDK.getPath()
							+ "\" is "
							+ String.valueOf(this.sa.pathToJDK.isFile() ? ""
									: "not") + " a file, ").getBytes());
					fout.write(("but is "
							+ String.valueOf(this.sa.pathToJDK.isDirectory() ? ""
									: "not") + " a directory. ").getBytes());
					fout.write(("And is last modified at: " + String
							.valueOf(this.sa.pathToJDK.lastModified())).getBytes());
					fout.write("\n______________________________________ CONTENT ______________________________________________\n"
							.getBytes());
					fout.write(("\n" + this.sa.textArea.getText()).getBytes());
				} else if (returnVal != JFileChooser.APPROVE_OPTION) {
					System.out.println("Saving Canceled");
				}
				System.out.println("returnVal = " + returnVal
						+ " and ba.fileChooser.APPROVE_OPTION = "
						+ JFileChooser.APPROVE_OPTION);
			}
		} catch (final FileNotFoundException ex) {
			// ...
		} catch (final IOException e1) {
			// ...
		}
	}
}

class SaveAs implements ActionListener {
	NotePad saas;

	SaveAs(NotePad saas) {
		this.saas = saas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final int returnVal = this.saas.fileChooser.showDialog(
				this.saas.mainFrame, "Save As...");
		try {
			final File file = this.saas.fileChooser.getSelectedFile();
			final String name = file.toString();
			try (FileOutputStream fout = new FileOutputStream(name, true)) {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fout.write("________________________________________HEADER______________________________________________\n"
							.getBytes());
					fout.write(("\""
							+ this.saas.pathToJDK.getPath()
							+ "\" is "
							+ String.valueOf(this.saas.pathToJDK.isFile() ? ""
									: "not") + " a file, ").getBytes());
					fout.write(("but is "
							+ String.valueOf(this.saas.pathToJDK.isDirectory() ? ""
									: "not") + " a directory. ").getBytes());
					fout.write(("And is last modified at: " + String
							.valueOf(this.saas.pathToJDK.lastModified()))
							.getBytes());
					fout.write("\n______________________________________ CONTENT ______________________________________________\n"
							.getBytes());
					fout.write(("\n" + this.saas.textArea.getText()).getBytes());
				} else if (returnVal != JFileChooser.APPROVE_OPTION) {
					System.out.println("Saving Canceled");
				}
				System.out.println("returnVal = " + returnVal
						+ " and ba.fileChooser.APPROVE_OPTION = "
						+ JFileChooser.APPROVE_OPTION);
			}
		} catch (final FileNotFoundException ex) {
			// TODO Auto-generated catch block
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
		}
	}
}

class Exit implements ActionListener {
	NotePad ex;

	Exit(NotePad ex) {
		this.ex = ex;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ex.exit) {
			System.exit(0);
		}
	}
}
