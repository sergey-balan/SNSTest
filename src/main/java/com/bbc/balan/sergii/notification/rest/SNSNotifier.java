package com.bbc.balan.sergii.notification.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/notify")
public class SNSNotifier {
	 
	@POST
	@Path("/android")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doNotify(@QueryParam("message") String msg) {
		 
		String output = "Jersey say : " + msg;
			 
		return Response.status(200).entity(output).build();
		 
	}	
	
	
	@GET
	@Path("/logged")
	@Produces(MediaType.TEXT_HTML)
	public Response getLogged() {
		 
		String output = "getLogged";
			 
		return Response.status(200).entity(output).build();
		 
	}
	
	
}
