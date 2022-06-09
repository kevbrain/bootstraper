package com.its4u.buildfactory.beans;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.its4u.buildfactory.model.CodeReadyModel;
import com.its4u.buildfactory.ocp.resources.TemplateGenerator;
import com.its4u.buildfactory.ocp.resources.TemplateResource;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.Data;

@Data
@Component
public class CodeReadyWorspaceManagerBean {
	
	@Value("${codeready.user}")
	private String user;
	
	@Value("${codeready.client.id}")
	private String clientId;
	
	@Value("${codeready.access.token.url}")
	private String authProviderUrl;
	
	@Value("${codeready.workspace.url}")
	private String workspaceBaseUrl;
	
	@Value("${codeready.user.password}")
	private String userPassword;
	
	@Value("${codeready.client.secret}")
	private String clientSecret;
	
	@Value("${path.template}")
	private String pathTemplate;
	
	private TemplateGenerator generator;
	
	private CodeReadyModel codeReadyModel;
	
	private TemplateResource body_generated;
	
	private String getToken() {
		Unirest.setTimeouts(0, 0);
		
		try {
			HttpResponse<JsonNode> response = Unirest.post(authProviderUrl)
			  .header("Content-Type", "application/x-www-form-urlencoded")
			  .field("grant_type", "password")
			  .field("client_id", clientId)
			  .field("username", user)
			  .field("password", userPassword)
			  .field("client_secret", clientSecret)
			  .asJson();
			return response.getBody().getObject().getString("access_token");
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
	
	public void createWorkspace(String projectName) throws Exception {
		
		this.generator = new TemplateGenerator(pathTemplate);
		this.codeReadyModel = new CodeReadyModel(projectName);
		this.body_generated = new TemplateResource("body.json", generator.generateResourceWithTemplate(codeReadyModel,generator.getTemplate_codeReady_devfile()), 0, 0, 0);
		
		Unirest.setTimeouts(0, 0);
		String url = workspaceBaseUrl+"/api/workspace/devfile";
		System.out.println("url called = "+url);
		try {
			HttpResponse<String> response = Unirest.post(url)
					  .header("Authorization", "Bearer "+getToken())
					  .header("content-type", "application/json")
					  .body(body_generated.getResourceAsString())
					  .asString();		
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
		}
	}

}
