package com.grassvsmower.components;

import org.springframework.stereotype.Component;

import com.grassvsmower.entities.Works;
import com.grassvsmower.model.WorksModel;

@Component("worksConverter")
public class WorksConverter {
	
	public Works workModel2Works(WorksModel worksModel){
		
		Works retval = null;
		
		if (worksModel!=null) {
			retval = new Works();
			retval.setAfterPhoto(worksModel.getAfterPhoto());
			retval.setBeforePhoto(worksModel.getBeforePhoto());
			retval.setId(worksModel.getId());
			retval.setShowHomePage(worksModel.isShowHomePage());
			retval.setDate(worksModel.getDate());
		}
		return retval;
	}
	
	public WorksModel works2workModel(Works works){
		
		WorksModel retval = null;
		
		if (works!=null) {
			retval.setAfterPhoto(works.getAfterPhoto());
			retval.setBeforePhoto(works.getBeforePhoto());
			retval.setId(works.getId());
			retval.setShowHomePage(works.isShowHomePage());
			retval.setDate(works.getDate());
		}
			
		return retval;
	}

}
