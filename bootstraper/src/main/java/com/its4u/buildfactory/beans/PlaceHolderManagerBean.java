package com.its4u.buildfactory.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
		System.out.println("create PlaceHolder Project");
		System.out.println("appName = "+appName);
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
		
		URL url = new URL(placeholdermanagerUrl+"/createProject");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		System.out.println("Connection to "+url);
		conn.setDoOutput(true);
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		
		System.out.println(jsonProject);
		OutputStream os = conn.getOutputStream();
		os.write(jsonProject.getBytes());
		os.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
	}
	
}
