package com.its4u.buildfactory.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilesToAnalyse {
	
	public static final String SCRIPT_FILE= "Script file";
	
	public static final String PROPERTY_FILE= "Properties file";

	private String fileName;
	
	private String content;
	
	private String type;
	
}
