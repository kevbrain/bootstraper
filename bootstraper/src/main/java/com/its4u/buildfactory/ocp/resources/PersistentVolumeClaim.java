package com.its4u.buildfactory.ocp.resources;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersistentVolumeClaim {

	private String name;
	
	private String accessModes;
	
	private int gbStorage;
	
	private String storageClassName;
	
	private String volumeMode;

	public PersistentVolumeClaim(String name) {
		super();
		this.name = name;
	}

}
