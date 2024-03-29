package com.its4u.buildfactory.ocp.resources;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.its4u.buildfactory.model.TemplateModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;

@Data
public class TemplateGenerator {


    private final Template template_all_in_one;
    
    private final Template template_configMaps;
    
    private final Template template_secrets;
    
    private final Template template_deployment;
    
    private final Template template_service;
    
    private final Template template_route;
    
    private final Template template_serviceAccount;
    
    private final Template template_scc;
    
    private final Template template_pvc;
    
    private final Template template_implementation_allInOne;
    
    private final Template template_implementation_splited;
    
    private final Template template_maven_application;
    
    private final Template template_maven_classpath;
    
    private final Template template_maven_lombok;
    
    private final Template template_maven_pom;
    
    private final Template template_maven_application_properties;
    
    private final Template template_maven_readMe;
    
    private final Template template_argo_application;
    
    private final Template template_argo_kustomization;
    
    private final Template template_namespace;
    
    private final Template template_rolebinding;
    
    private final Template template_cm_maven;
    
    private final Template template_pipeline;
    
    private final Template template_pipeline_create_branch;
    
    private final Template template_pipeline_tag;
    
    private final Template template_pvc_pipeline;
    
    private final Template template_pipelineTrigger;
    	
    private final Template template_pipelineTriggerBinding;
           
    private final Template template_pipelineTriggerTemplate;
    
    private final Template template_pipelineEventListener;
    
    private final Template template_pipelineEventListenerRoute;
    
    private final Template template_mainPageHtml;
    
    private final Template template_welcomePageRedirection;
    
    private final Template template_tektonStartPipeline;
    
    private final Template template_codeReady_devfile;
    
    private final Template template_quotas_namespace;
    
    private final Template template_defaultLimits_namespace;
    
    private TemplateResource appArgo;
    
    public List<TemplateResource> generatedResources = new ArrayList<>();

