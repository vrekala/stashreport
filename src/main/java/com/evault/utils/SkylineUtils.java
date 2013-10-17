package com.evault.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class SkylineUtils {

	String stashURL="http://stash.fb.lab";


	public String getAuthTokens(String username, String password) {

		HttpURLConnection conn = null;
		String authToken = "";
		int actualStatusCode = 0;
		try {
			String credentialsjsonTxt = null;

			URL url = new URL(stashURL + "/" + SkylineConstant.AUTH);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000); // set timeout to 5 seconds
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"username\":\"" + username + "\",\"password\":\""
					+ password + "\"}";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			actualStatusCode = conn.getResponseCode();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String line = null;
           
              // Read Server Response
              while((line = br.readLine()) != null)
                  {
                         // Append server response in string
                         sb.append(line + "");
                  }
               
              // Append Server Response To Content String
              credentialsjsonTxt = sb.toString();
          
		
			System.out.println(credentialsjsonTxt);
			System.out.println("Skyline server Response Code "
					+ actualStatusCode);
			conn.disconnect();

			JSONObject json = (JSONObject) JSONSerializer
					.toJSON(credentialsjsonTxt);
			authToken = json.getString("token");

			System.out.println("Skyline  authToken " + authToken);

		} catch (SocketTimeoutException se) {
			se.printStackTrace();
 
		} catch (Exception e) {
			e.printStackTrace();
 
		} finally {
			System.out.println("Skyline server Response Code "
					+ actualStatusCode);

			conn.disconnect();
		}

		return authToken;
	}
	
	
	public String getCommits(String tokenId) {
		HttpResponse response=null;
		String jsonTxt = "";
		String skylineURL = stashURL + "/" + SkylineConstant.COMMITS_LIST;
		System.out.println("stashURL== "+skylineURL);
		HttpGet request;
		try {
			request = new HttpGet(skylineURL);
			jsonTxt = executeAPI(request, response, tokenId, "Get Containers");

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonTxt;
	}
	
	public String getContainers(String tokenId) {
		HttpResponse response=null;
		String jsonTxt = "";
		String skylineURL = stashURL + "/" + SkylineConstant.CONTAINERS_LIST;
		System.out.println("skylineURL== "+skylineURL);
		HttpGet request;
		try {
			request = new HttpGet(skylineURL);
			jsonTxt = executeAPI(request, response, tokenId, "Get Containers");

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonTxt;
	}
	
	
	
	public String executeAPI(HttpGet request, HttpResponse response,
			String tokenId, String message) {
		int actualStatusCode = 0;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String jsonTxt=null;

		try {
			//request.setHeader("Authentication", tokenId);
			response = httpclient.execute(request);
			actualStatusCode = response.getStatusLine().getStatusCode();
//			utils.verifyAPIRequestStatus(actualStatusCode, message);
//			CustomValidator.assertEquals(actualStatusCode, 200, message
//					+ actualStatusCode);
			System.out.println( "Status Code "
					+ actualStatusCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
            String line = null;
            StringBuilder sb = new StringBuilder();

			
            // Read Server Response
            while((line = br.readLine()) != null)
                {
                       // Append server response in string
                       sb.append(line + "");
                }
             
            // Append Server Response To Content String
            jsonTxt = sb.toString();
        
		
			System.out.println(jsonTxt);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			//CustomValidator.fail(message, e);

		} finally {
			//utils.closeConnection(response, httpclient);

		}
		return jsonTxt;

	}
	
	
//	public String getContainers(String tokenId) {
//		String jsonTxt = "";
//		skylineURL = skylineHostName + "/" + SkylineConstant.PROJECTS;
//		HttpGet request = new HttpGet(skylineURL);
//		jsonTxt = executeAPI(request, response, tokenId, "Get Containers");
//		return jsonTxt;
//	}
//	
//
//	public String executeAPI(HttpGet request, HttpResponse response,
//			String tokenId, String message) {
//		int actualStatusCode = 0;
//		String jsonTxt=null;
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
//		try {
//			request.setHeader("Authentication", tokenId);
//			response = httpclient.execute(request);
//			actualStatusCode = response.getStatusLine().getStatusCode();
////			utils.verifyAPIRequestStatus(actualStatusCode, message);
////			CustomValidator.assertEquals(actualStatusCode, 200, message
////					+ actualStatusCode);
////			EvaultLogger.info(" -- " + message + "Status Code "
////					+ actualStatusCode);
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					response.getEntity().getContent()));
//
//			jsonTxt = br.readLine();
//
//		} catch (Exception e) {
//			//CustomValidator.fail(message, e);
//
//		} finally {
//			utils.closeConnection(response, httpclient);
//
//		}
//		return jsonTxt;
//
//	}	

}
