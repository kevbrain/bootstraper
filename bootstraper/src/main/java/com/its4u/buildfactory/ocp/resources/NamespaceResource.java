package com.its4u.buildfactory.ocp.resources;

import lombok.Data;

@Data
public class NamespaceResource {
	
	private String name;	
	
	private boolean activate;

	public NamespaceResource(String name, boolean activate) {
		super();
		this.name = name;
		this.activate = activate;
	}
	
	
}
