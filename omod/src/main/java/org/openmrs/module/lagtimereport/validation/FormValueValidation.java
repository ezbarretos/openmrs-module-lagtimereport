/**
 * 
 */
package org.openmrs.module.lagtimereport.validation;

import org.openmrs.module.lagtimereport.FormValue;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author ossemaeb
 */
public class FormValueValidation implements Validator {
	
	@Override
	public boolean supports(Class<?> c) {
		return c.equals(FormValue.class);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		FormValue formValue = (FormValue) obj;
		
		if (formValue == null) {
			errors.reject("formValue", "lagtimereport.error.general");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfForm", "lagtimereport.error.numberOfForm");
	}
	
}
