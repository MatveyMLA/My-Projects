package com.mati.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.mati.beans.User;

@Path("/api/user")
public class UserApi {
	
	@Path("/createUser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(User user){
		System.out.println(user);
	}
}
