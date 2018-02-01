package com.bupt.twitterapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月14日上午10:56:18
 */
public class GetTwitterStreamInstance {

	public static TwitterStream twitterStream = null;
	Logger logger = LoggerFactory.getLogger(GetTwitterStreamInstance.class.getName());

	public GetTwitterStreamInstance() throws IOException {
		// TODO Auto-generated constructor stub
		File file = new File("config//twitter4j.properties");
		Properties prop = new Properties();
		InputStream is = null;
		OutputStream os = null;
		try {
			if (file.exists()) {
				is = new FileInputStream(file);
				prop.load(is);
			} else {
				logger.error("twitter4j.properties does not exist!");
				System.exit(-1);
			}
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignore) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ignore) {
				}
			}
		}
		twitterStream = new TwitterStreamFactory().getInstance();
	}

	public GetTwitterStreamInstance(String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken,
			String OAuthAccessTokenSecret) {
		// TODO Auto-generated constructor stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setJSONStoreEnabled(true).setOAuthConsumerKey(OAuthConsumerKey)
				.setOAuthConsumerSecret(OAuthConsumerSecret).setOAuthAccessToken(OAuthAccessToken)
				.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	}

}
