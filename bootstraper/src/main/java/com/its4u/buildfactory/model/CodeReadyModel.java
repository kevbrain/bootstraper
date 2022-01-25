package com.its4u.buildfactory.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CodeReadyModel extends TemplateModel{
	
	public String appName;

	public CodeReadyModel(String appName) {
		super();
		this.appName = appName;
	}
	
	

}
