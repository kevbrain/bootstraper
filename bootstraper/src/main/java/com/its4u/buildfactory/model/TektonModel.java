package com.its4u.buildfactory.model;



import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TektonModel extends TemplateModel {

	public String appName;

	public TektonModel(String appName) {
		super();
		this.appName = appName;
	}
	
	
	
}
