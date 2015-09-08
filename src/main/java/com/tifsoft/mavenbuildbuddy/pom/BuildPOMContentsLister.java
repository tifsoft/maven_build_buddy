package com.tifsoft.mavenbuildbuddy.pom;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.tifsoft.mavenbuildbuddy.MavenBuildBuddy;
import com.tifsoft.mavenbuildbuddy.model.BuildModule;
import com.tifsoft.mavenbuildbuddy.model.BuildPOM;
import com.tifsoft.mavenbuildbuddy.model.BuildProfile;

public class BuildPOMContentsLister {
	private static final String DEFAULT_POM = "DefaultPOM";
	static Logger LOG = LoggerFactory.getLogger(BuildPOMContentsLister.class);

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//BuildPOM buildPOM = MavenBuildBuddy.pomMap.get("DefaultPOM");
		BuildPOM buildPOM = parsePOM("../pom.xml");
		//LOGGER.info("List size: " + profileList.size());
		for (BuildProfile buildProfile : buildPOM.profileList.values()) {
			LOG.info("Profile: " + buildProfile.getName());
			for (BuildModule module : buildProfile.moduleList) {
				LOG.info("Module: " + module.getName());				
			}			
		}
	}

	public static BuildPOM parsePOM(String path) throws ParserConfigurationException, SAXException, IOException {
//		String path = ;
		//Logger.
		BuildXMLHandler handler = new BuildXMLHandler();
		BuildXMLProcessor.executeFile(path, handler);
		//BuildXMLProcessor.profileList = handler.data;
		//LOGGER.info("Build module size: " + BuildXMLProcessor.profileList.size());				
		return MavenBuildBuddy.pomMap.get(DEFAULT_POM);
	}
}
