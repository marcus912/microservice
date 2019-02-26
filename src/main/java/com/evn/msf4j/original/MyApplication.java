package com.evn.msf4j.original;

import org.wso2.msf4j.MicroservicesRunner;

import com.evn.msf4j.original.service.MenuService;
import com.evn.msf4j.original.service.MyService;

public class MyApplication {

	public static void main(String[] args) {
		System.out.println("Start Service");
		new MicroservicesRunner()
        .deploy(new MyService(), new MenuService())
        .start();

	}

}
