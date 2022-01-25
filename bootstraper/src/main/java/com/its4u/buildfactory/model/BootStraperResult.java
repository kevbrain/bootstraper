package com.its4u.buildfactory.model;

import lombok.Data;

@Data
public class BootStraperResult {

	private String urlGitDev;
	
	private String urlPlaceHolder;
	
	private String urlCodeReady;
	
	private String urlOpenShift;

	public BootStraperResult(String urlGitDev, String urlPlaceHolder, String urlCodeReady, String urlOpenShift) {
		super();
		this.urlGitDev = urlGitDev;
		this.urlPlaceHolder = urlPlaceHolder;
		this.urlCodeReady = urlCodeReady;
		this.urlOpenShift = urlOpenShift;
	}
	
	
}
