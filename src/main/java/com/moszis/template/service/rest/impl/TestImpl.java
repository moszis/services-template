package com.moszis.template.service.rest.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.moszis.template.service.dao.TestDao;
import com.moszis.template.service.entity.TestEntity;
import com.moszis.template.service.rest.api.TestService;

@Stateless
public class TestImpl implements TestService {
	
	
	@EJB
	TestDao testDao;
    
    @Override
    public Response getTestData(long testId) {

    	System.out.println("*****Test ****"+testId);

        TestEntity result = testDao.getTest(testId);
        
        
    	return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();

    }
    
    @Override
    public Response getTestData() {

    	System.out.println("*****getTestData() ****");

        List<TestEntity> result = testDao.getTests();
        
        
    	return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();

    }
}