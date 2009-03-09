/**********************************************************************************
*
* $Id:$
*
***********************************************************************************
*
* Copyright (c) 2008, 2009 The Regents of the University of California
*
* Licensed under the
* Educational Community License, Version 2.0 (the "License"); you may
* not use this file except in compliance with the License. You may
* obtain a copy of the License at
* 
* http://www.osedu.org/licenses/ECL-2.0
* 
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an "AS IS"
* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing
* permissions and limitations under the License.
*
**********************************************************************************/
package org.sakaiproject.gradebook.gwt.client.gxt.event;

import org.sakaiproject.gradebook.gwt.client.model.StudentModel;

import com.extjs.gxt.ui.client.event.BaseEvent;

public class BrowseLearner extends BaseEvent {

	public enum BrowseType { CURRENT, NEXT, PREV };
	
	public BrowseType type;
	public StudentModel learner;
	
	public BrowseLearner() {
		
	}
	
	public BrowseLearner(StudentModel learner, BrowseType type) {
		this.learner = learner;
		this.type = type;
	}
	
}
