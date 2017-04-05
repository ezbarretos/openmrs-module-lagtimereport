/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ossemaeb
 */
@Controller
public class LagTimeReportAddController {
	
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
	
	@RequestMapping(value = "/module/lagtimereport/addLagtimereport.form", method = RequestMethod.GET)
	public void onGet() {
		
	}
	
	@ModelAttribute("lagTimeTeport")
	public LagTimeReport getLagTimeReport(@RequestParam(value = "lagtimereportId", required = false) Integer reportId) {
		if (reportId != null) {
			LagTimeReport lagTimeReport = reportService.getLagTimeReport(reportId);
			
			return lagTimeReport;
		}
		LagTimeReport lagTimeReport = new LagTimeReport();
		
		return lagTimeReport;
	}
	
	@ModelAttribute("listLagTimeReportSetup")
	public List<LagTimeReportSetup> getAllLagtimeReportSetups() {
		return lagtimeService.getAllLagTimeReportSetups(false);
	}
	
}
