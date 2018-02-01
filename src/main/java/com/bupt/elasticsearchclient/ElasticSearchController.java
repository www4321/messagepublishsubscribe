package com.bupt.elasticsearchclient;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @Describe TODO
 * @data Date:2017年12月11日下午3:22:21
 * @version v1.0
 */
@RestController
public class ElasticSearchController {
	@Autowired
	ElasticSearchClient elasticSearchClient;
	private Logger log = Logger.getLogger(ElasticSearchController.class);
	/**
	 * 获取最近的n条数据
	 * 
	 * @param index
	 * @param type
	 * @param count
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "/getLastIndex", method = RequestMethod.POST)
	public String getLastIndex(@RequestBody String argvs) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		log.info("................"+argvs);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = objectMapper.readValue(argvs, Map.class);

		String index = (String) map.get("index");
		String type = (String) map.get("type");
		int count = (int) map.get("count");

		List<String> list = new ArrayList<>();

		String id = txt2String(index, type, "id.txt").trim();

		if (id.equals("")) {
			System.out.println("error");
			return "error!";
		}
		long Max = Long.parseLong(id);

		for (long i = Max - 1; i >= Max - count; i--) {
			list.add(String.valueOf(i));
		}
		String content = "";
		for (String string : elasticSearchClient.multiGetById(index, type, list)) {
			content = content + string + "\n";
		}
		return content;
	}

	/**
	 * 批量插入数据
	 * 
	 * @param bulkIndexRequest
	 * @return
	 */
	@RequestMapping(value = "/bulkIndex", method = RequestMethod.POST)
	public String bulkIndex(@RequestBody BulkIndexRequest bulkIndexRequest) {

		String index = bulkIndexRequest.index;
		String type = bulkIndexRequest.type;
		List<Map<String, Object>> map = bulkIndexRequest.list;

		String id = txt2String(index, type, "id.txt").trim();
		long i;
		if (id.equals("")) {
			i = 1;
		} else {
			i = Long.parseLong(id);
		}
		BulkRequestBuilder bulkRequest = ElasticSearchClient.client.prepareBulk();
		for (Map<String, Object> map2 : map) {
			try {
				bulkRequest.add(ElasticSearchClient.client.prepareIndex(index, type, String.valueOf(i))
						.setSource(map2Xcontent(map2)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		elasticSearchClient.bulkIndex(bulkRequest);
		contentToTxt(index, type, "id.txt", String.valueOf(i));
		return "succeed!";
	}

	/**
	 * 插入一条数据
	 * 
	 * @param indexRequest
	 * @return
	 */

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String index(@RequestBody IndexRequest indexRequest) {
		String index = indexRequest.index;
		String type = indexRequest.type;
		Map<String, Object> map = indexRequest.map;
		ObjectMapper objectMapper = new ObjectMapper();

		System.out.println("index:" + index);
		System.out.println("type:" + type);
		try {
			System.out.println("content:" + objectMapper.writeValueAsString(map));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = txt2String(index, type, "id.txt").trim();
		System.out.println(id);
		long i;
		if (id.equals("")) {
			i = 1;
		} else {
			i = Long.parseLong(id);
		}

		elasticSearchClient.index(index, type, String.valueOf(i), map);
		i++;

		contentToTxt(index, type, "id.txt", String.valueOf(i));
		return "succeed!";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String test() {
		return "hello world!";
	}

	/**
	 * 读取txt文件的内容
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 返回文件内容
	 */
	public static String txt2String(String index, String type, String filePath) {
		// StringBuilder result = new StringBuilder();
		String result = "";
		try {
			// 构造一个BufferedReader类来读取文件

			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				String[] list = s.toString().split("\\s+");
				if (list[0].equals(index) && list[1].equals(type)) {
					result = list[2];
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将内容写入文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            要写入文件的内容（字符串）
	 */
	public static void contentToTxt(String index, String type, String filePath, String content) {

		String str = "";// 原文件内容

		try {
			File f = new File(filePath);

			BufferedReader br = new BufferedReader(new FileReader(f));
			boolean flag = false;
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				String[] list = s.toString().split("\\s+");
				if (list[0].equals(index) && list[1].equals(type)) {
					str = str + list[0] + " " + list[1] + " " + content + "\r\n";
					flag = true;
					continue;
				}
				str = str + s.toString() + "\r\n";
			}
			if (!flag) {
				str = str + index + " " + type + " " + content + "\r\n";
			}

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(str);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 将map转换为XContentBuilder，用于向es中批量插入数据
	 * 
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public XContentBuilder map2Xcontent(Map<String, Object> map) throws IOException {

		Set<String> set = map.keySet();

		XContentBuilder xContentBuilder = jsonBuilder().startObject();

		for (String string : set) {
			xContentBuilder = xContentBuilder.field(string, map.get(string));
		}
		return xContentBuilder.endObject();
	}

}
