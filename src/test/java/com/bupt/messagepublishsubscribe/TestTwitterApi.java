package com.bupt.messagepublishsubscribe;

import java.io.IOException;

import com.bupt.twitterapi.GetTwitterInstance;
import com.bupt.twitterapi.GetUserTimelineByPage;
import com.bupt.twitterapi.TwitterSearch;

import twitter4j.TwitterException;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月14日下午2:20:44
 */
public class TestTwitterApi implements Runnable {
	public String OAuthConsumerKey = "b8ux9vYhw0LTq3wploDRw65CQ";
	public String OAuthConsumerSecret = "OdqULL4IyIcugAEAIJd0AZGctal4oisRCvTMGzrMAh2VUxAUhq";
	public String OAuthAccessToken = "929937801562423296-vrou936VPYWLvxn14RJufL66uTNEgxi";
	public String OAuthAccessTokenSecret = "U1zMWC9QKHMMCwPAWGWPw5aQApBVF2FKxsLYBIjwhzDHM";

	public void start() throws IOException, TwitterException {
		new GetTwitterInstance(OAuthConsumerKey, OAuthConsumerSecret, OAuthAccessToken, OAuthAccessTokenSecret);
		// new GetTwitterInstance();
		GetUserTimelineByPage getUserTimelineByPage = new GetUserTimelineByPage();
		getUserTimelineByPage.getUserTimeline(5, "gaoyang8320");

		TwitterSearch twitterSearch = new TwitterSearch();
		twitterSearch.search("gaoyang8320");
	}

	@Override
	public void run() {
		try {
			start();
		} catch (IOException | TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
