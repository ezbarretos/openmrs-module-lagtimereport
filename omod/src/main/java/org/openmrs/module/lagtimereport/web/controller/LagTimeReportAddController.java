/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Form;
import org.openmrs.api.FormService;
import org.openmrs.module.lagtimereport.FormValue;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.propertyeditor.LagTimeReportEditor;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@Autowired
	FormService formService;
	
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
	
	public FormService getFormService() {
		return formService;
	}
	
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LagTimeReport.class, new LagTimeReportEditor());
		binder.registerCustomEditor(LagTimeReportSetup.class, new PropertyEditorSupport() {
			
			@Override
			public void setAsText(String text) {
				LagTimeReportSetup lagtime = lagtimeService.getLagTimeReportSetup(Integer.parseInt(text));
				super.setValue(lagtime);
			}
		});
		
		binder.registerCustomEditor(Form.class, new PropertyEditorSupport() {
			
			@Override
			public void setAsText(String text) {
				Form form = formService.getForm(Integer.parseInt(text));
				super.setValue(form);
			}
		});
	}
	
	@RequestMapping(value = "/module/lagtimereport/addLagtimereport.form", method = RequestMethod.GET)
	public void onGet() {
		
	}
	
	@ModelAttribute("lagTimeReport")
	public LagTimeReport getLagTimeReport(@RequestParam(value = "lagtimereportId", required = false) Integer lagtimereportId) {
		if (lagtimereportId != null) {
			LagTimeReport lagTimeReport = reportService.getLagTimeReport(lagtimereportId);
			
			return lagTimeReport;
		}
		LagTimeReport lagTimeReport = new LagTimeReport();
		
		return lagTimeReport;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/lagtimereport/fetchForms")
	@ResponseBody
	public Object getLagTimeReportForms(@RequestParam Integer lagtimereportId) {
		LagTimeReportSetup lagTimeReport = lagtimeService.getLagTimeReportSetup(lagtimereportId);
		Map<Integer, Object> map = new HashedMap();
		for (Form form : lagTimeReport.getForms()) {
			map.put(form.getId(), form.getName());
		}
		return map;
	}
	
	@ModelAttribute("listLagTimeReportSetup")
	public List<LagTimeReportSetup> getAllLagtimeReportSetups() {
		return lagtimeService.getAllLagTimeReportSetups(false);
	}
	
	@RequestMapping(value = "/module/lagtimereport/addLagtimereport", method = RequestMethod.POST)
	public String onSubmit(HttpServletRequest request, @ModelAttribute("lagTimeReport") LagTimeReport lagTimeReport,
	        BindingResult result) throws Exception {
		
		HttpSession httpSession = request.getSession();
		//if (Context.isAuthenticated()) {
		ValidateUtil.validate(lagTimeReport, result);
		
		/*if (result.hasErrors()) {
			return null;
		}*/
		
		//Set c = new HashSet();
		Set<FormValue> formValues = new HashSet<FormValue>();
		
		String[] forms = request.getParameterValues("forms");
		String[] numberOfForm = request.getParameterValues("numberOfForm");
		for (int i = 0; i < forms.length; i++) {
			FormValue formValue = new FormValue();
			formValue.setForms(Integer.parseInt(forms[i]));
			if (numberOfForm[i].isEmpty())
				formValue.setNumberOfForm(0);
			formValue.setNumberOfForm(Integer.parseInt(numberOfForm[i]));
			formValues.add(formValue);
			lagTimeReport.setFormValue(formValues);
			reportService.saveLagTimeReport(lagTimeReport);
		}
		
		httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "lagtimereport.saved");
		
		//}
		
		return "redirect:manageLagtimereport.list";
	}
	
}
