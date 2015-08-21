package com.tifsoft.mavenbuildbuddy.utils;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class MBBMarkers {
	public static Marker MBB = MarkerFactory.getMarker("SBCC");
	public static Marker STOP = MarkerFactory.getMarker("STOP");
	public static Marker BUILD = MarkerFactory.getMarker("BUILD");
	public static Marker REFRESH = MarkerFactory.getMarker("REFRESH");
	public static Marker CLIENT = MarkerFactory.getMarker("CLIENT");
	public static Marker SERVER = MarkerFactory.getMarker("SERVER");
	public static Marker USERS = MarkerFactory.getMarker("USERS");	
	public static Marker EXECUTE = MarkerFactory.getMarker("EXECUTE");		
	public static Marker REPLICATION = MarkerFactory.getMarker("REPLICATION");	
	public static Marker MISC = MarkerFactory.getMarker("MISC");	
	public static Marker ERROR = MarkerFactory.getMarker("ERROR");	
}
