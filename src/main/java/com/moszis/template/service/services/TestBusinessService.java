package com.moszis.template.service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.moszis.template.service.core.exceptions.EntityNotFoundException;
import com.moszis.template.service.core.exceptions.InvalidDataException;
import com.moszis.template.service.core.util.LocalContext;
import com.moszis.template.service.core.util.StandardValidationHelper;
import com.moszis.template.service.dao.TestDao;
import com.moszis.template.service.dto.Test;
import com.moszis.template.service.entity.TestEntity;

@Stateless
public class TestBusinessService {

    private static final Log log = LogFactory.getLog(TestBusinessService.class);
    
    private static final String businessEntityName = "Test";
	
	@EJB
	TestDao testDao;

	
	public List<Test> getTests() throws EntityNotFoundException{

		List<Test> Tests = new ArrayList<Test>();
		
		List<TestEntity> TestEntities = getTestEntities();
		
		for(TestEntity TestEntity : TestEntities)
			Tests.add(TestEntity.toDTO());
		
		return Tests;

	}
	
	public List<TestEntity> getTestEntities() throws EntityNotFoundException{
		
		return testDao.getTests();
		
	}
	
	public Test getTest(String id) throws InvalidDataException, EntityNotFoundException{

		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
    	if(id == null){
    		validationHelper.addValidationError(""+businessEntityName+" Id", "Invalid "+businessEntityName+" Id");
    		throw new InvalidDataException("Invalid "+businessEntityName+" Id");
    	}
    		
		UUID uuid = validationHelper.validateAndConvertUUID(""+businessEntityName+" Id", id);
		
		if(uuid == null) throw new InvalidDataException("Invalid "+businessEntityName+" Id");

		return getTest(uuid);

	}
	
	public Test getTest(UUID uuid) throws EntityNotFoundException{
		
		
		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
    	try{
	
    		TestEntity entity = getEntity(uuid);
	    	
	    	if(entity == null){
	    		validationHelper.addValidationError(""+businessEntityName+"", ""+businessEntityName+" Not Found");
				throw new EntityNotFoundException(""+businessEntityName+"Entity:" + uuid + " does not exist");
	    	}

	    	return entity.toDTO();
	    	
		}catch (Exception e){
	        log.error("get"+businessEntityName+"(UUID uuId) : Unable to retrieve "+businessEntityName+" by ID: "+uuid+", error: ", e);
			throw new EntityNotFoundException(""+businessEntityName+"Entity:" + uuid + " does not exist");
		}
		
	}
	
	
	public TestEntity getTestEntity(String id) throws InvalidDataException, EntityNotFoundException{


		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
    	if(id == null){
    		validationHelper.addValidationError(""+businessEntityName+" Id", "Invalid "+businessEntityName+" Id");
    		throw new InvalidDataException("Invalid "+businessEntityName+" Id");
    	}
    		
		UUID uuid = validationHelper.validateAndConvertUUID("Invalid "+businessEntityName+" Id", id);
		
		if(uuid == null) throw new InvalidDataException("Invalid "+businessEntityName+"Id");

		return getTestEntity(uuid);

	}
	
	
	public TestEntity getTestEntity(UUID uuid) throws EntityNotFoundException{
		
		
		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
    	try{
	
    		TestEntity entity = getEntity(uuid);
	    	
	    	if(entity == null){
	    		validationHelper.addValidationError(""+businessEntityName+"", ""+businessEntityName+" Not Found");
				throw new EntityNotFoundException(""+businessEntityName+"Entity:" + uuid + " does not exist");
	    	}

	    	return entity;
	    	
		}catch (Exception e){
	        log.error("get"+businessEntityName+"Entity(UUID uuid) : Unable to retrieve "+businessEntityName+" by ID: "+uuid+", error: ", e);
			throw new EntityNotFoundException(""+businessEntityName+"Entity:" + uuid + " does not exist");
		}
		
	}
	
	
	private TestEntity getEntity(UUID uuid){
		return testDao.getTest(uuid);
	}

	
}