    public TemplateGenerator(String pathTemplate ) throws IOException {    	
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        this.generatedResources = new ArrayList<>();
        
        // infra
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate));
        template_rolebinding= cfg.getTemplate("rolebinding.yaml");
        template_implementation_allInOne = cfg.getTemplate("implementation-dar.yaml");
        template_implementation_splited = cfg.getTemplate("implementation-split-dar.yaml");
        
        // ocp resources templates
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate+"//openshift"));
        template_all_in_one = cfg.getTemplate("deployment.yaml");
        template_configMaps = cfg.getTemplate("deployment-configMaps.yaml");
        template_secrets = cfg.getTemplate("deployment-secrets.yaml");
        template_deployment = cfg.getTemplate("deployment-deployment.yaml");
        template_service = cfg.getTemplate("deployment-service.yaml");
        template_route = cfg.getTemplate("deployment-route.yaml");
        template_serviceAccount = cfg.getTemplate("deployment-serviceAccount.yaml");
        template_scc = cfg.getTemplate("deployment-scc.yaml");
        template_pvc = cfg.getTemplate("deployment-pvc.yaml");        
        template_namespace= cfg.getTemplate("namespace.yaml");
        template_quotas_namespace = cfg.getTemplate("quotas.yaml");
        template_defaultLimits_namespace = cfg.getTemplate("defaultLimits.yaml");
                                              
        // gitops argo
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate+"//argocd"));
        template_argo_application = cfg.getTemplate("argo-application.yaml");
        template_argo_kustomization = cfg.getTemplate("argo-kustomization.yaml");
        
             
        // maven resources templates
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate+"//maven"));
        template_maven_application = cfg.getTemplate("application");
        template_maven_classpath = cfg.getTemplate("classpath");
        template_maven_lombok = cfg.getTemplate("lombok.config");
        template_maven_pom = cfg.getTemplate("pom-template.xml");
        template_maven_application_properties = cfg.getTemplate("application-properties");
        template_maven_readMe = cfg.getTemplate("README-md");
        template_cm_maven=cfg.getTemplate("maven-cm.yaml");
        
        
        // joinfaces
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate+"//joinfaces"));
        template_mainPageHtml = cfg.getTemplate("main-page.xhtml");
        template_welcomePageRedirection = cfg.getTemplate("WelcomePageRedirect.template");
        
        // tekton
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate+"//tekton"));
        template_pipeline = cfg.getTemplate("pipeline-build.yaml");
        template_pipelineTrigger = cfg.getTemplate("pipeline-trigger.yaml");
        template_pipelineTriggerBinding = cfg.getTemplate("pipeline-triggerBinding.yaml");
        template_pipelineTriggerTemplate = cfg.getTemplate("pipeline-triggerTemplate.yaml");
        template_pipelineEventListener = cfg.getTemplate("pipeline-eventListener.yaml");
        template_pipelineEventListenerRoute  = cfg.getTemplate("pipeline-eventListenerRoute.yaml");
        template_tektonStartPipeline = cfg.getTemplate("startPipeline.json");
        template_pvc_pipeline=cfg.getTemplate("pvc-claim-pipeline.yaml");
        template_pipeline_create_branch= cfg.getTemplate("pipeline-createBranch.yaml");
        template_pipeline_tag = cfg.getTemplate("pipeline-tag.yaml");
        
        // codeReady
        cfg.setDirectoryForTemplateLoading(new File(pathTemplate+"//codeready"));
        template_codeReady_devfile = cfg.getTemplate("devfile.json");
        
    }

    
    // generate ocp resources
    public TemplateResource generateAllInOne(DeploymentModel model) throws IOException, TemplateException {
    	TemplateResource allInOne = new TemplateResource("Deployment-all.yml",generateResourceWithTemplate(model,template_all_in_one),60,50,40);
    	return allInOne;
    }
    public TemplateResource generateImplemenatationAllInOne(DeploymentModel model) throws IOException, TemplateException {
    	TemplateResource allInOne = new TemplateResource("pom-dar.xml",generateResourceWithTemplate(model,template_implementation_allInOne),0,0,0);
    	return allInOne;
    }
    
    
    
    public List<TemplateResource> generateAllDeployments(DeploymentModel model) throws IOException, TemplateException {

    	this.generatedResources = new ArrayList<>();
    	
    	String argoNameApp = "argoApp-"+model.getAppName()+".yaml";
    	
    	// for all env
	    TemplateResource namespace = new TemplateResource("NS-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_namespace),0,0,0);
	    TemplateResource quotas = new TemplateResource("NS-Q-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_quotas_namespace),0,0,0);
	    TemplateResource defaultLimits = new TemplateResource("NS-DL-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_defaultLimits_namespace),0,0,0);
	    generatedResources.add(namespace); 
	    generatedResources.add(quotas);
	    generatedResources.add(defaultLimits);
    	
    	// only for dev
    	if (model.getEnv().equalsIgnoreCase("dev")) {
		    	if (model.getServiceAccount()!=null) {
		    		TemplateResource serviceAccount = new TemplateResource("00-SA-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_serviceAccount),10,10,55);
		    		generatedResources.add(serviceAccount);
		    		TemplateResource scc = new TemplateResource("00-SCC-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_scc),0,0,0);
		    		generatedResources.add(scc);
		    	}
		    	
		    	TemplateResource configMaps = new TemplateResource("CM-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_configMaps),20,20,50);
		    	TemplateResource secrets = new TemplateResource("S-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_secrets),25,25,25);
		    	TemplateResource deployment = new TemplateResource("D-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_deployment),50,50,10);
		    	TemplateResource service = new TemplateResource("SVC-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_service),55,55,20);
		    	
		    	TemplateResource pipeline = new TemplateResource("00-PL-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipeline),0,0,0);
		    	TemplateResource pipelineTrigger = new TemplateResource("00-T-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipelineTrigger),0,0,0);
		    	TemplateResource pipelineTriggerTemplate = new TemplateResource("00-TT-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipelineTriggerTemplate),0,0,0);
		    	TemplateResource pipelineTriggerBinding = new TemplateResource("00-TB-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipelineTriggerBinding),0,0,0);
		    	TemplateResource pipelineEventListener = new TemplateResource("00-EL-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipelineEventListener),0,0,0);
		    	TemplateResource pipelineEventListenerRoute = new TemplateResource("00-ELR-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipelineEventListenerRoute),0,0,0);
		    	TemplateResource pipelineCreateBranch =  new TemplateResource("00-PLCB-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipeline_create_branch),0,0,0);
		    	TemplateResource pipelineTag = new TemplateResource("00-PLCT-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pipeline_tag),0,0,0);
		    	
		    	TemplateResource mavensetting= new TemplateResource("00-MAVENSETTING-CM-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_cm_maven),0,0,0);
		    	TemplateResource pvcPipeline= new TemplateResource("00-PVCPL-"+model.getOcpNamespace()+".yml",generateResourceWithTemplate(model,template_pvc_pipeline),0,0,0);
		    	appArgo = new TemplateResource(argoNameApp,generateResourceWithTemplate(model,template_argo_application),0,0,0);
		
		    	generatedResources.add(appArgo);		    	    	   	   	       
		    	generatedResources.add(configMaps);
		    	generatedResources.add(secrets);
		    	generatedResources.add(deployment);
		    	generatedResources.add(service);
		    			    	
	    		generatedResources.add(pipeline);
	    		generatedResources.add(pipelineCreateBranch);
	    		generatedResources.add(pipelineTag);
	    		generatedResources.add(pvcPipeline);
	    		generatedResources.add(pipelineTrigger);
	    		generatedResources.add(pipelineTriggerTemplate);
	    		generatedResources.add(pipelineTriggerBinding);
	    		generatedResources.add(pipelineEventListener); 
	    		generatedResources.add(pipelineEventListenerRoute);
	    		generatedResources.add(mavensetting);
		    	
		    	
		    	if (!model.getRoutes().isEmpty()) {
		    		TemplateResource route = new TemplateResource("RT-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_route),60,60,10);
		    		generatedResources.add(route);
		    	}
		    	
		    	if (!model.getPersitentVolumes().isEmpty()) {
		    		TemplateResource pvc = new TemplateResource("00-PVC-"+model.getAppName()+".yml",generateResourceWithTemplate(model,template_pvc),30,30,60);
		    		generatedResources.add(pvc);
		    	}
    	}
 
        return generatedResources;

    }
    
       

    public String generateResourceWithTemplate(TemplateModel model,Template template) throws IOException, TemplateException {

        Writer out = new StringWriter();
        template.process(model, out);
        return out.toString();

    }
    
    

  
}
