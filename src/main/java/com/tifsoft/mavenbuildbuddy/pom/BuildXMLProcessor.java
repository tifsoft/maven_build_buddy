package com.tifsoft.mavenbuildbuddy.pom;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tifsoft.mavenbuildbuddy.model.BuildProfile;

public class BuildXMLProcessor {
	static ArrayList<BuildProfile> profileList;
	
	static void executeFile(final String path, BuildXMLHandlerBase handler) throws ParserConfigurationException, SAXException, IOException {
		profileList = new ArrayList<BuildProfile>();
		// Create a JAXP "parser factory" for creating SAX parsers
		final javax.xml.parsers.SAXParserFactory spf = SAXParserFactory.newInstance();

		// Configure the parser factory for the type of parsers we require
		spf.setValidating(false); // No validation required

		// Now use the parser factory to create a SAXParser object
		// Note that SAXParser is a JAXP class, not a SAX class
		final javax.xml.parsers.SAXParser sp = spf.newSAXParser();

		// Create a SAX input source for the file argument
		final org.xml.sax.InputSource input = new InputSource(new FileReader(path));

		// Give the InputSource an absolute URL for the file, so that
		// it can resolve relative URLs in a <!DOCTYPE> declaration, e.g.
		input.setSystemId("file://" + new File(path).getAbsolutePath());

		// Create an instance of this class; it defines all the handler methods
		//final UnitTestXMLHandler handler = new UnitTestXMLHandler();

		//handler.module = path;

		// Finally, tell the parser to parse the input and notify the handler
		try {
			sp.parse(input, handler);
		} catch (Exception e) {
			System.out.println("Problem processing path: " + path);
			e.printStackTrace();
		}

		// Instead of using the SAXParser.parse() method, which is part of the
		// JAXP API, we could also use the SAX1 API directly. Note the
		// difference between the JAXP class javax.xml.parsers.SAXParser and
		// the SAX1 class org.xml.sax.Parser
		//
		// org.xml.sax.Parser parser = sp.getParser(); // Get the SAX parser
		// parser.setDocumentHandler(handler); // Set main handler
		// parser.setErrorHandler(handler); // Set error handler
		// parser.parse(input); // Parse!

	}
}
