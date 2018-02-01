package com.bupt.elasticsearchclient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public interface IMsgStorageManager {
	/**
	 * 向一个index中插入一个document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document类型
	 * @param json
	 *            document实体，json格式
	 * @return 插入的document的id
	 */
	public String index(String index, String type, Map<String, Object> map);

	/**
	 * 向一个index中插入一个document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document类型
	 * @param json
	 *            document实体，json格式
	 * @return 插入的document的id
	 */
	public String index(String index, String type, String id, Map<String, Object> map);

	/**
	 * 根据document的id查找document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document类型
	 * @param id
	 *            document的id
	 * @return
	 */
	public String getById(String index, String type, String id);

	/**
	 * 在一个请求中批量插入document
	 * 
	 * @param bulkRequest
	 * @return 插入document成功的id
	 */
	public List<String> bulkIndex(BulkRequestBuilder bulkRequest);

	/**
	 * 根据id删除一个document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document的类型
	 * @param id
	 *            document的id
	 * @return true:找到该index且删除成功
	 */
	public boolean deleteById(String index, String type, String id);

	/**
	 * 根据id对document的内容进行更新
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document的类型
	 * @param id
	 *            document的id
	 * @param map
	 *            document需要更新的内容，key:fieldName value:fieldValue
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updateById(String index, String type, String id, Map<String, Object> map)
			throws IOException, InterruptedException, ExecutionException;

	/**
	 * 得到多个id的document
	 * 
	 * @param index
	 *            document所在的index
	 * @param type
	 *            document的类型
	 * @param ids
	 *            要查询的document的ids
	 * @return
	 */
	public List<String> multiGetById(String index, String type, Iterable<String> ids);

	/**
	 * 查找
	 * 
	 * @param index
	 *            一或多个index
	 * @param types
	 *            一或者多个type
	 * @param queryBuilder
	 *            查询的具体条件
	 * @return 所有查询到的document
	 */
	public List<String> search(String index, String type, QueryBuilder queryBuilder);

	/**
	 * 查找
	 * 
	 * @param indices
	 *            一或多个index
	 * @param types
	 *            一或者多个type
	 * @param queryBuilder
	 *            查询的具体条件
	 * @return 所有查询到的document
	 */
	public List<String> search(String[] indices, String[] types, QueryBuilder queryBuilder);
}
