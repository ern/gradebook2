/**********************************************************************************
*
* $Id: GradingScale.java 21205 2007-02-09 20:00:15Z ray@media.berkeley.edu $
*
***********************************************************************************
*
* Copyright (c) 2006 The Regents of the University of California
*
* Licensed under the Educational Community License, Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.opensource.org/licenses/ecl1.php
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
**********************************************************************************/

package org.sakaiproject.gradebook.gwt.sakai.mock.tool.gradebook;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GradingScale implements Serializable, Comparable {
	private Long id;
	private int version;

	private String uid;
	private String name;
	private List<String> grades;
	private Map<String, Double> defaultBottomPercents;	// From grade to percentage
	private boolean unavailable;

	public Map<String, Double> getDefaultBottomPercents() {
		return defaultBottomPercents;
	}
	public void setDefaultBottomPercents(Map<String, Double> defaultBottomPercents) {
		this.defaultBottomPercents = defaultBottomPercents;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Because the Gradebook now supports non-calculated manual-only grades with
	 * no percentage equivalent, it is possible for the list of grades to include
	 * codes that are not included in the defaultBottomPercents map. In other
	 * words, callers shouldn't expect getDefaultBottomPercents.keySet() to be
	 * equivalent to this list.
	 * @return list of supported grade codes, ordered from highest to lowest
	 */
	public List<String> getGrades() {
		return grades;
	}
	public void setGrades(List<String> grades) {
		this.grades = grades;
	}
	public boolean isUnavailable() {
		return unavailable;
	}
	public void setUnavailable(boolean unavailable) {
		this.unavailable = unavailable;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

    public int compareTo(Object o) {
        return getName().compareTo(((GradingScale)o).getName());
    }
    public String toString() {
        return new ToStringBuilder(this).
            append(getUid()).toString();
    }
}