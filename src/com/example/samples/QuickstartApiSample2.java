package com.example.samples;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.DescribeGlobalResult;
import com.sforce.soap.enterprise.DescribeGlobalSObjectResult;
import com.sforce.soap.enterprise.DescribeSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.Field;
import com.sforce.soap.enterprise.FieldType;
import com.sforce.soap.enterprise.GetUserInfoResult;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.PicklistEntry;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.*;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;

public class QuickstartApiSample2 {
	private static BufferedReader reader = new BufferedReader(
	new InputStreamReader(System.in));
	EnterpriseConnection connection;
	String authEndPoint = "";
	Attachment aa=new Attachment();
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: com.example.samples."
			+ "QuickstartApiSamples <AuthEndPoint>");
			System.exit(-1);
		}
		QuickstartApiSample sample = new QuickstartApiSample(args[0]);
		sample.run();
	}
	public void run() {
		// Make a login call
		if (login()) {

			attachmentSample();
			logout();
		}
	}
	// Constructor
	public QuickstartApiSample2(String authEndPoint) {
		this.authEndPoint = authEndPoint;
	}

	private boolean login() {
		boolean success = false;

		String username="fuzhaolong@qq.com";
		String password ="valueplus1212";
		try {
			ConnectorConfig config = new ConnectorConfig();
			config.setUsername(username);
			config.setPassword(password);
			System.out.println("AuthEndPoint: " + authEndPoint);
			config.setAuthEndpoint(authEndPoint);
			connection = new EnterpriseConnection(config);
			printUserInfo(config);
			success = true;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return success;
	}
	private void printUserInfo(ConnectorConfig config) {
		try {
			GetUserInfoResult userInfo = connection.getUserInfo();
			System.out.println("\nLogging in ...\n");
			System.out.println("UserID: " + userInfo.getUserId());
			System.out.println("User Full Name: " + userInfo.getUserFullName());
			System.out.println("User Email: " + userInfo.getUserEmail());
			System.out.println();
			System.out.println("SessionID: " + config.getSessionId());
			System.out.println("Auth End Point: " + config.getAuthEndpoint());
			System.out
			.println("Service End Point: " + config.getServiceEndpoint());
			System.out.println();
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
	}
	private void logout() {
		try {
		connection.logout();
		System.out.println("Logged out.");
		} catch (ConnectionException ce) {
		ce.printStackTrace();
		}
	}


	private void attachmentSample() {
	    System.out.println("Start attachmentSample: ");
		try{
		    File f = new File("c:\\web1121\\lookup7.jpg");
		
		    InputStream is = new FileInputStream(f);
		    byte[] inbuff = new byte[(int)f.length()];        
		    is.read(inbuff);
		
		    Attachment attach = new Attachment();
		    attach.setBody(inbuff);
		    attach.setName("lookup7.jpg");
		    attach.setIsPrivate(false);
		    // attach to an object in SFDC 
		    attach.setParentId("0019000001uifVB");
		    System.out.println("Start to upload File: ");
		    SaveResult sr = connection.create(new com.sforce.soap.enterprise.sobject.SObject[] {attach})[0];
		    System.out.println("End to upload File: ");
		    if (sr.isSuccess()) {
		        System.out.println("Successfully added attachment.");
		    } else {
		        //System.out.println("Error adding attachment: " + sr.getErrors().getErrors(0).getMessage());
		    	System.out.println("Error adding attachment: " + sr.getErrors().toString());
		    }
	    } catch (FileNotFoundException fnf) {
	        System.out.println("File Not Found: " +fnf.getMessage());

	    } catch (IOException io) {
	        System.out.println("IO: " +io.getMessage());            
	    }
		catch (Exception exp) {
			System.out.println("Exception: " +exp.getMessage());            
		}
		System.out.println("End attachmentSample: ");
	}
}