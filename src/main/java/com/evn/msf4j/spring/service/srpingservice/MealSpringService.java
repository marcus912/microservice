package com.evn.msf4j.spring.service.srpingservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evn.msf4j.model.Meal;
import com.evn.msf4j.spring.dao.MealDao;

@Service("meal")
public class MealSpringService {
	
	@Autowired
	MealDao mealDao;
 
    public Meal find(int id) {
        return mealDao.find(id);
    }
 
    public List<Meal> findAll() {
        return mealDao.findAll();
    }
 
    public void create(Meal meal) {
    	mealDao.create(meal);
    }
}
