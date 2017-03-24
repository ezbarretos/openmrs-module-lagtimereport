/**
 * 
 */
package org.openmrs.module.lagtimereport.db;

import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;

/**
 * @author ossemaeb
 */
public interface LagTimeReportSetupDao {
	
	public LagTimeReportSetup saveLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws APIException;
	
	public LagTimeReportSetup updateLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws APIException;
	
	public List<LagTimeReportSetup> getAllLagTimeReportSetup() throws APIException;
	
	public List<LagTimeReportSetup> getAllLagTimeReportSetup(Boolean includeRetired) throws APIException;
	
	public LagTimeReportSetup getLagTimeReportSetup(Integer lagTimeReportById) throws APIException;
	
	public LagTimeReportSetup getLagTimeReportSetupByUuid(String uuid) throws APIException;
	
	public LagTimeReportSetup getLagTimeReportSetupByName(String name) throws APIException;
	
	public List<LagTimeReportSetup> findLagTimeReportSetup(String name) throws APIException;
	
	public void deleteLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws APIException;
	
}
