package com.practo.sai.jobportal.service;

import java.util.List;

import org.hibernate.exception.JDBCConnectionException;

import com.practo.sai.jobportal.model.AddJobAppModel;
import com.practo.sai.jobportal.model.AddJobModel;
import com.practo.sai.jobportal.model.Filter;
import com.practo.sai.jobportal.model.JobApplicationModel;
import com.practo.sai.jobportal.model.JobModel;
import com.practo.sai.jobportal.model.PageableJobs;
import com.practo.sai.jobportal.model.UpdateJobModel;

import inti.ws.spring.exception.client.BadRequestException;
import inti.ws.spring.exception.client.NotFoundException;

public interface JobService {

  public PageableJobs getJobs(int eId, int perpage, int pageno, Filter filter)
      throws JDBCConnectionException;

  public JobModel addJob(AddJobModel job) throws BadRequestException;

  public JobModel updateJob(int jobId, UpdateJobModel jobModel)
      throws BadRequestException, NotFoundException;

  public void deleteJob(int jobId) throws BadRequestException;

  public List<JobApplicationModel> getJobApplications(int jobId) throws BadRequestException;

  public List<JobApplicationModel> getMyJobApplications(int eId) throws BadRequestException;

  public JobApplicationModel addJobApplication(int jobId, AddJobAppModel jobApp)
      throws BadRequestException;

  public void deleteJobApplication(int appId) throws BadRequestException;

}
