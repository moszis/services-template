package com.moszis.template.service.rest.api;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@PermitAll
@Path("/test")
public interface TestService {
   
    @PermitAll
    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTestData();
    
    @PermitAll
    @GET
    @Path("/{testId}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTestData(@PathParam("testId") long testId);

}
