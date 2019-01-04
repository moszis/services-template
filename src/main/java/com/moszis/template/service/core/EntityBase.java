package com.moszis.template.service.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.moszis.template.service.core.util.LocalContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@MappedSuperclass
public abstract class EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The Selector fields.
	 */
	@Transient
	protected List<String> selectorFields = new ArrayList<>();
	
	@Column(name = "VERSION")
	@Version
	@NotNull
	private Long version;
	
	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@Column(name = "LAST_MODIFIED_DT")
	@NotNull
	private Date lastModifiedDt;

	/**
	 * Instantiates a new Version base.
	 */
	public EntityBase() {
		super();
	}

	/**
	 * On pre persist.
	 */
	@PrePersist
	public void onPrePersist() {
		lastModifiedDt = new Date();
		lastModifiedBy = LocalContext.getAuthContext().getUsername();
	}

	/**
	 * On pre update.
	 */
	@PreUpdate
	public void onPreUpdate() {
		lastModifiedDt = new Date();
		lastModifiedBy = LocalContext.getAuthContext().getUsername();
	}

	/**
	 * Gets version.
	 *
	 * @return the version
	 */
	public Long getVersion() {
		return this.version;
	}

	/**
	 * Sets version.
	 *
	 * @param version the version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Gets last modified by.
	 *
	 * @return the last modified by
	 */
	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	/**
	 * Sets last modified by.
	 *
	 * @param lastModifiedBy the last modified by
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets last modified dt.
	 *
	 * @return the last modified dt
	 */
	public Date getLastModifiedDt() {
		return this.lastModifiedDt;
	}

	/**
	 * Sets last modified dt.
	 *
	 * @param lastModifiedDt the last modified dt
	 */
	public void setLastModifiedDt(Date lastModifiedDt) {
		this.lastModifiedDt = lastModifiedDt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		return true;
	}

	/**
	 * Sets selector fields.
	 *
	 * @param fields the fields
	 */
	public void setSelectorFields(String... fields) {
		this.selectorFields = Arrays.asList(fields);
	}

	/**
	 * Sets selector fields.
	 *
	 * @param selectors the selectors
	 */
	public void setSelectorFields(List<String> selectors) {
		selectorFields = selectors;
	}
}

