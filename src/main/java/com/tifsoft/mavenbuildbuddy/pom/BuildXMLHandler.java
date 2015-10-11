package com.tifsoft.mavenbuildbuddy.pom;

import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.model.BuildModule;
import com.tifsoft.mavenbuildbuddy.model.BuildPOM;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;
import com.tifsoft.mavenbuildbuddy.utils.LaunchBuildProcesses;

public class BuildXMLHandler extends BuildXMLHandlerBase {
	
	static org.slf4j.Logger LOG = LoggerFactory.getLogger(BuildXMLHandler.class);

	static enum BuildPOMTagType {
		ID,
		PROFILE,
		MODULE
	}
	
	BuildPOMTagType type;
	
	BuildProfile currentBuildProfile = new BuildProfile(LaunchBuildProcesses.DEFAULT_PROFILE);
	
	@Override
	public void characters(final char[] buffer, final int start, final int length) {
		String string = new String(buffer, start, length);
		//LOG.info(string);
		if (this.type != null) {
			switch (this.type) {
				case ID:
					
					buildNewProfile(string);
					break;
				case MODULE:
					//System.out.println("Module: " + string);
					this.currentBuildProfile.moduleList.add(new BuildModule(string));
					this.type = null;
					break;
				default:
					break;
			}
		}
	}

	private void buildNewProfile(String newBuildProfile) {
		this.currentBuildProfile = new BuildProfile(newBuildProfile);
		this.type = null;
	}

	private void storeProfile() {
		BuildPOM pom = MavenBuildBuddy.pomMap.get(BuildPOM.DEFAULT_POM);
		pom.profileList.add(this.currentBuildProfile);
	}

	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if ("id".equalsIgnoreCase(name)) {
			if (this.type == BuildPOMTagType.PROFILE ) {
				//LOG.info("Found ID");
				this.type = BuildPOMTagType.ID;
			}
		} else if ("module".equalsIgnoreCase(name)) {
			//LOG.info("Found module");
			this.type = BuildPOMTagType.MODULE;
		} else if ("profile".equalsIgnoreCase(name)) {
			//LOG.info("Found profile");
			this.type = BuildPOMTagType.PROFILE;
		}
	}

	@Override
    public void endElement (String uri, String localName, String name) throws SAXException {
		if ("profile".equalsIgnoreCase(name)) {
			storeProfile();
		}
	}

	/** This method is called when warnings occur */
	@Override
	public void warning(final SAXParseException exception) {
		System.err.println("WARNING: line " + exception.getLineNumber() + ": " + exception.getMessage());
	}

	/** This method is called when errors occur */
	@Override
	public void error(final SAXParseException exception) {
		System.err.println("ERROR: line " + exception.getLineNumber() + ": " + exception.getMessage());
	}

	/** This method is called when non-recoverable errors occur. */
	@Override
	public void fatalError(final SAXParseException exception) throws SAXException {
		System.err.println("FATAL: line " + exception.getLineNumber() + ": " + exception.getMessage());
		throw (exception);
	}

	@Override
	public void startDocument() throws SAXException {
		//...
	}

	@Override
	public void endDocument() throws SAXException {
		BuildPOM pom = MavenBuildBuddy.pomMap.get(BuildPOM.DEFAULT_POM);
		//pom.profileList.put(this.currentBuildProfile.toString(), this.currentBuildProfile);

		if (pom.profileList.size() == 0) {
			LOG.info("Set up profile: " + this.currentBuildProfile);
			this.storeProfile();
		}
	}
}
