package com.bupt.messagepublishsubscribe;

import java.io.IOException;

import com.bupt.twitterapi.GetTwitterStreamInstance;
import com.bupt.twitterapi.GetUserTimelineByStream;

import twitter4j.TwitterException;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月14日下午9:24:30
 */
public class TestTwitterStreamApi implements Runnable {

	public String OAuthConsumerKey = "b8ux9vYhw0LTq3wploDRw65CQ";
	public String OAuthConsumerSecret = "OdqULL4IyIcugAEAIJd0AZGctal4oisRCvTMGzrMAh2VUxAUhq";
	public String OAuthAccessToken = "929937801562423296-vrou936VPYWLvxn14RJufL66uTNEgxi";
	public String OAuthAccessTokenSecret = "U1zMWC9QKHMMCwPAWGWPw5aQApBVF2FKxsLYBIjwhzDHM";

	public void start() throws IOException, TwitterException {
		new GetTwitterStreamInstance(OAuthConsumerKey, OAuthConsumerSecret, OAuthAccessToken, OAuthAccessTokenSecret);
		// new GetTwitterInstance();
		GetUserTimelineByStream getUserTimelineByStream = new GetUserTimelineByStream();
		getUserTimelineByStream.getUserTimeline("gaoyang8320");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			start();
		} catch (IOException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
