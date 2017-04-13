/**
 * 
 */
package org.openmrs.module.lagtimereport;

import java.io.Serializable;

/**
 * @author ossemaeb
 */
public class FormValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer forms;
	
	private LagTimeReport LagTimeReport;
	
	private Integer numberOfForm;
	
	public Integer getForms() {
		return forms;
	}
	
	public void setForms(Integer forms) {
		this.forms = forms;
	}
	
	public Integer getNumberOfForm() {
		return numberOfForm;
	}
	
	public void setNumberOfForm(Integer numberOfForm) {
		this.numberOfForm = numberOfForm;
	}
	
	public LagTimeReport getLagTimeReport() {
		return LagTimeReport;
	}
	
	public void setLagTimeReport(LagTimeReport lagTimeReport) {
		LagTimeReport = lagTimeReport;
	}
	
}
