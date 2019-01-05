package com.moszis.template.service.rest.api;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.moszis.template.service.core.constants.InterfaceConstants;
import com.moszis.template.service.dto.Test;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

@PermitAll
@Path("/test")
public interface TestService {
   
	@PermitAll
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Get All Tests")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = InterfaceConstants.RETURN_CODE_200_MESSAGE, response = Test.class),
			@ApiResponse(code = 401, message = InterfaceConstants.RETURN_CODE_401_MESSAGE),
			@ApiResponse(code = 403, message = InterfaceConstants.RETURN_CODE_403_MESSAGE),
			@ApiResponse(code = 410, message = InterfaceConstants.RETURN_CODE_410_MESSAGE),
			@ApiResponse(code = 422, message = InterfaceConstants.RETURN_CODE_422_MESSAGE)
	})
    public Response getTestData();
    
    @PermitAll
    @GET
    @Path("/{testId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTestData(@PathParam("testId") String testId);
    
    
	@PermitAll
	@POST
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Add Test Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = InterfaceConstants.RETURN_CODE_200_MESSAGE, response = Test.class),
			@ApiResponse(code = 401, message = InterfaceConstants.RETURN_CODE_401_MESSAGE),
			@ApiResponse(code = 403, message = InterfaceConstants.RETURN_CODE_403_MESSAGE),
			@ApiResponse(code = 410, message = InterfaceConstants.RETURN_CODE_410_MESSAGE),
			@ApiResponse(code = 422, message = InterfaceConstants.RETURN_CODE_422_MESSAGE)
	})
	Response createTestData(@ApiParam(value = "Test", required = true) Test test);
	
	@PermitAll
	@PUT
    @Path("/{testId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Update Test Data")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = InterfaceConstants.RETURN_CODE_200_MESSAGE, response = Test.class),
			@ApiResponse(code = 401, message = InterfaceConstants.RETURN_CODE_401_MESSAGE),
			@ApiResponse(code = 403, message = InterfaceConstants.RETURN_CODE_403_MESSAGE),
			@ApiResponse(code = 410, message = InterfaceConstants.RETURN_CODE_410_MESSAGE),
			@ApiResponse(code = 422, message = InterfaceConstants.RETURN_CODE_422_MESSAGE)
	})
	Response updateTestData(@PathParam("testId") String testId,
							@ApiParam(value = "Test", required = true) Test test);
}
