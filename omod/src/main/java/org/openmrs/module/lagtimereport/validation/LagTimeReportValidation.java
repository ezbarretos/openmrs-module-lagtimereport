/**
 * 
 */
package org.openmrs.module.lagtimereport.validation;

import org.openmrs.module.lagtimereport.LagTimeReport;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author ossemaeb
 */
public class LagTimeReportValidation implements Validator {
	
	@Override
	public boolean supports(Class<?> c) {
		return c.equals(LagTimeReport.class);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		LagTimeReport lagtimereport = (LagTimeReport) obj;
		
		if (lagtimereport == null) {
			errors.reject("lagTimeTeport", "lagtimereport.error.general");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dueDate", "lagtimereport.error.dueDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "lagtimereport.error.startDate");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "lagtimereport.error.endDate");
		
	}
	
}
