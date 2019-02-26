package com.evn.msf4j.original.dao;

import java.util.ArrayList;
import java.util.List;

import com.evn.msf4j.model.Meal;

public class OrigianlMealDao {
	
	List<Meal> meals = new ArrayList<Meal>();

	public OrigianlMealDao() {
		if(meals.size() == 0) {
			
			meals.add(new Meal("Beef", (double) 200));
			
			meals.add(new Meal("Pork", (double) 160));
		}
	}

	public Meal find(int id) {
		return meals.get(id);
	}

	public List<Meal> findAll() {
		return meals;
	}

	public void create(Meal meal) {
		meals.add(meal);
	}
	
}
