package com.its4u.buildfactory.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.its4u.buildfactory.model.placeholders.Environments;
import com.its4u.buildfactory.model.placeholders.PlaceHolderId;
import com.its4u.buildfactory.model.placeholders.PlaceHolders;
import com.its4u.buildfactory.model.placeholders.Project;
import com.its4u.buildfactory.ocp.resources.ConfigMap;
import com.its4u.buildfactory.ocp.resources.Secrets;



@Service
public class PlaceHolderManagerService {

	public Project createProject(String projectName,String gitURl,List<ConfigMap> cms,List<Secrets> secrets,HashMap<String,Boolean> environments) {
		Project project = new Project(projectName, gitURl, "Kevyn");
		List<Environments> envsProject =  new ArrayList<>();
		for (String keyenv:environments.keySet()) {
			if (environments.get(keyenv)) {
				System.out.println("Create Environment : "+keyenv);
				Environments env = new Environments(project,projectName+"-"+keyenv);
				if (keyenv.equalsIgnoreCase("dev")) {
					env.setPlaceholders(createplaceHoldersForEnv(env,cms,secrets,keyenv));
				} 
				envsProject.add(env);
			}
		}
		project.setEnvironments(envsProject);
		return project;
	}
	
	public List<PlaceHolders> createplaceHoldersForEnv(Environments env,List<ConfigMap> cms,List<Secrets> secrets,String keyenv) {
		List<PlaceHolders> placeHolders = new ArrayList<>();
		for (ConfigMap cm:cms) {
			for (String cmKey :cm.getKeyValue().keySet()) {
				String valueKey=cm.getKeyValue().get(cmKey);
				if (cmKey.equalsIgnoreCase("ocp-namespace")) {valueKey=env.getEnvironment();}
				if (cmKey.equalsIgnoreCase("app-version")) {valueKey="0.0.1-SNAPSHOT";}
				if (cmKey.equalsIgnoreCase("ocp-cluster.registry")) {valueKey="image-registry.openshift-image-registry.svc.cluster.local:5000";}
				if (cmKey.equalsIgnoreCase("cluster-suffix")) {valueKey="apps.ocp-lab.its4u.eu";}
				if (cmKey.equalsIgnoreCase("ocp-namespace")) {valueKey=env.getEnvironment();}
				if (cmKey.equalsIgnoreCase("ocp.environment")) {valueKey=keyenv;}
				if (cmKey.equalsIgnoreCase("ocp.replicas")) {valueKey="1";}
				placeHolders.add(new PlaceHolders(new PlaceHolderId(env.getEnvironment(),cmKey),env,valueKey,""));
			}
		}
		for (Secrets secret:secrets) {
			for (String secretKey :secret.getKeyValue().keySet()) {
				String valueKey=secret.getKeyValue().get(secretKey);
				if (secretKey.equalsIgnoreCase("actuator.password")) {valueKey="password";}
				placeHolders.add(new PlaceHolders(new PlaceHolderId(env.getEnvironment(),secretKey),env,valueKey,"secret"));
			}
		}
		return placeHolders;
	}
	}
