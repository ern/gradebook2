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
package org.sakaiproject.gradebook.gwt.client.action;

import org.sakaiproject.gradebook.gwt.client.model.CategoryModel;
import org.sakaiproject.gradebook.gwt.client.model.GradebookModel;

public class UserCategoryCreateAction extends UserEntityCreateAction<CategoryModel> {

	private static final long serialVersionUID = 1L;
	
	//protected Boolean isEqualWeight;
	//protected Integer dropLowest;
	
	public UserCategoryCreateAction() {
		super();
	}
	
	public UserCategoryCreateAction(GradebookModel gbModel, String name, 
			Double weight, Boolean isEqualWeight, Integer dropLowest) {
		super(gbModel, EntityType.CATEGORY, gbModel.getGradebookId(), name, weight);
		setIsEqualWeight(isEqualWeight);
		setDropLowest(dropLowest);
	}

	public Boolean getIsEqualWeight() {
		return get(Key.EQUAL_WEIGHT.name());
	}
	
	public void setIsEqualWeight(Boolean equalWeight) {
		set(Key.EQUAL_WEIGHT.name(), equalWeight);
	}

	public Integer getDropLowest() {
		return get(Key.DROP_LOWEST.name());
	}
	
	public void setDropLowest(Integer dropLowest) {
		set(Key.DROP_LOWEST.name(), dropLowest);
	}
	
}