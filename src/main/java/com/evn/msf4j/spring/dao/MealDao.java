package com.evn.msf4j.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.evn.msf4j.model.Meal;

@Service("mealDao")
public class MealDao {
	
	List<Meal> meals = new ArrayList<Meal>();

	public MealDao() {
		if(meals.size() == 0) {
			
			meals.add(new Meal("Beef", (double) 120));
			
			meals.add(new Meal("Pork", (double) 80));
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
