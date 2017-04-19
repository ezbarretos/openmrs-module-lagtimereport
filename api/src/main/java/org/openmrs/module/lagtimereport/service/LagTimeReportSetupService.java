/**
 * 
 */
package org.openmrs.module.lagtimereport.service;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.lagtimereport.LagTimeReportConfig;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ossemaeb
 */
@Transactional
public interface LagTimeReportSetupService extends OpenmrsService {
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_SETUP_REPORT)
	public LagTimeReportSetup saveLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup) throws APIException;
	
	public LagTimeReportSetup getLagTimeReportSetup(Integer lagTimeReportSetupId) throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	public LagTimeReportSetup getLagTimeReportSetupByUuid(String uuid) throws APIException;
	
	public LagTimeReportSetup getLagTimeReportSetup(String name) throws APIException;
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_VIEW_SETUP_REPORT)
	public List<LagTimeReportSetup> getAllLagTimeReportSetups() throws APIException;
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_VIEW_SETUP_REPORT)
	public List<LagTimeReportSetup> getAllLagTimeReportSetups(boolean includeRetired) throws APIException;
	
	public List<LagTimeReportSetup> findLagTimeReportSetups(String name) throws APIException;
	
	@Authorized(LagTimeReportConfig.PRIVILEGE_SETUP_RETIRE_REPORT)
	public LagTimeReportSetup retireLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup, String reason)
	        throws APIException;
	
	public LagTimeReportSetup unretireLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup) throws APIException;
	
	public void purgeLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup) throws APIException;
	
}
