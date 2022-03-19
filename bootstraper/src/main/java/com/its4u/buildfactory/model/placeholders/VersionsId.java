package com.its4u.buildfactory.model.placeholders;

import java.io.Serializable;
import lombok.Data;

@Data
public class VersionsId implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectId;
	
	private String version;
	
	

	public VersionsId(String projectId, String version) {
		super();
		this.projectId = projectId;
		this.version = version;
	}



	public VersionsId() {
		super();
	}
	
	
	
	

}
