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
package org.sakaiproject.gradebook.gwt.client.gxt;

import org.sakaiproject.gradebook.gwt.client.action.UserEntityUpdateAction;
import org.sakaiproject.gradebook.gwt.client.gxt.event.GradebookEvents;
import org.sakaiproject.gradebook.gwt.client.gxt.event.UserChangeEvent;
import org.sakaiproject.gradebook.gwt.client.model.GradebookModel;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class GradebookTabItem extends TabItem {

	private String gradebookUid;
	
	public GradebookTabItem(final String gradebookUid) {
		super();
		this.gradebookUid = gradebookUid;
		setMonitorResize(true);
		setLayout(new FitLayout());
		
		addListener(GradebookEvents.UserChange, new Listener<UserChangeEvent>() {

			public void handleEvent(UserChangeEvent uce) {
				if (!(uce.getAction() instanceof UserEntityUpdateAction))
					return;
				
				UserEntityUpdateAction updateAction = (UserEntityUpdateAction)uce.getAction();
				switch (updateAction.getEntityType()) {
				case GRADEBOOK:
					GradebookModel.Key gradebookModelKey = GradebookModel.Key.valueOf(updateAction.getKey());
					
					switch (gradebookModelKey) {
					case NAME:	
						GradebookTabItem.this.setText((String)updateAction.getValue());
						break;
					};
					break;
				}
				
			}
			
		});
		
	}
	
	public String getGradebookUid() {
		return gradebookUid;
	}

}