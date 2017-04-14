/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.api.EncounterService;
import org.openmrs.module.lagtimereport.FormValue;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ossemaeb
 */
@Controller
public class LagTimeReportingController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	LagTimeReportService reportService;
	
	@Autowired
	LagTimeReportSetupService lagtimeService;
	
	@Autowired
	private EncounterService encounterService;
	
	public LagTimeReportService getReportService() {
		return reportService;
	}
	
	public void setReportService(LagTimeReportService reportService) {
		this.reportService = reportService;
	}
	
	public LagTimeReportSetupService getLagtimeService() {
		return lagtimeService;
	}
	
	public void setLagtimeService(LagTimeReportSetupService lagtimeService) {
		this.lagtimeService = lagtimeService;
	}
	
	public EncounterService getEncounterService() {
		return encounterService;
	}
	
	public void setEncounterService(EncounterService encounterService) {
		this.encounterService = encounterService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LagTimeReport.class, new PropertyEditorSupport() {
			
			@Override
			public void setAsText(String text) {
				LagTimeReport lagtime = reportService.getLagTimeReport(Integer.parseInt(text));
				super.setValue(lagtime);
			}
		});
		binder.registerCustomEditor(LagTimeReportSetup.class, new PropertyEditorSupport() {
			
			@Override
			public void setAsText(String text) {
				LagTimeReportSetup lagtime = lagtimeService.getLagTimeReportSetup(Integer.parseInt(text));
				super.setValue(lagtime);
			}
		});
		
		binder.registerCustomEditor(Encounter.class, new PropertyEditorSupport() {
			
			@Override
			public void setAsText(String text) {
				Encounter encounter = encounterService.getEncounter(Integer.parseInt(text));
				super.setValue(encounter);
			}
		});
	}
	
	@RequestMapping(value = "/module/lagtimereport/showLagtimeReporting", method = RequestMethod.GET)
	public String showForm() {
		return "redirect:lagtimeReporting.list";
	}
	
	//@ModelAttribute("printReport")
	public LagTimeReport getLagTimeReport(@RequestParam(value = "reportId", required = false) Integer reportId) {
		LagTimeReport lagTimeReport = reportService.getLagTimeReport(reportId);
		return lagTimeReport;
	}
	
	//@ModelAttribute("printEncounter")
	@RequestMapping(value = "/module/lagtimereport/lagtimeReporting", method = RequestMethod.GET)
	public ModelAndView getLagTimeReportingEncounter(@RequestParam(value = "reportId", required = false) Integer reportId) {
		
		LagTimeReport lagTimeReport = reportService.getLagTimeReport(reportId);
		
		List<Encounter> encounters = encounterService.getEncounters(null, null, lagTimeReport.getStartDate(),
		    lagTimeReport.getEndDate(), lagTimeReport.getLagtimereportSetup().getForms(), null, null, null, null, false);
		log.error("TEST " + encounters.size());
		Map<Integer, Integer> encounterCount = new HashMap<Integer, Integer>();
		for (Encounter encounter : encounters) {
			Integer count = encounterCount.get(encounter.getForm().getId());
			if (count == null) {
				count = 0;
			}
			encounterCount.put(encounter.getForm().getId(), count + 1);
		}
		
		ModelAndView view = new ModelAndView();
		view.addObject("printReport", lagTimeReport);
		view.addObject("printEncounter", encounterCount);
		
		return view;
	}
	
	@ModelAttribute("printReports")
	public List<LagTimeReport> getAllLagTimeReports() {
		List<LagTimeReport> lagTimeReports = reportService.getAllLagTimeReports(false);
		return lagTimeReports;
	}
	
	// download PDF
	@RequestMapping(value = "/module/lagtimereport/lagtimeReporting", method = RequestMethod.POST)
	public String retireLagTimeReportSetup(HttpServletRequest request, ModelMap model) {
		String reason = request.getParameter("reason");
		if (request.getParameterValues("checkRetire") != null) {
			for (String id : request.getParameterValues("checkRetire")) {
				LagTimeReportSetup lagTimeReport = lagtimeService.getLagTimeReportSetup(Integer.parseInt(id));
				lagtimeService.retireLagTimeReportSetup(lagTimeReport, reason);
			}
		}
		return "redirect:lagtimeReporting.list";
	}
	
}
