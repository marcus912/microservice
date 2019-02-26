package com.evn.msf4j.spring.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

@Component
@Path("/myComponent")
public class MyService {

    @GET
    @Path("/restAPI/{value}")
	public String restAPI(@PathParam("value") String value) {
		return "restAPI " + value;
	}
}
