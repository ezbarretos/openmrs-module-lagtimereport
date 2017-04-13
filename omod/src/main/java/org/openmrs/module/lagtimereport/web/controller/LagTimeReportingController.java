/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.propertyeditor.LagTimeReportEditor;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	}
	
	@RequestMapping(value = "/module/lagtimereport/lagtimeReporting", method = RequestMethod.GET)
	public void showForm() {
		
	}
	
	@ModelAttribute("printReport")
	public LagTimeReport getLagTimeReport(@RequestParam(value = "reportId", required = false) Integer reportId) {
		
		LagTimeReport lagTimeReport = reportService.getLagTimeReport(reportId);
		log.error(lagTimeReport.getId());
		log.error(lagTimeReport.getLagtimereportSetup().getName());
		log.error(lagTimeReport.getLagtimereportSetup().getForms());
		
		return lagTimeReport;
	}
	
	@ModelAttribute("printReports")
	public List<LagTimeReport> getAllLagTimeReports() {
		List<LagTimeReport> lagTimeReports = reportService.getAllLagTimeReports(false);
		return lagTimeReports;
	}
	
	//download PDF
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
