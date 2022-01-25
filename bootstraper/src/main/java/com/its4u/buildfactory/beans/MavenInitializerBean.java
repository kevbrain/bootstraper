package com.its4u.buildfactory.beans;

import java.io.IOException;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.its4u.buildfactory.maven.resources.MavenModel;
import com.its4u.buildfactory.maven.resources.ProjectArborescenceItem;
import com.its4u.buildfactory.ocp.resources.TemplateGenerator;
import com.its4u.buildfactory.ocp.resources.TemplateResource;

import freemarker.template.TemplateException;
import lombok.Data;

@Data
@Component
public class MavenInitializerBean {
	
	
	@Autowired
    private DialogBean dialogBean;
	
	@Autowired
    private OcpInitializerBean ocpInitializerBean;
	
	private static Logger logger = LoggerFactory.getLogger(MavenInitializerBean.class);
	
	@Value("${path.template}")
	private String pathTemplate;

	private String group = "com.its4u";
	
	private String artifact;
	
	private String description;
	
	private String packageName;
	
	private String java="11";
	
	private boolean newMavenProject=false;
	
	private boolean lombok=true;
	
	private boolean actuator=true;
	
	private boolean freemarker=false;
	
	private boolean sonar=true;
	
	private boolean web=true;
	
	private boolean joinfaces=false;
	
	private boolean apiRest=false;
	
	private boolean springIntegration=false;
	
	private boolean publishOnGit=true;
	
	private TemplateResource pom_generated;
	
	private TemplateResource lombok_generated;
	
	private TemplateResource source_application_generated;
	
	private TemplateResource application_properties_generated;
	
	private TemplateGenerator generator;
	
	private TemplateResource maven_classpath_generated;
	
	private TemplateResource maven_readMe;
	
	private TemplateResource joinfaces_mainpage;
	
	private TemplateResource joinfaces_redirectionPage;
	
	private MavenModel model;
	
	private TreeNode root;
	
	private TreeNode jkube;
	
	private TreeNode argo;
	
