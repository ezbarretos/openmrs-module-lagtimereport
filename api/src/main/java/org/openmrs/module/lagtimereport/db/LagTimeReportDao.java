/**
 * 
 */
package org.openmrs.module.lagtimereport.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.lagtimereport.LagTimeReport;

/**
 * @author ossemaeb
 */
public interface LagTimeReportDao {
	
	public LagTimeReport saveLagTimeReport(LagTimeReport lagTimeReport) throws APIException;
	
	public LagTimeReport updateLagTimeReport(LagTimeReport lagTimeReport) throws APIException;
	
	public List<LagTimeReport> getAllLagTimeReport() throws APIException;
	
	public List<LagTimeReport> getAllLagTimeReport(Boolean includeRetired) throws APIException;
	
	public LagTimeReport getLagTimeReport(Integer lagTimeReportById) throws APIException;
	
	public LagTimeReport getLagTimeReportByUuid(String uuid) throws APIException;
	
	public LagTimeReport getLagTimeReportByName(String name) throws APIException;
	
	public List<LagTimeReport> findLagTimeReport(String name) throws APIException;
	
	public void deleteLagTimeReport(LagTimeReport lagTimeReport) throws APIException;
	
}
