package com.moszis.template.service.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


import com.moszis.template.service.core.EntityBase;
import com.moszis.template.service.core.ITransformable;
import com.moszis.template.service.core.util.StringUtil;
import com.moszis.template.service.dto.Test;

@NamedQueries({

	@NamedQuery(
			name = "Test.all", query = " "
			+ " SELECT t "
			+ " FROM TestEntity t "),
	
	@NamedQuery(
			name = "Test.by.testId", query = " "
			+ " SELECT t "
			+ " FROM TestEntity t "
			+ " WHERE t.id = :testId  ")
})

@Entity
@Table(name = "TEST")
public class TestEntity  extends EntityBase  implements ITransformable<Test>{ 

	private static final long serialVersionUID = 2968353361253859616L;
	
	@Id
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name = "test_id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "test_name")
	private String name;

	
	public TestEntity() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestEntity other = (TestEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Test toDTO() {

		Test target = new Test();
		target.setId(StringUtil.uuidToString(this.getId()));
		target.setName(this.getName());
		return target;
	}

}
