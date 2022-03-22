package com.its4u.buildfactory.beans;

import java.util.LinkedHashMap;
import java.util.List;

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
	
	@Value("${argo.repo.gitops}")
	private String gitOpsRepo;
	
	@Value("${argo.repo.gitapps}")
	private String gitOpsAppsRepo;
	
	@Value("${argo.projet}")
	private String argoProj;
	
	public void createInfra(List<TemplateResource> resources) {
		
		for (TemplateResource template:resources) {
			if (template.getName().startsWith("00-")) {
				System.out.println("create "+template.getName());
				parseCreatedRessource(template.getResourceAsString());
			}
		}
		
	}
	
	private void parseCreatedRessource(String resourceyml) {
		//System.out.println(resourceyml);
		Yaml yamlfile = new Yaml();
		for (Object obj:yamlfile.loadAll(resourceyml)) {
			LinkedHashMap lhm = (LinkedHashMap) obj;
			String group="";
			String version="";
			String apiVersionAndGroup = (String) lhm.get("apiVersion");
			
			if (apiVersionAndGroup.split("/").length>1) {
				group=apiVersionAndGroup.split("/")[0];
				version=apiVersionAndGroup.split("/")[1];
			} else {
				version= apiVersionAndGroup;
			}
			String kind = (String) lhm.get("kind");
			String name=(String)((LinkedHashMap)lhm.get("metadata")).get("name");
			String namespace=(String)((LinkedHashMap)lhm.get("metadata")).get("namespace");
			
			System.out.println(group);
			System.out.println(version);
			System.out.println(kind);
			System.out.println(name);
			System.out.println(namespace);
		}
		
	
	}
}
