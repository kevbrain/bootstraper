package com.its4u.buildfactory.beans;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DialogBean {
	
	private String header;
	
	private String content;

}
