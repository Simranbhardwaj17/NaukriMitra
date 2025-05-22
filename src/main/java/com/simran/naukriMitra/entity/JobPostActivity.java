package com.simran.naukriMitra.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class JobPostActivity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobPostId;

	@ManyToOne
	@JoinColumn(name = "postedById", referencedColumnName = "userid")
	private Users postedById;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobLocationId", referencedColumnName = "id")
	private JobLocation jobLocationId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jobCompanyId", referencedColumnName = "id")
	private JobCompany jobCompanyId;
	
	@Transient
	private Boolean isActive;
	
	@Transient
	private Boolean isSaved;
	
	@Length(max = 10000)
	private String descriptionOfJob;
	
	private String jobType;
	private String salary;
	private String remote;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date postedDate;
	private String jobTitle;
	
	public JobPostActivity() {
	}

	public JobPostActivity(Integer jobPostId, Users postedById, JobLocation jobLocationId, JobCompany jobCompanyId,
			Boolean isActive, Boolean isSaved, @Length(max = 10000) String descriptionOfJob, String jobType,
			String salary, String remote, Date postedDate, String jobTitle) {
		this.jobPostId = jobPostId;
		this.postedById = postedById;
		this.jobLocationId = jobLocationId;
		this.jobCompanyId = jobCompanyId;
		this.isActive = isActive;
		this.isSaved = isSaved;
		this.descriptionOfJob = descriptionOfJob;
		this.jobType = jobType;
		this.salary = salary;
		this.remote = remote;
		this.postedDate = postedDate;
		this.jobTitle = jobTitle;
	}

	public Integer getJobPostId() {
		return jobPostId;
	}

	public void setJobPostId(Integer jobPostId) {
		this.jobPostId = jobPostId;
	}

	public Users getPostedById() {
		return postedById;
	}

	public void setPostedById(Users postedById) {
		this.postedById = postedById;
	}

	public JobLocation getJobLocationId() {
		return jobLocationId;
	}

	public void setJobLocationId(JobLocation jobLocationId) {
		this.jobLocationId = jobLocationId;
	}

	public JobCompany getJobCompanyId() {
		return jobCompanyId;
	}

	public void setJobCompanyId(JobCompany jobCompanyId) {
		this.jobCompanyId = jobCompanyId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}

	public String getDescriptionOfJob() {
		return descriptionOfJob;
	}

	public void setDescriptionOfJob(String descriptionOfJob) {
		this.descriptionOfJob = descriptionOfJob;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "JobPostActivity [jobPostId=" + jobPostId + ", postedById=" + postedById + ", jobLocationId="
				+ jobLocationId + ", jobCompanyId=" + jobCompanyId + ", isActive=" + isActive + ", isSaved=" + isSaved
				+ ", descriptionOfJob=" + descriptionOfJob + ", jobType=" + jobType + ", salary=" + salary + ", remote="
				+ remote + ", postedDate=" + postedDate + ", jobTitle=" + jobTitle + "]";
	}
}
