package com.simran.naukriMitra.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class JobCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	private String name;
	private String logo;
	
	public JobCompany() {
	}

	public JobCompany(Integer id, String name, String logo) {
		Id = id;
		this.name = name;
		this.logo = logo;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "JobCompany [Id=" + Id + ", name=" + name + ", logo=" + logo + "]";
	}
	
}