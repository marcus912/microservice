package com.evn.msf4j.original.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class MyService {
	
	@GET
	public String index() {
		return "default content";
	}
	
	@GET
	@Path("/hello/{name}")
	public String hello(@PathParam("name") String name) {
		return "Hello " + name;
	}
}
