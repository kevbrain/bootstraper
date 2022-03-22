package com.its4u.buildfactory.model.placeholders;

import java.io.Serializable;
import java.util.List;


import lombok.Data;


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

	public String getProject_Id() {
		return project_Id;
	}

	public void setProject_Id(String project_Id) {
		this.project_Id = project_Id;
	}

	public String getGitUrl() {
		return gitUrl!=null?gitUrl:"";
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTeam() {
		return team!=null?team:"";
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getValueChain() {
		return valueChain!=null?valueChain:"";
	}

	public void setValueChain(String valueChain) {
		this.valueChain = valueChain;
	}

	public List<Environments> getEnvironments() {
		return environments;
	}

	public void setEnvironments(List<Environments> environments) {
		this.environments = environments;
	}

	public List<Versions> getVersions() {
		return versions;
	}

	public void setVersions(List<Versions> versions) {
		this.versions = versions;
	}
	
	
	
}
