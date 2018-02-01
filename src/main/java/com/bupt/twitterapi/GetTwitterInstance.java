package com.bupt.twitterapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月13日下午4:13:16
 */
public class GetTwitterInstance {

	public static Twitter twitter = null;

	Logger logger = org.slf4j.LoggerFactory.getLogger(GetTwitterInstance.class.getName());

	public GetTwitterInstance() throws IOException {
		File file = new File("config/twitter4j.properties");
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
		twitter = new TwitterFactory().getInstance();
	}

	public GetTwitterInstance(String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken,
			String OAuthAccessTokenSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setJSONStoreEnabled(true).setOAuthConsumerKey(OAuthConsumerKey)
				.setOAuthConsumerSecret(OAuthConsumerSecret).setOAuthAccessToken(OAuthAccessToken)
				.setOAuthAccessTokenSecret(OAuthAccessTokenSecret).setHttpConnectionTimeout(100000);
		// .setHttpProxyHost("127.0.0.1").setHttpProxyPort(1080);
		twitter = new TwitterFactory(cb.build()).getInstance();
	}

}
