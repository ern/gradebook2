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
package org.sakaiproject.gradebook.gwt.client.gxt.multigrade;

import org.sakaiproject.gradebook.gwt.client.model.StudentModel;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;

public class ExtraCreditNumericCellRenderer extends NumericCellRenderer {

	public String render(StudentModel model, String property,
			ColumnData config, int rowIndex, int colIndex,
			ListStore<StudentModel> store) {
		
		return new StringBuilder("<div style=\"color:darkgreen;\">")
			.append(super.render(model, property, config, rowIndex, colIndex, store))
			.append("</div>").toString();
	}
	
}