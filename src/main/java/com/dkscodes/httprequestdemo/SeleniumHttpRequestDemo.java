package com.dkscodes.httprequestdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.remote.http.HttpMethod;
/**
 * 
 * @author DK
 * Class to show a demo of calling REST APIs with org.openqa.selenium.remote.http.HttpClient
 * and related classes
 *
 */
public class SeleniumHttpRequestDemo {
	static Object returnedValue = null;	
	public static void main(String[] args) {
		Map<String,String>headerParams = new HashMap<String,String>();
		String requestBody=null;
		HttpUtil httpUtil = new HttpUtil();
		
		//Calling a simple Http GET API without any header or request body parameter
		// Uses API from reqres.in - A hosted REST-API which helps in testing any front-end against a real API 
		JSONObject resultJson = httpUtil.getApiResponse(HttpMethod.GET, "https://reqres.in", "/api/users/2", headerParams, requestBody);
		System.out.println("Data: "+resultJson.get("data"));
		// I couldn't find a way to add HTTP header parameters to a mock api in reqres.in 
		// Uncomment the fbelow code block to try next API calls
		/*
		//Calling Http POST API header and query parameters. Query params are added to url itself
		headerParams.put("<key>", "<value>");
		resultJson = httpUtil.getApiResponse(HttpMethod.POST, "<baseUrl>", "<api_url_with_params>", headerParams, requestBody);
		Object value = getValueFromJson(resultJson,"<key>");
		System.out.println("Value"+value.toString());
		
		//Calling Http POST API with header and request body parameters
		requestBody="{<request_json>}";
		resultJson = httpUtil.getApiResponse(HttpMethod.POST, "<baseUrl>", "<post_api_url>", headerParams, requestBody);
		System.out.println(resultJson.get("<key>")); */
	}
	/**
	 * Method to iterate through the API response json object and get the value corresponding to given key
	 * @param jsonObj - API response json
	 * @param keyName - key
	 * @return value corresponding to the given key
	 */ 
	public static Object getValueFromJson(JSONObject jsonObj, String keyName) {
	    for (Object key : jsonObj.keySet()) {
	        String keyStr = (String)key;
	        Object keyvalue = jsonObj.get(keyStr);
	        if(keyStr.equals(keyName)) {
	        	returnedValue = keyvalue;
	        	return returnedValue;
	        }
	        //If nested json, iterate the objects if needed. calling recursively
	        if (keyvalue instanceof JSONObject && null==returnedValue)
	        	getValueFromJson((JSONObject)keyvalue, keyName);
	      //If array of objects, iterate the json objects if needed. calling recursively
	        if (keyvalue instanceof JSONArray) {
	        	JSONArray array = (JSONArray) keyvalue;
	        	for(int i=0;i<array.length();i++) {
	        		JSONObject jsonObject = array.getJSONObject(i);
	        		if( null==returnedValue)
	        			getValueFromJson(jsonObject, keyName);
	        	}
	        }
	    }
	    return returnedValue;
	}	
}
