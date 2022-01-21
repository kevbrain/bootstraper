package com.its4u.buildfactory.beans;

import java.util.List;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import com.its4u.buildfactory.ocp.resources.TemplateResource;

import lombok.Data;

@Data
@Component
public class ArgoInitializerBean {
	
	@Value("${argo.server}")
	private String argoServer;

	@Value("${argo.user}")
	private String argoUser;
	
	@Value("${argo.password}")
	private String argoPassword;
	
	public void createInfra(List<TemplateResource> resources) {
		
		for (TemplateResource template:resources) {
			if (template.getName().startsWith("00-")) {
				System.out.println("create "+template.getName());
				parseCreatedRessource(template.getResourceAsString());
			}
		}
		
	}
	
	private void parseCreatedRessource(String resourceyml) {
		System.out.println(resourceyml);
		Yaml yaml = new Yaml();
		for (Object object : yaml.loadAll(resourceyml)) {
			System.out.println(object.toString());
		}
		
	}
}
