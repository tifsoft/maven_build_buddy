package com.tifsoft.mavenbuildbuddy.pom;

public class BuildXMLHandlerFactory implements BuildXMLHandlerFactoryBase {
	@Override
	public BuildXMLHandlerBase getHandler() {
		return new BuildXMLHandler();
	}
}
