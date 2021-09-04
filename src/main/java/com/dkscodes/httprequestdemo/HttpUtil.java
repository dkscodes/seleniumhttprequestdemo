package com.dkscodes.httprequestdemo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
/**
 * 
 * @author DK
 * Util class to invoke http GET/POST APIs using org.openqa.selenium.remote.http.HttpClient
 * and related classes
 */
public class HttpUtil {
	/**
	 * 
	 * @param httpMethod - Should be an object of org.openqa.selenium.remote.http.HttpMethod 
	 * @param baseUrl - Base url of the API
	 * @param api - API String
	 * @param headerParams - Map of header parameters. Should pass null or empty Map if there are't any
	 * header parameters.
	 * @param requestBody - Json string of request parameters. Should pass null if there are't any
	 * @return JSONObject of the API response
	 */
	public JSONObject getApiResponse(HttpMethod httpMethod,String baseUrl, String api, Map<String,String>headerParams, String requestBody) {
		HttpRequest request = new HttpRequest(httpMethod,api);	// create request object with method and API 
		if(!headerParams.isEmpty()) {
			// Add header parameters, if any 
			for(Map.Entry<String, String> entry : headerParams.entrySet()) {
				request.setHeader(entry.getKey(),entry.getValue());		      
			}
		}
		if(null!=requestBody) {
			// Add request body parameters, if any 
			byte[] byteArrray = requestBody.getBytes();
			request.setContent(byteArrray);	
		}
		try {	
			URL url = new URL(baseUrl);
			// Create HttpClient object with the URL
			HttpClient client = HttpClient.Factory.createDefault().createClient(url);			
			HttpResponse response = client.execute(request); // Execute the request
			// Created a JSONObject from the API response, assuming REST API and the response is json string
			JSONObject resultJson = new JSONObject(response.getContentString());  
			return resultJson;			
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
		return null;
	}
}
