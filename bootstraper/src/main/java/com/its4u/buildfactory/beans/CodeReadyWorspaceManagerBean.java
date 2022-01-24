package com.its4u.buildfactory.beans;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

}
