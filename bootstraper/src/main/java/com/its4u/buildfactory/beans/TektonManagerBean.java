package com.its4u.buildfactory.beans;


//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.its4u.buildfactory.model.TektonModel;
import com.its4u.buildfactory.ocp.resources.TemplateGenerator;
import com.its4u.buildfactory.ocp.resources.TemplateResource;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import lombok.Data;

@Data
@Component
public class TektonManagerBean {
	
	@Value("${path.template}")
	private String pathTemplate;
	
	private TemplateGenerator generator;
	
	private TektonModel tektonModel;
	
	private TemplateResource body_generated;
	
	public void startPipelineExecution(String projectName,String namespace) throws Exception {
		
		//Generate Body
		this.generator = new TemplateGenerator(pathTemplate);
		this.tektonModel = new TektonModel(projectName);
		this.body_generated = new TemplateResource("body.json", generator.generateResourceWithTemplate(tektonModel,generator.getTemplate_tektonStartPipeline()), 0, 0, 0);
		
		Unirest.setTimeouts(0, 0);
		String url = "http://el-"+namespace+".apps.ocp-lab.its4u.eu/";
		System.out.println("Trigger url = "+url);
		try {
			HttpResponse<String> response = Unirest.post(url)
					  .header("content-type", "application/json")
					  .body(body_generated.getResourceAsString())
					  .asString();		
			System.out.println(response.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Tekton build pipeline launched"));
		
	}
}
