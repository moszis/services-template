package com.moszis.template.service.rest.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.moszis.template.service.core.exceptions.EntityNotFoundException;
import com.moszis.template.service.core.util.LocalContext;
import com.moszis.template.service.core.util.StandardResponseBuilder;
import com.moszis.template.service.core.util.StandardValidationHelper;
import com.moszis.template.service.dto.Test;
import com.moszis.template.service.rest.api.TestService;
import com.moszis.template.service.services.TestBusinessService;

@Stateless
public class TestImpl implements TestService {
	
	
	@EJB
	TestBusinessService testBusinessService;
    
    @Override
    public Response getTestData(String testId) {

		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
		try {
			Test result = testBusinessService.getTest(testId);
	    	return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		} catch (EntityNotFoundException e) {
			return StandardResponseBuilder.resourceNotFound();
		} catch (Exception e) {
			return validationHelper.hasErrors() ? StandardResponseBuilder.validationError(validationHelper.getValidationErrors()):
				StandardResponseBuilder.resourceNotFound("Test could not be found");
		}

    }
    
    @Override
    public Response getTestData() {

		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
		try {
			List<Test> result = testBusinessService.getTests();
	    	return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		} catch (EntityNotFoundException e) {
			return StandardResponseBuilder.resourceNotFound();
		} catch (Exception e) {
			return validationHelper.hasErrors() ? StandardResponseBuilder.validationError(validationHelper.getValidationErrors()):
				StandardResponseBuilder.resourceNotFound("Test could not be found");
		}

    }

	@Override
	public Response createTestData(Test test) {

		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
		try {
			Test result = testBusinessService.createTest(test);
	    	return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		} catch (Exception e) {
			return validationHelper.hasErrors() ? StandardResponseBuilder.validationError(validationHelper.getValidationErrors()):
				StandardResponseBuilder.resourceNotFound("Test could not be created");
		}
		
	}
	
	@Override
	public Response updateTestData(String testId, Test test) {

		final StandardValidationHelper validationHelper = LocalContext.getValidationContext();
		
		try {
			Test result = testBusinessService.updateTest(testId, test);
	    	return Response.status(Response.Status.OK.getStatusCode()).entity(result).build();
		} catch (Exception e) {
			return validationHelper.hasErrors() ? StandardResponseBuilder.validationError(validationHelper.getValidationErrors()):
				StandardResponseBuilder.resourceNotFound("Test could not be created");
		}
		
	}
}