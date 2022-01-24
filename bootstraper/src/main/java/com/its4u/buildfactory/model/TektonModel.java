package com.its4u.buildfactory.model;


import lombok.Data;

@Data
public class TektonModel extends TemplateModel {

	public String appName;

	public TektonModel(String appName) {
		super();
		this.appName = appName;
	}
	
	
	
}
