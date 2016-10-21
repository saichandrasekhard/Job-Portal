package com.practo.sai.jobportal.service;

import java.util.List;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.sai.jobportal.entities.Category;
import com.practo.sai.jobportal.entities.Employee;
import com.practo.sai.jobportal.entities.Job;
import com.practo.sai.jobportal.entities.JobApplication;
import com.practo.sai.jobportal.entities.Team;
import com.practo.sai.jobportal.model.AddJobAppModel;
import com.practo.sai.jobportal.model.AddJobModel;
import com.practo.sai.jobportal.model.JobApplicationModel;
import com.practo.sai.jobportal.model.JobModel;
import com.practo.sai.jobportal.model.TeamModel;
import com.practo.sai.jobportal.model.UpdateJobModel;
import com.practo.sai.jobportal.repo.CategoryDao;
import com.practo.sai.jobportal.repo.JobApplicationDao;
import com.practo.sai.jobportal.repo.JobDao;
import com.practo.sai.jobportal.repo.TeamDao;
import com.practo.sai.jobportal.utility.Logger;
import com.practo.sai.jobportal.utility.MappingUtility;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger LOG = Logger.getInstance(JobServiceImpl.class);

	@Autowired
	MappingUtility mUtility;

	@Autowired
	JobDao jobDao;

	@Autowired
	JobApplicationDao jobApplicationDao;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	TeamDao teamDao;

	@Override
	public List<JobModel> getJobs() throws JDBCConnectionException {
		LOG.info("Servicing request for all jobs");
		List<Job> jobs = (List<Job>) jobDao.getJobs();
		List<JobModel> jobModels = mUtility.mapToJobModels(jobs);
		LOG.info("Response prepared for user");
		return jobModels;

	}

	@Override
	public JobModel addJob(AddJobModel jobModel) throws BadRequestException {
		if (jobModel.getCategoryId() <= 0 || jobModel.getDescription() == null || jobModel.getDescription().equals("")
				|| jobModel.getPostedBy() <= 0 || jobModel.getTeamId() <= 0)
			throw new BadRequestException("Required parameters are either missing or invalid");
		LOG.debug("All the required parameters are present");
		Job job = mUtility.mapFromAddJob(jobModel);
		jobDao.save(job);
		LOG.info("Job added to database succesfully");
		return mUtility.mapToJobModel(job);
	}

	@Override
	public JobModel updateJob(int jobId, UpdateJobModel jobModel) throws BadRequestException, NotFoundException {
		if (jobId <= 0 || jobModel == null)
			throw new BadRequestException("Required parameters are either missing or invalid");
		LOG.info("Processing request for updating job - " + jobId);
		Job job = jobDao.getJob(jobId);
		if (job == null)
			throw new NotFoundException("Requested Job doesn't exist");
		mUtility.mapFromUpdateJob(jobId, jobModel, job);
		jobDao.update(job);
		LOG.info("Update Job processed. Mapping response");
		return mUtility.mapToJobModel(job);
	}

	@Override
	public void deleteJob(int jobId) throws BadRequestException {
		if (jobId <= 0)
			throw new BadRequestException("Required parameters are either missing or invalid");
		LOG.info("Processing request for deleting job - " + jobId);
		Job job = new Job();
		job.setJId(jobId);
		jobDao.delete(job);
	}

	@Override
	public List<JobApplicationModel> getJobApplications(int jobId) throws BadRequestException {
		if (jobId <= 0)
			throw new BadRequestException("Required parameters are either missing or invalid");

		List<JobApplication> jobApplications;
		Job job = new Job();
		job.setJId(jobId);
		jobApplications = jobApplicationDao.getApplications(job);
		LOG.debug("Applications fetched from database - " + jobApplications.size());
		return mUtility.mapToJobAppModels(jobApplications);
	}

	@Override
	public JobApplicationModel addJobApplication(int jobId, AddJobAppModel jobApp) throws BadRequestException {
		if (jobApp.getAppliedBy() <= 0 || jobId <= 0)
			throw new BadRequestException("Required parameters are either missing or invalid");
		JobApplication application = mUtility.mapFromAddJobAppModel(jobId, jobApp);
		jobApplicationDao.save(application);
		LOG.info("Application added to database succefully");
		application = jobApplicationDao.getApplication(application.getJAppId());
		return mUtility.mapToJobAppModel(application);
	}

	@Override
	public void deleteJobApplication(int appId) throws BadRequestException {
		if (appId <= 0)
			throw new BadRequestException("Required parameters are either missing or invalid");
		JobApplication application = new JobApplication();
		application.setJAppId(appId);
		jobApplicationDao.delete(application);

	}

	@Override
	public List<Category> getCategories() {
		List<Category> categories = null;
		categories = categoryDao.getCategories();
		return categories;
	}

	public Employee getEmployee(String emailId) {
		Employee employee = null;
		// employee = employeeRepo.findByEmailId(emailId);
		return employee;
	}

	@Override
	public List<TeamModel> getTeams() {
		List<Team> teams = teamDao.getAll();
		return mUtility.mapToTeamModels(teams);
	}

}
