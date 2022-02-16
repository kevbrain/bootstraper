package com.its4u.buildfactory.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.its4u.buildfactory.model.placeholders.Environments;
import com.its4u.buildfactory.model.placeholders.PlaceHolderId;
import com.its4u.buildfactory.model.placeholders.PlaceHolders;
import com.its4u.buildfactory.model.placeholders.Project;
import com.its4u.buildfactory.model.placeholders.Versions;
import com.its4u.buildfactory.ocp.resources.ConfigMap;
import com.its4u.buildfactory.ocp.resources.NamespaceResource;
import com.its4u.buildfactory.ocp.resources.Secrets;



@Service
public class PlaceHolderManagerService {

	public Project createProject(String projectName,String gitURl,List<ConfigMap> cms,List<Secrets> secrets,
									HashMap<String,NamespaceResource> env_namespaces,
									String gitOpsRepo, String gitOpsAppsRepo, String argoProj,
									String argoServer, String argoUser , String argoPassword) {
		Project project = new Project(projectName, gitURl, "Kevyn");
		
		List<Versions> versions = new ArrayList<Versions>();
		versions.add(new Versions("0.0.1-SNAPSHOT",project.getProject_Id(),project));
		project.setVersions(versions);
		
		List<Environments> envsProject =  new ArrayList<>();
		for (String keyenv:env_namespaces.keySet()) {
			//if (environments.get(keyenv)) {
				System.out.println("Create Environment : "+keyenv);
				Environments env = new Environments(project,projectName+"-"+keyenv);
				env.setGitOpsRepo(gitOpsRepo);
				env.setGitOpsAppsRepo(gitOpsAppsRepo);
				env.setArgoProj(argoProj);
				env.setGitOpsAppsRepo(gitURl);
				env.setArgoServer(argoServer);
				env.setArgoUser(argoUser);
				env.setArgoPassword(argoPassword);
				if (keyenv.equalsIgnoreCase("dev")) {
					env.setPlaceholders(createplaceHoldersForEnv(env,cms,secrets,keyenv,env_namespaces.get(keyenv).getName()));					
				} 
				envsProject.add(env);
			//}
		}
		project.setEnvironments(envsProject);
		return project;
	}
	
	public List<PlaceHolders> createplaceHoldersForEnv(Environments env,List<ConfigMap> cms,List<Secrets> secrets,String keyenv, String namespace) {
		List<PlaceHolders> placeHolders = new ArrayList<>();
		for (ConfigMap cm:cms) {
			for (String cmKey :cm.getKeyValue().keySet()) {
				String valueKey=cm.getKeyValue().get(cmKey);
				if (cmKey.equalsIgnoreCase("ocp-namespace")) {valueKey=env.getEnvironment();}
				if (cmKey.equalsIgnoreCase("app-version")) {valueKey="0.0.1-SNAPSHOT";}
				if (cmKey.equalsIgnoreCase("ocp-cluster.registry")) {valueKey="image-registry.openshift-image-registry.svc.cluster.local:5000";}
				if (cmKey.equalsIgnoreCase("cluster-suffix")) {valueKey="apps.ocp-lab.its4u.eu";}
				if (cmKey.equalsIgnoreCase("ocp-namespace")) {valueKey=namespace;}
				if (cmKey.equalsIgnoreCase("ocp.environment")) {valueKey=keyenv;}
				if (cmKey.equalsIgnoreCase("ocp.replicas")) {valueKey="1";}
				if (cmKey.equalsIgnoreCase("app-request.memory")) {valueKey="250Mi";}
				if (cmKey.equalsIgnoreCase("app-request.cpu")) {valueKey="200m";}
				if (cmKey.equalsIgnoreCase("app-limit.memory")) {valueKey="750Mi";}
				if (cmKey.equalsIgnoreCase("app-limit.cpu")) {valueKey="500m";}
				if (cmKey.equalsIgnoreCase("app-actuator.port")) {valueKey="8080";}
				if (cmKey.equalsIgnoreCase("app-container.port")) {valueKey="8080";}
				
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
