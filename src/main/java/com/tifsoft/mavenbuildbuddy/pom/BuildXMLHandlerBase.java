package com.tifsoft.mavenbuildbuddy.pom;

import org.xml.sax.helpers.DefaultHandler;

/** 
 * This class implements the HandlerBase helper class, which means that it
 * defines all the "callback" methods that the SAX parser will invoke to notify
 * the application. In this example we override the methods that we require.
 * 
 * This example uses full package names in places to help keep the JAXP and SAX
 * APIs distinct.
 */
public class BuildXMLHandlerBase extends DefaultHandler {
	public BuildData data = new BuildData();
}
