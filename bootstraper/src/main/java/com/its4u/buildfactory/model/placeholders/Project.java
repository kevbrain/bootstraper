package com.its4u.buildfactory.model.placeholders;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

@Data

public class Project implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private String project_Id;
	
	private String gitUrl;
	
	private String owner;
	
	private String team;
	
	private String valueChain;

	private List<Environments> environments;
	
	private List<Versions> versions;

	public Project() {
		super();
	}

	public Project(String projectId, String gitUrl, String owner, String team, String valueChain) {
		
		super();
		this.project_Id = projectId;
		this.gitUrl = gitUrl;
		this.owner = owner;
		this.team = team;
		this.valueChain = valueChain;
		System.out.println("New project placeholder : "+projectId+" "+gitUrl+" "+owner);
	}

	
	public String toString() {
		return project_Id ;
	}
	
}
