package com.bupt.messagepublishsubscribe;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @version Date:2017年11月14日下午9:27:31
 */
public class TestTwitter {
	public static void main(String[] args) {
		new Thread(new TestTwitterApi()).start();
		new Thread(new TestTwitterStreamApi()).start();
	}
}
