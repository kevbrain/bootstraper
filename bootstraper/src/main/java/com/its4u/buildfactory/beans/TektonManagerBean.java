package com.its4u.buildfactory.beans;


import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.its4u.buildfactory.model.TektonModel;
import com.its4u.buildfactory.ocp.resources.TemplateGenerator;
import com.its4u.buildfactory.ocp.resources.TemplateResource;
import com.mashape.unirest.http.Unirest;

import lombok.Data;

@Data
@Component
public class TektonManagerBean {
	
	@Value("${path.template}")
	private String pathTemplate;
	
	private TemplateGenerator generator;
	
	private TektonModel tektonModel;
	
	private TemplateResource body_generated;
	
	public void startPipelineExecution(String projectName) throws Exception {
		
		//Generate Body
		this.generator = new TemplateGenerator(pathTemplate);
		this.tektonModel = new TektonModel(projectName);
		this.body_generated = new TemplateResource("body.json", generator.generateResourceWithTemplate(tektonModel,generator.getTemplate_tektonStartPipeline()), 0, 0, 0);
		
		System.out.println(body_generated.getResourceAsString());
		
		Unirest.setTimeouts(0, 0);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Tekton build pipeline launched"));
		
	}
}
