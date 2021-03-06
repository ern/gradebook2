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

package org.sakaiproject.gradebook.gwt.client.gxt.a11y;

import com.extjs.gxt.ui.client.widget.TabItem;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Accessibility;

public class AriaTabItem extends TabItem {

	public AriaTabItem() {
		super();
	}
	
	public AriaTabItem(String text) {
		super(text);
	}
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		Accessibility.setRole(el().dom, Accessibility.ROLE_TAB);
		Accessibility.setState(el().dom, "aria-controls", getTabPanel().el().getId());
	}
	
}
