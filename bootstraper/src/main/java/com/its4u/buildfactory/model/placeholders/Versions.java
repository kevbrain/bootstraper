package com.its4u.buildfactory.model.placeholders;

import java.io.Serializable;



import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Versions implements Serializable {


		private static final long serialVersionUID = 1L;
		
		private VersionsId versionsid;
		
		@JsonIgnore
	    private Project project;

		public Versions() {
			super();
		}

		public Versions(com.its4u.buildfactory.model.placeholders.VersionsId versionsid, Project project) {
			super();
			this.versionsid = versionsid;
			this.project = project;
		}
				
}
