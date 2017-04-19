/**
 * 
 */
package org.openmrs.module.lagtimereport.validation;

import java.util.Collection;

import org.openmrs.module.lagtimereport.FormValue;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author ossemaeb
 */
public class LagTimeReportValidation implements Validator {
	
	private FormValueValidation valueValidator;
	
	public void setValueValidator(FormValueValidation valueValidator) {
		this.valueValidator = valueValidator;
	}
	
	@Override
	public boolean supports(Class<?> c) {
		return c.equals(LagTimeReport.class);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		LagTimeReport lagtimereport = (LagTimeReport) obj;
		
		if (lagtimereport == null) {
			errors.reject("lagTimeReport", "lagtimereport.error.general");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dueDate", "lagtimereport.error.dueDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "lagtimereport.error.startDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "lagtimereport.error.endDate");
		
		LagTimeReportSetup lagTimeReportSetup = lagtimereport.getLagtimereportSetup();
		if (lagTimeReportSetup == null)
			errors.rejectValue("lagTimeReportSetup", "lagtimereport.error.lagtimereportsetup");
		
		Collection<FormValue> formValues = lagtimereport.getFormValue();
		if (formValues == null || formValues.size() == 0) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "formValue", "lagtimereport.error.form.value");
		} else {
			try {
				for (FormValue formValue : formValues) {
					ValidationUtils.invokeValidator(valueValidator, formValue, errors);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
