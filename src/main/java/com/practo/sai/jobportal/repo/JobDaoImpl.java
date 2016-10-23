package com.practo.sai.jobportal.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.practo.sai.jobportal.entities.Job;
import com.practo.sai.jobportal.entities.JobApplication;
import com.practo.sai.jobportal.model.Filter;
import com.practo.sai.jobportal.model.PageableJobs;
import com.practo.sai.jobportal.utility.Logger;

/**
 * DAO Implementation that performs CRUD operations on Job Entity
 * 
 * @author saichandrasekhardandu
 *
 */
@Repository
@Transactional
public class JobDaoImpl implements JobDao {

	private static final Logger LOG = Logger.getInstance(JobDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public int save(Job job) {
		getSession().save(job);
		return job.getJId();
	}

	@Override
	public Job getJob(int jobId) {
		return getSession().get(Job.class, jobId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageableJobs getJobsNewForEmployee(int eId, int perpage, int pageno, Filter filter)
			throws JDBCConnectionException {
		// Query query = getSession().createQuery(
		// "from Job where JId NOT IN (Select job.JId from JobApplication where
		// employee.EId = :eId)");
		// query.setParameter("eId", eId);
		// Get the total number of items for pagination purpose
		DetachedCriteria application = DetachedCriteria.forClass(JobApplication.class)
				.setProjection(Property.forName("job.JId")).createCriteria("employee").add(Restrictions.eq("EId", eId));
		DetachedCriteria jobCriteria = DetachedCriteria.forClass(Job.class, "j");
		addFilter(jobCriteria, filter);
		jobCriteria.add(Property.forName("JId").notIn(application));
		jobCriteria.setProjection(Projections.rowCount());

		Long count = (Long) jobCriteria.getExecutableCriteria(getSession()).uniqueResult();
		int totalPages = (int) Math.ceil(((double) count) / perpage);

		application = DetachedCriteria.forClass(JobApplication.class).setProjection(Property.forName("job.JId"))
				.createCriteria("employee").add(Restrictions.eq("EId", eId));
		jobCriteria = DetachedCriteria.forClass(Job.class, "j");
		addFilter(jobCriteria, filter);
		jobCriteria.add(Property.forName("JId").notIn(application));
		List<Job> jobs = jobCriteria.getExecutableCriteria(getSession()).addOrder(Order.desc("postedOn"))
				.setFirstResult((pageno - 1) * perpage).setMaxResults(perpage).list();

		for (Job job : jobs) {
			LOG.debug("jobId - " + job.getJId());
		}

		return new PageableJobs(totalPages, jobs);

	}

	@SuppressWarnings("unchecked")
	@Override
	public PageableJobs getJobsByAdmin(int eId, int perpage, int pageno) throws JDBCConnectionException {
		// return getSession().createQuery("from Job").list();
		DetachedCriteria criteria = DetachedCriteria.forClass(Job.class);

		DetachedCriteria employeeCriteria = criteria.createCriteria("employeeByPostedBy");
		employeeCriteria.add(Restrictions.eq("EId", eId));
		Criteria executableCriteria = employeeCriteria.getExecutableCriteria(getSession());
		executableCriteria.setProjection(Projections.rowCount());
		Long count = (Long) executableCriteria.uniqueResult();
		int totalPages = (int) Math.ceil(((double) count) / perpage);

		criteria = DetachedCriteria.forClass(Job.class);
		employeeCriteria = criteria.createCriteria("employeeByPostedBy");
		executableCriteria = employeeCriteria.add(Restrictions.eq("EId", eId)).getExecutableCriteria(getSession())
				.addOrder(Order.desc("postedOn")).setFirstResult((pageno - 1) * perpage).setMaxResults(perpage);

		List<Job> jobs = executableCriteria.list();

		for (Job job : jobs) {
			LOG.debug("jobId - " + job.getJId());
		}

		return new PageableJobs(totalPages, jobs);
	}

	@Override
	public void update(Job job) {
		getSession().update(job);
	}

	@Override
	public void delete(Job job) {
		getSession().delete(job);
	}

	private void addFilter(DetachedCriteria criteria, Filter filter) {
		if (filter.getCategoryId() != null && filter.getCategoryId() != -1) {
			criteria.createCriteria("category").add(Restrictions.eq("CId", filter.getCategoryId()));
		}

		if (filter.getTeamId() != null && filter.getTeamId() != -1)
			criteria.createCriteria("team", "t").add(Restrictions.eq("t.id", filter.getTeamId()));

	}

}
