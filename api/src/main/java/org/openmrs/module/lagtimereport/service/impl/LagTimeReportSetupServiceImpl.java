/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.lagtimereport.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.db.LagTimeReportSetupDao;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LagTimeReportSetupServiceImpl extends BaseOpenmrsService implements LagTimeReportSetupService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	LagTimeReportSetupDao dao;
	
	@Autowired
	UserService userService;
	
	public LagTimeReportSetupDao getDao() {
		return dao;
	}
	
	public void setDao(LagTimeReportSetupDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public LagTimeReportSetup getLagTimeReportSetupByUuid(String uuid) throws APIException {
		return dao.getLagTimeReportSetupByUuid(uuid);
	}
	
	@Override
	public LagTimeReportSetup saveLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws APIException {
		return dao.saveLagTimeReportSetup(lagTimeReport);
	}
	
	@Override
	public LagTimeReportSetup getLagTimeReportSetup(Integer lagTimeReportById) throws APIException {
		return dao.getLagTimeReportSetup(lagTimeReportById);
	}
	
	@Override
	public LagTimeReportSetup getLagTimeReportSetup(String name) throws APIException {
		return dao.getLagTimeReportSetupByName(name);
	}
	
	@Override
	public List<LagTimeReportSetup> getAllLagTimeReportSetups() throws APIException {
		return dao.getAllLagTimeReportSetup(true);
	}
	
	@Override
	public List<LagTimeReportSetup> getAllLagTimeReportSetups(boolean includeRetired) throws APIException {
		return dao.getAllLagTimeReportSetup(includeRetired);
	}
	
	@Override
	public List<LagTimeReportSetup> findLagTimeReportSetups(String name) throws APIException {
		return dao.findLagTimeReportSetup(name);
	}
	
	@Override
	public LagTimeReportSetup retireLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup, String reason)
	        throws APIException {
		if (reason == null)
			throw new IllegalArgumentException("The 'reason' argument is required");
		
		lagTimeReportSetup.setRetired(true);
		lagTimeReportSetup.setRetireReason(reason);
		lagTimeReportSetup.setDateRetired(new Date());
		return saveLagTimeReportSetup(lagTimeReportSetup);
	}
	
	@Override
	public LagTimeReportSetup unretireLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup) throws APIException {
		lagTimeReportSetup.setRetired(false);
		return saveLagTimeReportSetup(lagTimeReportSetup);
	}
	
	@Override
	public void purgeLagTimeReportSetup(LagTimeReportSetup lagTimeReportSetup) throws APIException {
		dao.deleteLagTimeReportSetup(lagTimeReportSetup);
		
	}
	
}