	private TreeNode selectedNode;
	
	
	public void handleNewMavenProject() {
		//newMavenProject=!newMavenProject;
		try {
			generateResources();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
	}
	
	public void generateResources() throws IOException, TemplateException {
	  	logger.info("Generate NEW Maven TEMPLATE for : ");
    	this.generator = new TemplateGenerator(pathTemplate);
    	
    	model = new MavenModel(group, artifact, description, packageName, java,ocpInitializerBean.getNamespace(),ocpInitializerBean.getRegistry(), newMavenProject, lombok, actuator, freemarker, sonar, web, joinfaces);
    	
    	this.pom_generated = new TemplateResource("pom.xml", generator.generateResourceWithTemplate(model,generator.getTemplate_maven_pom()), 0, 0, 0);
    	this.maven_classpath_generated = new TemplateResource(".classpath", generator.generateResourceWithTemplate(model, generator.getTemplate_maven_classpath()), 0,0,0);
    	this.lombok_generated = new TemplateResource("lombok.config", generator.generateResourceWithTemplate(model, generator.getTemplate_maven_lombok()), 0,0,0);
    	this.source_application_generated = new TemplateResource("Application.java", generator.generateResourceWithTemplate(model, generator.getTemplate_maven_application()), 0,0,0);
    	this.application_properties_generated = new TemplateResource("application.properties", generator.generateResourceWithTemplate(model, generator.getTemplate_maven_application_properties()), 0,0,0);
    	this.maven_readMe =new TemplateResource("pom.xml", generator.generateResourceWithTemplate(model,generator.getTemplate_maven_readMe()), 0, 0, 0);
    	this.joinfaces_mainpage = new TemplateResource(model.getArtifact()+".xhtml", generator.generateResourceWithTemplate(model,generator.getTemplate_mainPageHtml()), 0, 0, 0);
    	this.joinfaces_redirectionPage = new TemplateResource(model.getArtifact()+"WelcomePageRedirect.java", generator.generateResourceWithTemplate(model,generator.getTemplate_welcomePageRedirection()), 0, 0, 0);
    	
    	root = new DefaultTreeNode(new ProjectArborescenceItem(artifact, "-",null ), null);
    	new DefaultTreeNode("Text",new ProjectArborescenceItem("pom.xml","Text",pom_generated),root);
    	new DefaultTreeNode("Text",new ProjectArborescenceItem("README.md","Text",maven_readMe),root);
    	new DefaultTreeNode("Text",new ProjectArborescenceItem(".classpath","Text",maven_classpath_generated),root);
    	new DefaultTreeNode("Text",new ProjectArborescenceItem("lombok.config","Text",lombok_generated),root);
    	TreeNode src = new DefaultTreeNode(new ProjectArborescenceItem("src","Folder",null),root);
    	src.setExpanded(true);
    	TreeNode test = new DefaultTreeNode(new ProjectArborescenceItem("test","Folder",null),src);
    	TreeNode testjava = new DefaultTreeNode(new ProjectArborescenceItem("java","Folder",null),test);
    	TreeNode main = new DefaultTreeNode(new ProjectArborescenceItem("main","Folder",null),src);
    	main.setExpanded(true);
    	jkube = new DefaultTreeNode(new ProjectArborescenceItem("jkube","Folder",null),main);
    	argo = new DefaultTreeNode(new ProjectArborescenceItem("argo","Folder",null),main);
    	jkube.setExpanded(true);
    	argo.setExpanded(true);
    	TreeNode java = new DefaultTreeNode(new ProjectArborescenceItem("java","Folder",null),main);
    	TreeNode resources = new DefaultTreeNode(new ProjectArborescenceItem("resources","Folder",null),main);
    	new DefaultTreeNode("Text",new ProjectArborescenceItem("application.properties","Text",application_properties_generated),resources);
    	
    	if (joinfaces) {
    		TreeNode metainf = new DefaultTreeNode(new ProjectArborescenceItem("META-INF","Folder",null),resources);
    		TreeNode resourcesMetainf = new DefaultTreeNode(new ProjectArborescenceItem("resources","Folder",null),metainf);
    		new DefaultTreeNode("Text",new ProjectArborescenceItem(model.getArtifact()+".xhtml","Text",joinfaces_mainpage),resourcesMetainf);
    	}
    	
    	String[] applicationGroup = this.group.split("[.]");

    	TreeNode parentTest = testjava;
    	TreeNode parent=java;
    	TreeNode newGroupPath = null;
    	TreeNode newGroupPathTest = null;
    	for (int i = 0;i < applicationGroup.length; i++) {
    		newGroupPath = new DefaultTreeNode("Text",new ProjectArborescenceItem(applicationGroup[i],"Folder",null),parent);
    		parent=newGroupPath;
    		newGroupPathTest = new DefaultTreeNode("Text",new ProjectArborescenceItem(applicationGroup[i],"Folder",null),parentTest);
    		parentTest=newGroupPathTest;
    	}
    	new DefaultTreeNode("Text",new ProjectArborescenceItem("Application.java","Text",source_application_generated),newGroupPath);
    	if (joinfaces) {
    		 new DefaultTreeNode("Text",new ProjectArborescenceItem("WelcomePageRedirect.java","Text",joinfaces_redirectionPage),newGroupPath);
    	}
	}

    public void createNewDefautTreeNode(String type,String fileName,TemplateResource templateResource,TreeNode parent) {
        new DefaultTreeNode(type,new ProjectArborescenceItem(fileName,type,templateResource),parent);
    }
	
	public void onNodeSelect(NodeSelectEvent event) {
		System.out.println("nodeSelected "+event.getTreeNode().getClass());
		DefaultTreeNode node = (DefaultTreeNode) event.getTreeNode();
		dialogBean.setHeader("No available");
		dialogBean.setContent("No render available");
		if (node.getData() instanceof ProjectArborescenceItem) {
			ProjectArborescenceItem item = (ProjectArborescenceItem) node.getData();
			dialogBean.setHeader(item.getName());
			if (item.getTemplateResource()!=null && item.getTemplateResource().getResourceAsString()!=null)
				dialogBean.setContent(item.getTemplateResource().getResourceAsString());		
		}
	}
}
