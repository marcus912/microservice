package com.evn.msf4j.original.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.evn.msf4j.original.dao.OrigianlMealDao;
import com.google.gson.Gson;

@Path("/menu")
public class MenuService {

	OrigianlMealDao mealDao = new OrigianlMealDao();

	@Path("/")
	@GET
	@Produces({"application/json"})
	public Response index() {
		return Response.ok().entity(mealDao.findAll()).build();
	}

	@Path("/findMeal/{id}")
	@GET
	@Produces({"application/json"})
	public Response findMeal(@PathParam("id") int id) {
		return Response.ok().entity(mealDao.find(id)).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces({"application/json"})
	public String meal(@PathParam("id") int id) {
		Gson gson = new Gson();
		return gson.toJson(mealDao.find(id));
	}

	@PostConstruct
    public void init() {
        System.out.println("MenuService is calling PostConstruct method");
    }

    @PreDestroy
    public void close() {
    	System.out.println("MenuService is calling PreDestroy method");
    }

}
