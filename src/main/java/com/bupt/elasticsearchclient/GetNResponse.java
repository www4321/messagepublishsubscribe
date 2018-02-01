package com.bupt.elasticsearchclient;
/**
*Copyright 2017 com.bupt
*@author gaoyang
*@Describe TODO
*@data Date:2017年12月11日下午8:48:49
*@version v1.0
*/

import java.util.List;

public class GetNResponse {
	String result;
	List<String> list;

	public GetNResponse() {
		// TODO Auto-generated constructor stub
	}

	public GetNResponse(String result) {
		this.result = result;
	}

	public GetNResponse(List<String> list) {
		this.list = list;
	}
}
