/**
 * 
 */
package org.openmrs.module.lagtimereport;

import java.io.Serializable;

/**
 * @author ossemaeb
 */
public class LagtimeReporting implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String forms;
	
	private Integer formValue;
	
	private Integer countEncounter;
	
	private Double percentageEntered;
	
	private Integer averageDays;
	
	private Integer medianDays;
	
	public LagtimeReporting(String forms, Integer formValue, Integer countEncounter, Double percentageEntered,
	    Integer averageDays, Integer medianDays) {
		super();
		this.forms = forms;
		this.formValue = formValue;
		this.countEncounter = countEncounter;
		this.percentageEntered = percentageEntered;
		this.averageDays = averageDays;
		this.medianDays = medianDays;
	}
	
	public String getForms() {
		return forms;
	}
	
	public void setForms(String forms) {
		this.forms = forms;
	}
	
	public Integer getFormValue() {
		return formValue;
	}
	
	public void setFormValue(Integer formValue) {
		this.formValue = formValue;
	}
	
	public Integer getCountEncounter() {
		return countEncounter;
	}
	
	public void setCountEncounter(Integer countEncounter) {
		this.countEncounter = countEncounter;
	}
	
	public Double getPercentageEntered() {
		return percentageEntered;
	}
	
	public void setPercentageEntered(Double percentageEntered) {
		this.percentageEntered = percentageEntered;
	}
	
	public Integer getAverageDays() {
		return averageDays;
	}
	
	public void setAverageDays(Integer averageDays) {
		this.averageDays = averageDays;
	}
	
	public Integer getMedianDays() {
		return medianDays;
	}
	
	public void setMedianDays(Integer medianDays) {
		this.medianDays = medianDays;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averageDays == null) ? 0 : averageDays.hashCode());
		result = prime * result + ((countEncounter == null) ? 0 : countEncounter.hashCode());
		result = prime * result + ((formValue == null) ? 0 : formValue.hashCode());
		result = prime * result + ((forms == null) ? 0 : forms.hashCode());
		result = prime * result + ((medianDays == null) ? 0 : medianDays.hashCode());
		result = prime * result + ((percentageEntered == null) ? 0 : percentageEntered.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LagtimeReporting other = (LagtimeReporting) obj;
		if (averageDays == null) {
			if (other.averageDays != null)
				return false;
		} else if (!averageDays.equals(other.averageDays))
			return false;
		if (countEncounter == null) {
			if (other.countEncounter != null)
				return false;
		} else if (!countEncounter.equals(other.countEncounter))
			return false;
		if (formValue == null) {
			if (other.formValue != null)
				return false;
		} else if (!formValue.equals(other.formValue))
			return false;
		if (forms == null) {
			if (other.forms != null)
				return false;
		} else if (!forms.equals(other.forms))
			return false;
		if (medianDays == null) {
			if (other.medianDays != null)
				return false;
		} else if (!medianDays.equals(other.medianDays))
			return false;
		if (percentageEntered == null) {
			if (other.percentageEntered != null)
				return false;
		} else if (!percentageEntered.equals(other.percentageEntered))
			return false;
		return true;
	}
	
}
