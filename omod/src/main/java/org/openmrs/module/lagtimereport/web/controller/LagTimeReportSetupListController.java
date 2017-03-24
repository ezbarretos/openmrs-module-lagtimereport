/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LagTimeReportSetupListController {
	
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
	
	@RequestMapping(value = "/module/lagtimereport/lagtimereportList.list", method = RequestMethod.GET)
	public void showForm(ModelMap model, @RequestParam(required = false, value = "includedRetired") String includedRetired) {
		
		List<LagTimeReportSetup> lagTimeReports = new ArrayList<LagTimeReportSetup>();
		
		lagTimeReports = lagtimeService.getAllLagTimeReportSetups();
		
		model.addAttribute("lagTimeReports", lagTimeReports);
	}
	
	@RequestMapping(value = "/module/lagtimereport/lagtimereportList.list", method = RequestMethod.POST)
	public String retireLagTimeReportSetup(HttpServletRequest request, ModelMap model) {
		
		if (request.getParameterValues("checkRetire") != null) {
			for (String id : request.getParameterValues("checkRetire")) {
				LagTimeReportSetup lagTimeReport = lagtimeService.getLagTimeReportSetup(Integer.parseInt(id));
				lagtimeService.retireLagTimeReportSetup(lagTimeReport, "Teste");
			}
		}
		return "redirect:lagtimereportList.list";
	}
}
