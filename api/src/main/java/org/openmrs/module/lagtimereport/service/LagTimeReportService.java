/**
 * 
 */
package org.openmrs.module.lagtimereport.service;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagTimeReportConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ossemaeb
 */
@Transactional
public interface LagTimeReportService extends OpenmrsService {
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_ADD_NEW_REPORT)
	public LagTimeReport saveLagTimeReport(LagTimeReport lagTimeReport) throws APIException;
	
	public LagTimeReport getLagTimeReport(Integer lagTimeReportId) throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	public LagTimeReport getLagTimeReportByUuid(String uuid) throws APIException;
	
	public LagTimeReport getLagTimeReport(String name) throws APIException;
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_VIEW_REPORT)
	public List<LagTimeReport> getAllLagTimeReports() throws APIException;
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_VIEW_REPORT)
	public List<LagTimeReport> getAllLagTimeReports(boolean includeRetired) throws APIException;
	
	public List<LagTimeReport> findLagTimeReports(String name) throws APIException;
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_REPORT_RETIRE)
	public LagTimeReport retireLagTimeReport(LagTimeReport lagTimeReport, String reason) throws APIException;
	
	public LagTimeReport unretireLagTimeReport(LagTimeReport lagTimeReport) throws APIException;
	
	public void purgeLagTimeReport(LagTimeReport lagTimeReport) throws APIException;
	
}
