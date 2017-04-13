/**
 * 
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
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.db.LagTimeReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author ossemaeb
 */
@Repository("lagtimereport.LagTimeReportDao")
public class HibernateLagTimeReport implements LagTimeReportDao {
	
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
	public LagTimeReport saveLagTimeReport(LagTimeReport lagTimeReport) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(lagTimeReport);
		return lagTimeReport;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LagTimeReport> getAllLagTimeReport() throws DAOException {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(LagTimeReport.class);
		//c.addOrder(Order.asc("name"));
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LagTimeReport> getAllLagTimeReport(Boolean includeRetired) throws DAOException {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(LagTimeReport.class);
		//c.addOrder(Order.asc("name"));
		if (!includeRetired)
			c.add(Restrictions.eq("voided", false));
		return c.list();
	}
	
	@Override
	public LagTimeReport getLagTimeReport(Integer lagTimeReportById) throws DAOException {
		return (LagTimeReport) sessionFactory.getCurrentSession().get(LagTimeReport.class, lagTimeReportById);
	}
	
	@Override
	public LagTimeReport getLagTimeReportByUuid(String uuid) throws APIException {
		return (LagTimeReport) sessionFactory.getCurrentSession().createCriteria(LagTimeReport.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	@Override
	public LagTimeReport updateLagTimeReport(LagTimeReport lagTimeReport) throws APIException {
		sessionFactory.getCurrentSession().update(lagTimeReport);
		return lagTimeReport;
	}
	
	@Override
	public LagTimeReport getLagTimeReportByName(String name) throws APIException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(LagTimeReport.class);
		crit.add(Restrictions.eq("voided", false));
		//crit.add(Restrictions.eq("name", name));
		LagTimeReport lagTimeReport = (LagTimeReport) crit.uniqueResult();
		
		return lagTimeReport;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LagTimeReport> findLagTimeReport(String name) throws APIException {
		return sessionFactory.getCurrentSession().createCriteria(LagTimeReport.class)
		        .add(Restrictions.ilike("name", name, MatchMode.START)).addOrder(Order.asc("name"))
		        .addOrder(Order.asc("voided")).list();
	}
	
	@Override
	public void deleteLagTimeReport(LagTimeReport lagTimeReport) throws APIException {
		sessionFactory.getCurrentSession().delete(lagTimeReport);
		
	}
	
}
