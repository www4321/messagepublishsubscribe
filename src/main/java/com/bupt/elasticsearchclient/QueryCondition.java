package com.bupt.elasticsearchclient;

import static org.elasticsearch.index.query.QueryBuilders.commonTermsQuery;
import static org.elasticsearch.index.query.QueryBuilders.existsQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;
import static org.elasticsearch.index.query.QueryBuilders.idsQuery;
import static org.elasticsearch.index.query.QueryBuilders.limitQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;
import static org.elasticsearch.index.query.QueryBuilders.simpleQueryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsLookupQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.typeQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

/**
 * Copyright 2017 com.bupt
 * 
 * @author gaoyang
 * @Describe TODO
 * @data Date:2017年11月20日下午2:25:52
 * @version v1.0
 */
public class QueryCondition {

	/**
	 * A query that match on all documents.
	 * 
	 * @return
	 */
	public QueryBuilder match_All_Query() {
		return matchAllQuery();
	}

	// 以下是Full text queries

	/**
	 * Creates a match query with type "BOOLEAN" for the provided field name and
	 * text.
	 * 
	 * @param field
	 * @param text
	 * @return
	 */
	public QueryBuilder match_query(String Name, Object text) {
		return matchQuery(Name, // field
				text); // text
	}

	/**
	 * Creates a match query with type "BOOLEAN" for the provided field name and
	 * text.
	 * 
	 * @param text
	 * @param fieldNames
	 * @return
	 */
	public QueryBuilder mutil_match_query(Object text, String... fieldNames) {
		return multiMatchQuery(text, // text
				fieldNames); // field
	}

	/**
	 * Creates a common query for the provided field name and text.
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	public QueryBuilder common_terms_query(String name, Object text) {
		return commonTermsQuery(name, text);
	}

	/**
	 * A query that parses a query string and runs it. There are two modes that
	 * this operates. The first, when no field is added (using
	 * {@link QueryStringQueryBuilder#field(String)}, will run the query once
	 * and non prefixed fields will use the
	 * {@link QueryStringQueryBuilder#defaultField(String)} set. The second,
	 * when one or more fields are added (using
	 * {@link QueryStringQueryBuilder#field(String)}), will run the parsed query
	 * against the provided fields, and combine them either using DisMax or a
	 * plain boolean query (see
	 * {@link QueryStringQueryBuilder#useDisMax(boolean)}).
	 * 
	 * @param queryString
	 * @return
	 */
	public QueryBuilder query_string_query(String queryString) {
		return queryStringQuery(queryString);
	}

	/**
	 * A query that acts similar to a query_string query, but won't throw
	 * exceptions for any weird string syntax.
	 * 
	 * @param queryString
	 * @return
	 */
	public QueryBuilder simple_query_string_query(String queryString) {
		return simpleQueryStringQuery(queryString);
	}

	// 以下为Term level queries

	/**
	 * A Query that matches documents containing a term.
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	public QueryBuilder term_query(String name, Object text) {
		return termQuery(name, text);
	}

	/**
	 * A filer for a field based on several terms matching on any of them.
	 * 
	 * @param name
	 * @param values
	 * @return
	 */
	public QueryBuilder terms_query(String name, Object... values) {
		return termsQuery(name, values);
	}

	/**
	 * A Query that matches documents within an range of terms.
	 * 
	 * @param name
	 * @param lower
	 * @param ishaslower
	 * @param higher
	 * @param ishashigher
	 * @return
	 */
	public QueryBuilder range_query(String name, Object lower, boolean ishaslower, Object higher, boolean ishashigher) {
		return rangeQuery(name).from(lower).includeLower(ishaslower).to(higher).includeUpper(ishashigher);
	}

	/**
	 * A filter to filter only documents where a field exists in them.
	 * 
	 * @param name
	 * @return
	 */
	public QueryBuilder exists_query(String name) {
		return existsQuery(name);
	}

	/**
	 * A Query that matches documents containing terms with a specified regular
	 * expression.
	 * 
	 * @param name
	 * @param regexp
	 * @return
	 */
	public QueryBuilder regexp_query(String name, String regexp) {
		return regexpQuery(name, regexp);
	}

	/**
	 * A Query that matches documents containing terms with a specified prefix.
	 * 
	 * @param name
	 * @param prefix
	 * @return
	 */
	public QueryBuilder prefix_query(String name, String prefix) {
		return prefixQuery(name, prefix);
	}

	/**
	 * Implements the wildcard search query. Supported wildcards are <tt>*</tt>,
	 * which matches any character sequence (including the empty one), and
	 * <tt>?</tt>, which matches any single character. Note this query can be
	 * slow, as it needs to iterate over many terms. In order to prevent
	 * extremely slow WildcardQueries, a Wildcard term should not start with one
	 * of the wildcards <tt>*</tt> or <tt>?</tt>.
	 * 
	 * @param name
	 * @param query
	 * @return
	 */
	public QueryBuilder waildcard_query(String name, String query) {
		return wildcardQuery(name, query);
	}

	/**
	 * A Query that matches documents using fuzzy query.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public QueryBuilder fuzzy_query(String name, Object value) {
		return fuzzyQuery(name, value);
	}

	/**
	 * A filter based on doc/mapping type.
	 * 
	 * @param type
	 * @return
	 */
	public QueryBuilder type_query(String type) {
		return typeQuery(type);
	}

	/**
	 * Constructs a query that will match only specific ids within types.
	 * 
	 * @param types
	 *            The mapping/doc type
	 * @return
	 */
	public QueryBuilder ids_query(String... types) {
		return idsQuery(types);
	}

	@SuppressWarnings("deprecation")
	public QueryBuilder limit_query(int limit) {
		return limitQuery(limit);
	}

	/**
	 * A terms lookup filter for the provided field name. A lookup terms filter
	 * can extract the terms to filter by from another doc in an index.
	 * 
	 * @param name
	 * @return
	 */
	public QueryBuilder terms_lookup_query(String name) {
		return termsLookupQuery(name);
	}

/**
			 * 查询与某个查询相近的文档 A more like this query that finds documents that are
			 * "like" the provided
			 * {@link MoreLikeThisQueryBuilder#likeText(String)} which is
			 * checked against the "_all" field.
			 *
			 * @return
			 *//*
			 * public QueryBuilder more_like_this_query() { return
			 * moreLikeThisQuery(); }
			 */

}
