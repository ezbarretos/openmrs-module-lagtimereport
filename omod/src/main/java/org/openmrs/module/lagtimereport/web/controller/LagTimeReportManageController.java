/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ossemaeb
 */
@Controller
public class LagTimeReportManageController {
	
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
	
	@RequestMapping(value = "/module/lagtimereport/manageLagtimereport.list", method = RequestMethod.GET)
	public void showForm() {
		
	}
	
	@ModelAttribute("listReports")
	public List<LagTimeReport> getAllLagTimeReports() {
		List<LagTimeReport> lagTimeReports = reportService.getAllLagTimeReports(false);
		return lagTimeReports;
	}
	
	@ModelAttribute("retiredReports")
	public List<LagTimeReport> getAllRetiredLagTimeReports() {
		List<LagTimeReport> retiredLagTimeReports = new ArrayList<LagTimeReport>();
		retiredLagTimeReports = reportService.getAllLagTimeReports(true);
		return retiredLagTimeReports;
	}
	
	@RequestMapping(value = "/module/lagtimereport/manageLagtimereport.list", method = RequestMethod.POST)
	public String retireLagTimeReport(HttpServletRequest request, ModelMap model) {
		String reason = request.getParameter("reason");
		if (request.getParameterValues("checkRetire") != null) {
			for (String id : request.getParameterValues("checkRetire")) {
				LagTimeReport lagTimeReport = reportService.getLagTimeReport(Integer.parseInt(id));
				reportService.retireLagTimeReport(lagTimeReport, reason);
			}
		}
		return "redirect:manageLagtimereport.list";
	}
	
}
