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

import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Form;
import org.openmrs.api.FormService;
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
	
	@Autowired
	FormService formService;
	
	public LagTimeReportSetupService getLagtimeService() {
		return lagtimeService;
	}
	
	public void setLagtimeService(LagTimeReportSetupService lagtimeService) {
		this.lagtimeService = lagtimeService;
	}
	
	public FormService getFormService() {
		return formService;
	}
	
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LagTimeReportSetup.class, new LagTimeReportSetupEditor());
		binder.registerCustomEditor(Form.class, new PropertyEditorSupport() {
			
			@Override
			public void setAsText(String text) {
				Form form = formService.getForm(Integer.parseInt(text));
				super.setValue(form);
			}
		});
	}
	
	@RequestMapping(value = "/module/lagtimereport/addLagTimeReportSetup", method = RequestMethod.GET)
	public void showSetupLagTimeForm() {
		
	}
	
	@ModelAttribute("lagTimeTeportSetup")
	public LagTimeReportSetup getLagTimeReportSetup(
	        @RequestParam(value = "lagtimereportId", required = false) Integer lagtimereportId) {
		if (lagtimereportId != null) {
			LagTimeReportSetup lagTimeReportSetup = lagtimeService.getLagTimeReportSetup(lagtimereportId);
			
			return lagTimeReportSetup;
		}
		LagTimeReportSetup lagTimeReportSetup = new LagTimeReportSetup();
		
		return lagTimeReportSetup;
	}
	
	@ModelAttribute("listLagTimeReportForms")
	public List<Form> getAllFroms() {
		return formService.getPublishedForms();
		
	}
	
	@RequestMapping(value = "/module/lagtimereport/addLagTimeReportSetup", method = RequestMethod.POST)
	public String onSubmit(HttpServletRequest request,
	        @ModelAttribute("lagTimeTeportSetup") LagTimeReportSetup lagTimeReportSetup, BindingResult result)
	        throws Exception {
		
		HttpSession httpSession = request.getSession();
		
		//if (Context.isAuthenticated()) {
		ValidateUtil.validate(lagTimeReportSetup, result);
		
		/*		if (result.hasErrors()) {
					log.fatal(result);
					return null;
				}*/
		LagTimeReportSetup updateLagtimereport = new LagTimeReportSetup();
		double version = 0;
		double updateVersion = 0;
		
		DecimalFormat df = new DecimalFormat("#.#");
		if (lagTimeReportSetup.getLagTimeReportId() != null) {
			version = lagTimeReportSetup.getVersion() + 0.1;
			updateVersion = Double.parseDouble(df.format(version));
			updateLagtimereport.setName(lagTimeReportSetup.getName());
			updateLagtimereport.setDescription(lagTimeReportSetup.getDescription());
			updateLagtimereport.setForms(lagTimeReportSetup.getForms());
			updateLagtimereport.setVersion(updateVersion);
			lagTimeReportSetup.setRetired(true);
			lagtimeService.saveLagTimeReportSetup(updateLagtimereport);
		} else {
			lagtimeService.saveLagTimeReportSetup(lagTimeReportSetup);
		}
		
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "lagtimereport.saved");
		
		//}
		
		return "redirect:lagtimereportList.list";
	}
	
}
