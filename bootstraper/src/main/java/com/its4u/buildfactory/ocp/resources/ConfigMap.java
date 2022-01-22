package com.its4u.buildfactory.ocp.resources;

import java.util.HashMap;

public class ConfigMap extends ConfigResource {
	
	public ConfigMap(int configMapID,String appName) {
		super(configMapID,"-cm-"+configMapID,new HashMap<String,String>());
	}

}
