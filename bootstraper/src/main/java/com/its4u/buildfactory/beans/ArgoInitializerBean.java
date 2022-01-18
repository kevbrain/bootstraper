package com.its4u.buildfactory.beans;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ArgoInitializerBean {
	
	@Value("${argo.server")
	private String argoServer;

	@Value("${argo.user")
	private String argoUser;
	
	@Value("${argo.password}")
	private String argoPassword;
}
