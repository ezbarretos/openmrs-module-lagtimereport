/**
 * 
 */
package org.openmrs.module.lagtimereport;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
	
	private LagTimeReportSetup lagTimeReportSetup;
	
	private Set<Integer> numberOfPaper;
	
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
	
	public LagTimeReportSetup getLagTimeReportSetup() {
		return lagTimeReportSetup;
	}
	
	public void setLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup) {
		this.lagTimeReportSetup = lagTimeReportSetup;
	}
	
	public Set<Integer> getNumberOfPaper() {
		return numberOfPaper;
	}
	
	public void setNumberOfPaper(Set<Integer> numberOfPaper) {
		this.numberOfPaper = numberOfPaper;
	}
	
}
