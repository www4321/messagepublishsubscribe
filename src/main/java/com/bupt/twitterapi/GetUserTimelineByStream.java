package com.bupt.twitterapi;

import java.util.Date;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月14日上午11:27:29
 */
public class GetUserTimelineByStream {
	public void getUserTimeline(String user) {
		TwitterStream twitterStream = GetTwitterStreamInstance.twitterStream;
		StatusListener statusListener = new StatusListener() {

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				ex.printStackTrace();
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// TODO Auto-generated method stub
				System.out.println(
						"[this is GetUserTimelineByStream:] Got track limitation notice:" + numberOfLimitedStatuses);

			}

			@Override
			public void onStatus(Status status) {
				// TODO Auto-generated method stub
				System.out.println(
						"[this is GetUserTimelineByStream:] Showing@" + status.getUser() + "'s user timeline.");
				// status.getRetweetCount() 转推的数目
				// status.getFavoriteCount() 点赞次数
				// status.getSource() 发布的客户端类型
				// status.getCreatedAt() 发布时间
				// status.getGeoLocation() 地点
				// status.getId() 获取该条tweet Id
				String content = status.getText(); // tweet内容
				String ScreenName = status.getUser().getScreenName();
				Date publishDate = status.getCreatedAt();
				System.out.println(
						"[this is GetUserTimelineByStream:] @" + ScreenName + "--" + content + "--" + publishDate);
				// System.out.println(status.getUser().getName() + ":" +
				// status.getText());
				String tweetUrl = "https://twitter.com/" + status.getUser().getScreenName() + "/status/"
						+ status.getId();
				System.out.println("[this is GetUserTimelineByStream:] tweetUrl:" + tweetUrl);
				if (status.getMediaEntities() != null && status.getMediaEntities().length >= 1) {
					try {
						String type = status.getMediaEntities()[0].getType();
						if (type.equals("photo")) {
							String imgUrl = status.getMediaEntities()[0].getMediaURL();
							System.out.println("[this is GetUserTimelineByStream:] imgUrl:" + imgUrl);
						} else if (type.equals("video")) {
							String videoUrl = status.getMediaEntities()[0].getMediaURL();
							System.out.println("[this is GetUserTimelineByStream:] videoUrl:" + videoUrl);
						} else {
							String animatedGifUrl = status.getMediaEntities()[0].getMediaURL();
							System.out.println("[this is GetUserTimelineByStream:] animatedGifUrl:" + animatedGifUrl);
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
					System.out.println("[this is GetUserTimelineByStream:] retweetUrl:" + retweetUrl);
				}

			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				System.out.println("[this is GetUserTimelineByStream:] Got stall warning:" + warning);

			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				System.out.println("[this is GetUserTimelineByStream:] Got scrub_geo event userId:" + userId
						+ " upToStatusId:" + upToStatusId);

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				System.out.println("[this is GetUserTimelineByStream:] Got a status deletion notice id:"
						+ statusDeletionNotice.getStatusId());

			}
		};

		twitterStream.addListener(statusListener);
		// twitterStream.sample();
		String[] Track = { user };
		// String[] Track = { "IMDB", "movie", "film", "cinema", };
		// String[] trackArray;
		// trackArray[0] = "Obama";
		// trackArray[1] = "Romney";

		FilterQuery filter = new FilterQuery();
		filter.track(Track);
		twitterStream.filter(filter);
	}
}
