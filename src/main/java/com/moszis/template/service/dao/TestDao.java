package com.moszis.template.service.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moszis.template.service.entity.TestEntity;

@Stateless
public class TestDao {

	private static Log log = LogFactory.getLog(TestDao.class);
	
    @PersistenceContext(unitName = "testjpa")
    protected EntityManager entityManager;

    
	public TestEntity getTest(Long testId){

	   	try{
	   	
		   	String queryName = "Test.by.testId";
		   	Query query = entityManager.createNamedQuery(queryName);
		   	query.setParameter("testId", testId);
	
		   	TestEntity testEntity = (TestEntity) query.getSingleResult();
		   	
		   	return testEntity;
	   	
		}catch (NoResultException nre){
			return null;
		}catch (Exception e){
		    log.error("getPaymentApproval(UUID paymentApprovalId) : Unable to retrieve PaymentApproval by ID: "+testId
		    		+", error: ", e);
		    throw new RuntimeException(e);
		}

	}
	
	public List<TestEntity> getTests(){

	   	try{
	   	
		   	String queryName = "Test.all";
		   	Query query = entityManager.createNamedQuery(queryName);
	
		   	List<TestEntity> tests = query.getResultList();
		   	
		   	return tests;
	   	
		}catch (NoResultException nre){
			return null;
		}catch (Exception e){

		    throw new RuntimeException(e);
		}

	}
	
}

