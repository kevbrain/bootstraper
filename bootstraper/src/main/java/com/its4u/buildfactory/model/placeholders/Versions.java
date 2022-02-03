package com.its4u.buildfactory.model.placeholders;

import java.io.Serializable;



import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Versions implements Serializable {


		private static final long serialVersionUID = 1L;
		
		private String version;
		
		private String projectId;
		
		@JsonIgnore
	    private Project project;

		public Versions() {
			super();
		}
		
		

		public Versions(String version, Project project) {
			super();
			this.version = version;
			this.project = project;
		}



		public Versions(String version, String projectId) {
			super();
			this.version = version;
			this.projectId = projectId;
		}



		public Versions(String version) {
			super();
			this.version = version;
		}



		public Versions(String version, String projectId, Project project) {
			super();
			this.version = version;
			this.projectId = projectId;
			this.project = project;
		}

		
}
