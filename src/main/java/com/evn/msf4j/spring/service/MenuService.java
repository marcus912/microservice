package com.evn.msf4j.spring.service;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evn.msf4j.spring.service.srpingservice.MealSpringService;

@Component
@Path("/menu")
public class MenuService {
	
	@Autowired
    private MealSpringService mealService;
	
	@GET
    @Path("/")
	@Produces({ "application/json" })
    public Response index() {
		return Response.ok().entity(mealService.findAll()).build();
    }
	
	@GET
    @Path("/html")
	@Produces({ "application/json" })
    public Response html() {
        Map map = Collections.singletonMap("meals", mealService.findAll());
        String html = "<body><h1> " + map.get("meals") + "</h1></body>";
        return Response.ok()
          .type(MediaType.TEXT_HTML)
          .entity(html)
          .build();
    }
 
    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    public Response meal(@PathParam("id") int id) {
		return Response.ok().entity(mealService.find(id)).build();
    }
}
