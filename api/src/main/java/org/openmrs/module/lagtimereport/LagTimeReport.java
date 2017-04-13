/**
 * 
 */
package org.openmrs.module.lagtimereport;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.openmrs.BaseOpenmrsData;

/**
 * @author ossemaeb
 */
public class LagTimeReport extends BaseOpenmrsData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer reportId;
	
	private Date dueDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private LagTimeReportSetup lagtimereportSetup;
	
	//private Collection<Integer> forms;
	
	//private Collection<Integer> numberOfForm;
	
	private Collection<FormValue> formValue = new HashSet<FormValue>();
	
	public Integer getReportId() {
		return reportId;
	}
	
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	
	@Override
	public Integer getId() {
		
		return getReportId();
	}
	
	@Override
	public void setId(Integer reportId) {
		setReportId(reportId);
		
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public LagTimeReportSetup getLagtimereportSetup() {
		return lagtimereportSetup;
	}
	
	public void setLagtimereportSetup(LagTimeReportSetup lagtimereportSetup) {
		this.lagtimereportSetup = lagtimereportSetup;
	}
	
	public Collection<FormValue> getFormValue() {
		return formValue;
	}
	
	public void setFormValue(Collection<FormValue> formValue) {
		this.formValue = formValue;
	}
	
}
