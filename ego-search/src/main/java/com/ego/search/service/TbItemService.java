package com.ego.search.service;

import it.wang.ego.pojo.TbItem;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

public interface TbItemService {
    /**
     * 初始化
     *
     * @throws
     * @throws IOException
     */
    void init() throws IOException, SolrServerException;

    /**
     * 分页查询 商品信息
     *
     * @param query
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException;

    /**
     * 新增时同步数据到solr 索引库中
     *
     * @param map
     * @param desc
     * @return
     */
    int add(Map<String, Object> map, String desc) throws Exception;
}
