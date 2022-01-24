package com.its4u.buildfactory.model;

import lombok.Data;

@Data
public class CodeReadyModel extends TemplateModel{
	
	public String appName;

	public CodeReadyModel(String appName) {
		super();
		this.appName = appName;
	}
	
	

}
