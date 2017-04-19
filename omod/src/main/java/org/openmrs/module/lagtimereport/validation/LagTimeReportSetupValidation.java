/**
 * 
 */
package org.openmrs.module.lagtimereport.validation;

import java.util.Collection;

import org.openmrs.Form;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.validator.FormValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author ossemaeb
 */
public class LagTimeReportSetupValidation implements Validator {
	
	private FormValidator formValidator;
	
	public void setFormValidator(FormValidator formValidator) {
		this.formValidator = formValidator;
	}
	
	@Override
	public boolean supports(Class<?> c) {
		return c.equals(LagTimeReportSetup.class);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		LagTimeReportSetup lagtimereport = (LagTimeReportSetup) obj;
		
		if (lagtimereport == null) {
			errors.reject("lagTimeReportSetup", "lagtimereport.error.general");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "lagtimereport.error.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "lagtimereport.error.description");
		
		Collection<Form> allForms = lagtimereport.getForms();
		if (allForms == null || allForms.size() == 0) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "forms", "lagtimereport.error.forms");
		} else {
			try {
				for (Form form : allForms) {
					ValidationUtils.invokeValidator(formValidator, form, errors);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
