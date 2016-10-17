package com.practo.sai.jobportal.model;

import java.util.Date;

import com.practo.sai.jobportal.entities.Category;
import com.practo.sai.jobportal.entities.Employee;

public class JobModel {
	private Integer jId;
	private Category category;
	private Employee postedBy;
	private Employee recruitId;
	private String description;
	private boolean isClosed;
	private Date lastModified;
	private Date postedOn;

	public Integer getjId() {
		return jId;
	}

	public void setjId(Integer jId) {
		this.jId = jId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Employee getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(Employee postedBy) {
		this.postedBy = postedBy;
	}

	public Employee getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(Employee recruitId) {
		this.recruitId = recruitId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
}