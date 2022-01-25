package com.its4u.buildfactory.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class PollView implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Stack<String> logPile = new Stack<String>();
	
	private String listLogs;
	
	private int number;
	
	@PostConstruct
    public void init()  {
		listLogs = "";
    }
 
	public void increment() {
		
		StringBuilder stb = new StringBuilder();
		Iterator<String> iter = logPile.iterator();

		while (iter.hasNext()){
			stb.append(iter.next());
		}
		listLogs = stb.toString();  
    }
	
	public int getNumber() {
        return number;
    }

    public void log(String log) {    	
        
    	String logFormated = "[<span style=\"color:green\"><b>INFO</b></span>] "+log+"</br>";
    	logPile.push(logFormated);
    			
    }
  
    public void logError(String log) {    	
        
    	String logFormated = "[<span style=\"color:red\"><b>ERROR</b></span>] "+log+"</br>";
    	logPile.push(logFormated);
    			
    }
    
	public String getListLogs() {
		
		return listLogs;
	}

	public void setListLogs(String listLogs) {
		this.listLogs = listLogs;
	}
    
    
}