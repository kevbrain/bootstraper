package com.its4u.buildfactory.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its4u.buildfactory.model.placeholders.Project;
import com.its4u.buildfactory.ocp.resources.ConfigMap;
import com.its4u.buildfactory.ocp.resources.Secrets;
import com.its4u.buildfactory.services.PlaceHolderManagerService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.Data;

@Data
@Component
public class PlaceHolderManagerBean {

	@Autowired
	private PollView pollView;
	
	@Autowired
    private OcpInitializerBean ocpInitializerBean;
		
	@Autowired
    private GitInitializerBean gitInitializerBean;
	
	@Value("${placeholdermanager.url}")
	private String placeholdermanagerUrl;
	
	@Autowired
	private PlaceHolderManagerService placeHolderService;
	
	public void createPlaceHolderProject(String appName,List<ConfigMap> cms,List<Secrets> secrets) {
		
		pollView.log("create PlaceHolder Project : "+appName);
		String gitUrl = gitInitializerBean.getGitUrlPrefix()+appName+".git";
		System.out.println("GitUrl = "+gitInitializerBean.getGitUrl());
		Project myProject = placeHolderService.createProject(
				appName,
				gitUrl,
				cms,
				secrets,
				ocpInitializerBean.getNamespaces());
		System.out.println(myProject.getProject_Id()+ " created with success !");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonProject = mapper.writeValueAsString(myProject);
			postProjectToPlaceHolderManager(jsonProject);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Project Placeholder created"));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void postProjectToPlaceHolderManager(String jsonProject) throws IOException {
		
		Unirest.setTimeouts(0, 0);
		String url = placeholdermanagerUrl+"/createProject";
		try {
			HttpResponse<String> response = Unirest.post(url)
				  .header("content-type", "application/json")
				  .body(jsonProject)
				  .asString();		
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void applyConf(String projectName) throws IOException {
		
		System.out.println("Apply Conf and sync "+projectName);
		
		Unirest.setTimeouts(0, 0);
		String url = placeholdermanagerUrl+"/apply-conf/"+projectName;
		try {
			HttpResponse<String> response = Unirest.get(url).asString();	
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void sync(String projectName) throws IOException {
		
		System.out.println("Sync project "+projectName);
		
		Unirest.setTimeouts(0, 0);
		String url = placeholdermanagerUrl+"/sync/"+projectName;
		try {
			HttpResponse<String> response = Unirest.get(url).asString();	
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
