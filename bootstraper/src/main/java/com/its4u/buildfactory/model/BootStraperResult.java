package com.its4u.buildfactory.model;


public class BootStraperResult {

	private String urlGitDev;
	
	private String urlPlaceHolder;
	
	private String urlCodeReady;
	
	private String urlOpenShift;

	
	public BootStraperResult() {
		super();
		this.urlGitDev = "";
		this.urlPlaceHolder = "";
		this.urlCodeReady = "";
		this.urlOpenShift = "";
	}
	
	public BootStraperResult(String urlGitDev, String urlPlaceHolder, String urlCodeReady, String urlOpenShift) {
		super();
		this.urlGitDev = urlGitDev;
		this.urlPlaceHolder = urlPlaceHolder;
		this.urlCodeReady = urlCodeReady;
		this.urlOpenShift = urlOpenShift;
	}

	public String getUrlGitDev() {
		return urlGitDev;
	}

	public void setUrlGitDev(String urlGitDev) {
		this.urlGitDev = urlGitDev;
	}

	public String getUrlPlaceHolder() {
		return urlPlaceHolder;
	}

	public void setUrlPlaceHolder(String urlPlaceHolder) {
		this.urlPlaceHolder = urlPlaceHolder;
	}

	public String getUrlCodeReady() {
		return urlCodeReady;
	}

	public void setUrlCodeReady(String urlCodeReady) {
		this.urlCodeReady = urlCodeReady;
	}

	public String getUrlOpenShift() {
		return urlOpenShift;
	}

	public void setUrlOpenShift(String urlOpenShift) {
		this.urlOpenShift = urlOpenShift;
	}
	
	




	
	
}
