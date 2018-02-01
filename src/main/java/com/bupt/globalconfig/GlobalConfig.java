package com.bupt.globalconfig;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Connection;

public class GlobalConfig {

	public static GlobalConfig config = new GlobalConfig();
	protected Logger logger = LoggerFactory.getLogger(GlobalConfig.class);

	public static GlobalConfig getInstance() {
		return config;
	}

	public String amqpHost = "10.108.171.217";
	public String amqpPort = "5672";
	public String amqpUserName = "gaoyang";
	public String amqpPassword = "gaoyang";
	public String ES_clusterName = "bupt-402";
	public String ES_HostName = "10.108.171.217";
	public Connection connection = null;

	public void loadConfig(String configName) {
		Field[] fs = this.getClass().getDeclaredFields();
		BufferedReader br;
		String line;

		try {
			br = new BufferedReader(new InputStreamReader(
					GlobalConfig.class.getClassLoader().getResourceAsStream(configName), Charset.forName("UTF-8")));
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#"))
					continue;
				String[] s = line.split("=", 2);
				if (s == null || s.length != 2)
					continue;

				String key = s[0].trim();
				String value = s[1].trim();

				for (Field f : fs) {
					if (!key.equals(f.getName()))
						continue;
					Type type = f.getType();
					if (type == String.class)
						f.set(this, value);
					else if (type == Integer.class) {
						f.set(this, Integer.parseInt(value));
						break;
					} else if (type == Long.class) {
						f.set(this, Long.parseLong(value));
						break;
					} else if (type == Float.class) {
						f.set(this, Float.parseFloat(value));
						break;
					} else {
						logger.error("Unresolved type :" + type);
						break;
					}
				}

			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		br = null;
	}

}
