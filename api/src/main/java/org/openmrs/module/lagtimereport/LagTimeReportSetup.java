package org.openmrs.module.lagtimereport;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsMetadata;

public class LagTimeReportSetup extends BaseOpenmrsMetadata implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer lagTimeReportId;
	
	private Double version = 1.0;
	
	public LagTimeReportSetup() {
	}
	
	public LagTimeReportSetup(Integer lagTimeReportId, Double version) {
		super();
		this.lagTimeReportId = lagTimeReportId;
		this.version = version;
	}
	
	public LagTimeReportSetup(String name, String description) {
		setName(name);
		setDescription(description);
	}
	
	public Integer getLagTimeReportId() {
		return lagTimeReportId;
	}
	
	public void setLagTimeReportId(Integer lagTimeReportId) {
		this.lagTimeReportId = lagTimeReportId;
	}
	
	public Double getVersion() {
		return version;
	}
	
	public void setVersion(Double version) {
		this.version = version;
	}
	
	@Override
	public Integer getId() {
		return getLagTimeReportId();
	}
	
	@Override
	public void setId(Integer lagTimeReportId) {
		setLagTimeReportId(lagTimeReportId);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((lagTimeReportId == null) ? 0 : lagTimeReportId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LagTimeReportSetup other = (LagTimeReportSetup) obj;
		if (lagTimeReportId == null) {
			if (other.lagTimeReportId != null)
				return false;
		} else if (!lagTimeReportId.equals(other.lagTimeReportId))
			return false;
		return true;
	}
	
}
