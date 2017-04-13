/**
 * 
 */
package org.openmrs.module.lagtimereport.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.db.LagTimeReportDao;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ossemaeb
 */
@Service
public class LagTimeReportServiceImpl extends BaseOpenmrsService implements LagTimeReportService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	LagTimeReportDao dao;
	
	@Autowired
	UserService userService;
	
	public LagTimeReportDao getDao() {
		return dao;
	}
	
	public void setDao(LagTimeReportDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public LagTimeReport getLagTimeReportByUuid(String uuid) throws APIException {
		return dao.getLagTimeReportByUuid(uuid);
	}
	
	@Override
	public LagTimeReport saveLagTimeReport(LagTimeReport lagTimeReport) throws APIException {
		return dao.saveLagTimeReport(lagTimeReport);
	}
	
	@Override
	public LagTimeReport getLagTimeReport(Integer lagTimeReportById) throws APIException {
		return dao.getLagTimeReport(lagTimeReportById);
	}
	
	@Override
	public LagTimeReport getLagTimeReport(String name) throws APIException {
		return dao.getLagTimeReportByName(name);
	}
	
	@Override
	public List<LagTimeReport> getAllLagTimeReports() throws APIException {
		return dao.getAllLagTimeReport(true);
	}
	
	@Override
	public List<LagTimeReport> getAllLagTimeReports(boolean includeRetired) throws APIException {
		return dao.getAllLagTimeReport(includeRetired);
	}
	
	@Override
	public List<LagTimeReport> findLagTimeReports(String name) throws APIException {
		return dao.findLagTimeReport(name);
	}
	
	@Override
	public LagTimeReport retireLagTimeReport(LagTimeReport lagTimeReport, String reason) throws APIException {
		if (reason == null)
			throw new IllegalArgumentException("The 'reason' argument is required");
		lagTimeReport.setVoided(true);
		lagTimeReport.setVoidReason(reason);
		lagTimeReport.setDateVoided(new Date());
		return saveLagTimeReport(lagTimeReport);
	}
	
	@Override
	public LagTimeReport unretireLagTimeReport(LagTimeReport lagTimeReport) throws APIException {
		lagTimeReport.setVoided(false);
		return saveLagTimeReport(lagTimeReport);
	}
	
	@Override
	public void purgeLagTimeReport(LagTimeReport lagTimeReport) throws APIException {
		dao.deleteLagTimeReport(lagTimeReport);
		
	}
	
}
