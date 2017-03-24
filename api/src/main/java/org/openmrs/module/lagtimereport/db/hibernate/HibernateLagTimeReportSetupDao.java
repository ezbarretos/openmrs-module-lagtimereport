/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.lagtimereport.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.lagtimereport.LagTimeReportSetup;
import org.openmrs.module.lagtimereport.db.LagTimeReportSetupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("lagtimereport.LagTimeReportDao")
public class HibernateLagTimeReportSetupDao implements LagTimeReportSetupDao {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Override
	public LagTimeReportSetup saveLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(lagTimeReport);
		return lagTimeReport;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LagTimeReportSetup> getAllLagTimeReportSetup() throws DAOException {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(LagTimeReportSetup.class);
		c.addOrder(Order.asc("name"));
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LagTimeReportSetup> getAllLagTimeReportSetup(Boolean includeRetired) throws DAOException {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(LagTimeReportSetup.class);
		c.addOrder(Order.asc("name"));
		if (!includeRetired)
			c.add(Restrictions.eq("retired", false));
		return c.list();
	}
	
	@Override
	public LagTimeReportSetup getLagTimeReportSetup(Integer lagTimeReportById) throws DAOException {
		return (LagTimeReportSetup) sessionFactory.getCurrentSession().get(LagTimeReportSetup.class, lagTimeReportById);
	}
	
	@Override
	public LagTimeReportSetup getLagTimeReportSetupByUuid(String uuid) throws APIException {
		return (LagTimeReportSetup) sessionFactory.getCurrentSession().createCriteria(LagTimeReportSetup.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	@Override
	public LagTimeReportSetup updateLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws APIException {
		sessionFactory.getCurrentSession().update(lagTimeReport);
		return lagTimeReport;
	}
	
	@Override
	public LagTimeReportSetup getLagTimeReportSetupByName(String name) throws APIException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(LagTimeReportSetup.class);
		crit.add(Restrictions.eq("retired", false));
		crit.add(Restrictions.eq("name", name));
		LagTimeReportSetup agTimeReportSetup = (LagTimeReportSetup) crit.uniqueResult();
		
		return agTimeReportSetup;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LagTimeReportSetup> findLagTimeReportSetup(String name) throws APIException {
		return sessionFactory.getCurrentSession().createCriteria(LagTimeReportSetup.class)
		        .add(Restrictions.ilike("name", name, MatchMode.START)).addOrder(Order.asc("name"))
		        .addOrder(Order.asc("retired")).list();
	}
	
	@Override
	public void deleteLagTimeReportSetup(LagTimeReportSetup lagTimeReport) throws APIException {
		sessionFactory.getCurrentSession().delete(lagTimeReport);
		
	}
}
