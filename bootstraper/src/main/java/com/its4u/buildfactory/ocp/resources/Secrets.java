package com.its4u.buildfactory.ocp.resources;

import java.util.HashMap;

public class Secrets extends ConfigResource{
	
	public Secrets(int secretID,String appName) {
		super(secretID,"-secret-"+secretID,new HashMap<String,String>());
	}
		
}
