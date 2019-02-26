package com.evn.msf4j.spring;


import org.wso2.msf4j.spring.MSF4JSpringApplication;

public class MySpringApplication {

	public static void main(String[] args) {
		System.out.println("Start Spring Service");
		MSF4JSpringApplication.run(MySpringApplication.class, args);
	}

}
