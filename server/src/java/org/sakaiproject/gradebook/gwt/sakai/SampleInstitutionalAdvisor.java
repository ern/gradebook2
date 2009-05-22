package org.sakaiproject.gradebook.gwt.sakai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.authz.api.Member;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.gradebook.gwt.sakai.model.UserDereference;
import org.sakaiproject.site.api.Group;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.ToolManager;


public class SampleInstitutionalAdvisor implements InstitutionalAdvisor {
	
	private static final Log log = LogFactory.getLog(SampleInstitutionalAdvisor.class);
	
	private final String CONTENT_TYPE_TEXT_HTML_UTF8 = "text/html; charset=UTF-8";
	private final char PLUS = '+';
	private final char MINUS = '-';
	private final String FILE_EXTENSION = ".csv";
	private final String FILE_HEADER = "User Eid,Name,Site Title : Group Title,Grade";
	
	String finalGradeSubmissionPath = null;
	
	private SiteService siteService = null;
	private ToolManager toolManager = null;
	
	public String getExportCourseManagementId(String userEid, Group group) {

		if(null == group) {
			log.error("ERROR : Group is null");
			return null;
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(group.getContainingSite().getTitle());
		stringBuilder.append(" : ");
		stringBuilder.append(group.getTitle());
		
		return stringBuilder.toString();
	}

	public String getExportUserId(UserDereference dereference) {

		return dereference.getDisplayId();
	}
	
	public String getFinalGradeUserId(UserDereference dereference) {
		
		return dereference.getEid();
	}
	
	public String[] getLearnerRoleNames() {
		String[] roleKeys = { "Student", "Open Campus", "access" };
		return roleKeys;
	}
	
	
	public boolean isLearner(Member member) {
		String role = member.getRole() == null ? "" : member.getRole().getId();
		
		return (role.equalsIgnoreCase("Student") 
				|| role.equalsIgnoreCase("Open Campus")
				|| role.equalsIgnoreCase("Access"))
				&& member.isActive();
	}

	public void submitFinalGrade(List<Map<Column, String>> studentDataList, String gradebookUid, HttpServletRequest request, HttpServletResponse response) {

		if (null == finalGradeSubmissionPath || "".equals(finalGradeSubmissionPath)) {
			log.error("ERROR: Null and or empty test failed for finalGradeSubmissionPath");
			// 500 Internal Server Error
			response.setStatus(500);
			return;
		}

		// Test if the path has a trailing file separator
		if(!finalGradeSubmissionPath.endsWith(File.separator)) {
			finalGradeSubmissionPath += File.separator;
		}

		response.setContentType(CONTENT_TYPE_TEXT_HTML_UTF8);
		PrintWriter responsePrintWriter = null;

		try {
			responsePrintWriter = response.getWriter();

		} catch (IOException e1) {

			log.error("EXCEPTION: Wasn't able to get the servlet's response wirter");
			// 500 Internal Server Error
			response.setStatus(500);
			e1.printStackTrace();
			return;
		}

		// Getting the siteId
		String siteId = null;

		try {

			Site site = siteService.getSite(toolManager.getCurrentPlacement().getContext());
			siteId = site.getId();

		} catch (IdUnusedException e2) {
			log.error("EXCEPTION: Wasn't able to get the siteId");
			// 500 Internal Server Error
			response.setStatus(500);
			e2.printStackTrace();
			return;
		}

		try {

			siteId = URLEncoder.encode(siteId, "utf-8");

		} catch (UnsupportedEncodingException e1) {
			log.error("EXCEPTION: Wasn't able to url encode the siteId");
			// 500 Internal Server Error
			response.setStatus(500);
			e1.printStackTrace();
			return;
		}

		// Putting the redirect url in the response so that the client can get it
		// FIXME : Check on the client side so that this is handled 
		//responsePrintWriter.println(finalGradeSubmissionUrl + siteId);

		// Test if path to final grade submission file exits
		File finalGradesPath = new File(finalGradeSubmissionPath);
		if(!finalGradesPath.exists()) {
			try {
				
				if(!finalGradesPath.mkdirs()) {
					log.error("Wasn't able to create final grade submission folder(s)");
					// 500 Internal Server Error
					response.setStatus(500);
					return;
				}
			}
			catch(SecurityException se) {
				log.error("EXCEPTION: Wasn't able to create final grade submission folder(s)");
				// 500 Internal Server Error
				response.setStatus(500);
				se.printStackTrace();
				return;
			}
		}
		
		// Using string buffer for thread safety
		StringBuffer finalGradeSubmissionFile = new StringBuffer();
		finalGradeSubmissionFile.append(finalGradeSubmissionPath);
		finalGradeSubmissionFile.append(siteId);
		finalGradeSubmissionFile.append(FILE_EXTENSION);
		File finalGradesFile = new File(finalGradeSubmissionFile.toString());
		
		log.info("Writing final grades to " + finalGradesFile.getPath());
		
		PrintWriter filePrintWriter = null;

		try {
			if ((finalGradesFile.createNewFile() || (finalGradesFile.delete() && finalGradesFile.createNewFile())) && finalGradesFile.canWrite()) {

				filePrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(finalGradesFile)));

				filePrintWriter.println(FILE_HEADER);
				
				// Using string buffer for thread safety
				StringBuffer exportData = null;

				for (Map<Column, String> studentData : studentDataList) {
					exportData = new StringBuffer();
					exportData.append(studentData.get(Column.FINAL_GRADE_USER_ID));
					exportData.append(",");
					exportData.append(studentData.get(Column.STUDENT_NAME));
					exportData.append(",");
					exportData.append(studentData.get(Column.EXPORT_CM_ID));
					exportData.append(",");
					exportData.append(extractLetterGrade(studentData.get(Column.STUDENT_GRADE)));
					filePrintWriter.println(exportData.toString());
				}

				filePrintWriter.flush();
				filePrintWriter.close();

				// 201 Created
				response.setStatus(201);
			} else {
				log.error("Wasn't able to create final grade submission file");
				// 500 Internal Server Error
				response.setStatus(500);
				return;
			}

		} catch (IOException e) {

			log.error("EXCEPTION: Wasn't able to access the final grade submission file");
			// 500 Internal Server Error
			response.setStatus(500);
			e.printStackTrace();
			return;
		}
		
	}
	
	/*
	 * Helper Methods
	 */
	private String extractLetterGrade(String studentGrade) {

		if (null == studentGrade) {
			return null;
		}

		final char[] studentGradeChars = studentGrade.toCharArray();

		if (Character.isLetter(studentGradeChars[0])) {

			if (PLUS == studentGradeChars[1] || MINUS == studentGradeChars[1]) {

				char[] letterGrade = { studentGradeChars[0], studentGradeChars[1] };
				return new String(letterGrade);
			} else {

				char[] letterGrade = { studentGradeChars[0] };
				return new String(letterGrade);
			}
		}

		return null;
	}
	
	/*
	 * IOC setters:
	 */
	
	public void setFinalGradeSubmissionPath(String finalGradeSubmissionPath) {
		
		this.finalGradeSubmissionPath = finalGradeSubmissionPath;
	}
	
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	public void setToolManager(ToolManager toolManager) {
		this.toolManager = toolManager;
	}

}