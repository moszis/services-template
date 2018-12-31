package com.moszis.template.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
@Table(name = "TEST" , schema = "HIBERNATE_TEST")
public class TestEntity {
	
	@Id
	@Column(name = "test_id")
	@GeneratedValue(strategy =  GenerationType.SEQUENCE)
	private long id;
	
	@Column(name = "test_name")
	private String name;

	
	public TestEntity() {
		super();
	}

	public TestEntity(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TestEntity other = (TestEntity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
