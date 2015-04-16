package com.bbc.balan.sergii.notification.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/notify")
public class SNSNotifier {

	
	@POST
	@Path("/android")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doNotify(String jsonRequest) {

		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(jsonRequest).getAsJsonObject();
		String value = obj.get("message").toString();
		
		String output = "SNS says: " + value;
	
		return Response.status(200).entity(output).build();
		 
	}		
	
	
//	@POST
//	@Path("/android")
//	@Produces(MediaType.TEXT_HTML)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doNotify(@QueryParam("message") String msg) {
//		 
//		String output = "SNS says: " + msg;
//			 
//		return Response.status(200).entity(output).build();
//		 
//	}	
	
	
//	@POST
//	@Path("/android")
//	@Produces(MediaType.TEXT_HTML)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response doNotify(JSONObject jsonEntity) {
//		 
//		String output = "SNS says: " + msg;
//			 
//		return Response.status(200).entity(output).build();
//		 
//	}		
	
	
	@GET
	@Path("/logged")
	@Produces(MediaType.TEXT_HTML)
	public Response getLogged() {
		 
		String output = "getLogged";
			 
		return Response.status(200).entity(output).build();
		 
	}
	
	
}
