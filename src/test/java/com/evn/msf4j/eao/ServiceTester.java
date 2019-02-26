package com.evn.msf4j.eao;

import java.io.IOException;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class ServiceTester extends TestCase {
	
	WebApplicationContext webApplicationContext;
	

	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public ServiceTester(String testName) {
		
		super(testName);
	}

	public ServiceTester() {
	}

	public void testServiceA() {
		System.out.println("Test service A");
		assertTrue(true);
	}
	
	public void testServiceB() {
		System.out.println("Test service B");
		String actual = "{id:123, name:\"John\"}";
		try {
			JSONAssert.assertEquals(
			  "{id:123,name:\"John\"}", actual, JSONCompareMode.LENIENT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void testSpringService() throws Exception {
		
	}
	
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
