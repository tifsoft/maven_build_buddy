package com.tifsoft.mavenbuildbuddy.pom;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.tifsoft.mavenbuildbuddy.model.BuildProfile;

public class BuildPOMContentsLister {
	static Logger LOG = LoggerFactory.getLogger(BuildPOMContentsLister.class);

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ArrayList<BuildProfile> profileList = parsePOM();
		//LOGGER.info("List size: " + profileList.size());
		for (BuildProfile buildProfile : profileList) {
			LOG.info("Profile: " + buildProfile.profile);
			for (String module : buildProfile.moduleList) {
				LOG.info("Module: " + module);				
			}			
		}
	}

	public static ArrayList<BuildProfile> parsePOM() throws ParserConfigurationException,
			SAXException, IOException {
		String path = "../" + "pom.xml";
		//Logger.
		BuildXMLHandler handler = new BuildXMLHandler();
		BuildXMLProcessor.executeFile(path, handler);
		//BuildXMLProcessor.profileList = handler.data;
		//LOGGER.info("Build module size: " + BuildXMLProcessor.profileList.size());				
		return BuildXMLProcessor.profileList;
	}

}
