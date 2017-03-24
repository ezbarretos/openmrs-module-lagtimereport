/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.lagtimereport.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.propertyeditor.LagTimeReportSetupEditor;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.openmrs.validator.ValidateUtil;
import org.openmrs.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class configured as controller using annotation and mapped with the URL of
 * 'module/${rootArtifactid}/${rootArtifactid}Link.form'.
 */
@Controller
public class LagTimeReportController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	LagTimeReportSetupService lagtimeService;
	
	public LagTimeReportSetupService getLagtimeService() {
		return lagtimeService;
	}
	
	public void setLagtimeService(LagTimeReportSetupService lagtimeService) {
		this.lagtimeService = lagtimeService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LagTimeReportSetup.class, new LagTimeReportSetupEditor());
	}
	
	@RequestMapping(value = "/module/lagtimereport/addLagTimeReportSetup", method = RequestMethod.GET)
	public void showSetupLagTimeForm() {
		
	}
	
	@ModelAttribute("lagTimeTeportSetup")
	public LagTimeReportSetup getLagTimeReportSetup(
	        @RequestParam(value = "lagtimereportId", required = false) Integer lagtimereportId) {
		if (lagtimereportId != null) {
			LagTimeReportSetup lagTimeTeportSetup = lagtimeService.getLagTimeReportSetup(lagtimereportId);
			
			return lagTimeTeportSetup;
		}
		LagTimeReportSetup lagTimeTeportSetup = new LagTimeReportSetup();
		
		return lagTimeTeportSetup;
	}
	
	@RequestMapping(value = "/module/lagtimereport/addLagTimeReportSetup", method = RequestMethod.POST)
	public String onSubmit(HttpServletRequest request, LagTimeReportSetup lagTimeReportSetup,
	        @ModelAttribute("lagTimeTeportSetup") LagTimeReportSetup lagtimereport, BindingResult result) throws Exception {
		
		HttpSession httpSession = request.getSession();
		
		//if (Context.isAuthenticated()) {
		
		if (request.getParameter("save") != null) {
			ValidateUtil.validate(lagTimeReportSetup, result);
			if (result.hasErrors()) {
				return null;
			} else {
				lagtimeService.saveLagTimeReportSetup(lagTimeReportSetup);
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "lagtimereport.saved");
			}
		}
		
		//}
		
		return "redirect:lagtimereportList.list";
	}
	
}
