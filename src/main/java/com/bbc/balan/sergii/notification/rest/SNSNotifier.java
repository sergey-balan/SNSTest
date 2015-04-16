package com.bbc.balan.sergii.notification.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bbc.balan.sergii.notification.sns.SNSPushGenerator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Path("/notify")
public class SNSNotifier {

	
	@POST
	@Path("/android")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doNotify(String jsonRequest) {

		String output = "SNS says: ";
		int status = 200;
		
		try {
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(jsonRequest).getAsJsonObject();
			String value = obj.get("message").toString();
			

				try {
					SNSPushGenerator.push(value);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			output += value;
		}
		catch(JsonSyntaxException e) {
			output += "Json syntax error";
			status = 500;
		}
		catch(JsonParseException e) {
			output += "Json parse error";
			status = 500;
		}

	
		return Response.status(status).entity(output).build();
		 
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
