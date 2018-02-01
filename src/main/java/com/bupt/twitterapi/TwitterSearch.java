package com.bupt.twitterapi;

import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月14日下午1:10:06
 */
public class TwitterSearch {
	public void search(String keyWord) throws TwitterException {
		Twitter twitter = GetTwitterInstance.twitter;
		Query query = new Query(keyWord);
		QueryResult queryResult = twitter.search(query);

		List<Status> statuses;
		// String user = "BillGates";
		// Paging page = new Paging(pageStart, pageEnd);
		statuses = queryResult.getTweets();
		System.out.println("[this is TwitterSearch:] Showing@" + keyWord + "'s user timeline.");
		processStatus(statuses);
	}

	public void processStatus(List<Status> statuses) {
		for (Status status : statuses) {
			// status.getRetweetCount() 转推的数目
			// status.getFavoriteCount() 点赞次数
			// status.getSource() 发布的客户端类型
			// status.getCreatedAt() 发布时间
			// status.getGeoLocation() 地点
			// status.getId() 获取该条tweet Id
			String content = status.getText(); // tweet内容
			String ScreenName = status.getUser().getScreenName();
			Date publishDate = status.getCreatedAt();
			System.out.println("[this is TwitterSearch:] @" + ScreenName + "--" + content + "--" + publishDate);
			// System.out.println(status.getUser().getName() + ":" +
			// status.getText());
			String tweetUrl = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
			System.out.println("[this is TwitterSearch:] tweetUrl:" + tweetUrl);
			if (status.getMediaEntities() != null && status.getMediaEntities().length >= 1) {
				try {
					String type = status.getMediaEntities()[0].getType();
					if (type.equals("photo")) {
						String imgUrl = status.getMediaEntities()[0].getMediaURL();
						System.out.println("[this is TwitterSearch:] imgUrl:" + imgUrl);
					} else if (type.equals("video")) {
						String videoUrl = status.getMediaEntities()[0].getMediaURL();
						System.out.println("[this is TwitterSearch:] videoUrl:" + videoUrl);
					} else {
						String animatedGifUrl = status.getMediaEntities()[0].getMediaURL();
						System.out.println("[this is TwitterSearch:] animatedGifUrl:" + animatedGifUrl);
					}

				} catch (Exception e) {
					System.out.println("Status:" + status);
					System.out.println(e.getStackTrace());
				}
			}
			if (status.getRetweetedStatus() != null) {
				// 如果这条消息是转发的
				// System.out.println("sourceTweet:"+status.getRetweetedStatus());
				// System.out.println(tweetUrl);
				String reScreenName = status.getRetweetedStatus().getUser().getScreenName();
				Long RetweetedId = status.getRetweetedStatus().getId();
				// System.out.println("getMediaEntities:"+status.getRetweetedStatus().getUser().getScreenName());
				// System.out.println("getMediaEntities:"+status.getRetweetedStatus().getId());
				String retweetUrl = "https://twitter.com/" + reScreenName + "/status/" + RetweetedId;
				System.out.println("[this is TwitterSearch:] retweetUrl:" + retweetUrl);
			}
		}
	}
}
