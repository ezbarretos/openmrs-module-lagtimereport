/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.module.lagtimereport.FormValue;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.LagtimeReporting;
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
	
	@Autowired
	private FormService formService;
	
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
	
	public FormService getFormService() {
		return formService;
	}
	
	public void setFormService(FormService formService) {
		this.formService = formService;
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
	
	@RequestMapping(value = "/module/lagtimereport/lagtimeReporting", method = RequestMethod.GET)
	public ModelAndView getLagTimeReportingEncounter(@RequestParam(value = "reportId", required = false) Integer reportId) {
		
		LagTimeReport lagTimeReport = reportService.getLagTimeReport(reportId);
		
		List<Encounter> encounters = encounterService.getEncounters(null, null, lagTimeReport.getStartDate(),
		    lagTimeReport.getEndDate(), lagTimeReport.getLagtimereportSetup().getForms(), null, null, null, null, false);
		
		Map<Integer, Integer> encounterCount = new HashMap<Integer, Integer>();
		Map<Integer, Integer> countDays = new HashMap<Integer, Integer>();
		int sumDays = 0;
		for (Encounter encounter : encounters) {
			Integer count = encounterCount.get(encounter.getForm().getId());
			if (count == null) {
				count = 0;
			}
			
			encounterCount.put(encounter.getForm().getId(), count + 1);
			
			long diff = encounter.getDateCreated().getTime() - encounter.getEncounterDatetime().getTime();
			int convertToDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			sumDays += convertToDays;
			countDays.put(encounter.getForm().getId(), sumDays);
		}
		
		List<FormValue> formValues = new ArrayList<FormValue>();
		for (FormValue formValue : lagTimeReport.getFormValue()) {
			formValues.add(formValue);
		}
		
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < lagTimeReport.getLagtimereportSetup().getForms().size(); i++) {
			
			int formId = formValues.get(i).getForms();
			if (encounterCount.containsKey(formId)) {
				Form form = formService.getForm(formId);
				int formValue = formValues.get(i).getNumberOfForm();
				
				DecimalFormat df = new DecimalFormat("#.#");
				Double percentageEntered = Double
				        .parseDouble(df.format(((double) encounterCount.get(formId) / (double) formValue) * 100));
				
				Integer averageDays = countDays.get(formId) / encounterCount.get(formId);
				
				LagtimeReporting reporting = new LagtimeReporting(form.getName(), formValue, encounterCount.get(formId),
				        percentageEntered, averageDays, sumDays);
				list.add(reporting);
			}
			
		}
		
		ModelAndView view = new ModelAndView();
		view.addObject("printReport", lagTimeReport);
		view.addObject("reporting", list);
		
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
