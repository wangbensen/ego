package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.search.service.TbItemService;
import it.ego.commons.pojo.TbItemChild;
import it.wang.ego.dubbo.service.TbItemCatDubboService;
import it.wang.ego.dubbo.service.TbItemDescDubboService;
import it.wang.ego.dubbo.service.TbItemDubboService;
import it.wang.ego.pojo.TbItem;
import it.wang.ego.pojo.TbItemCat;
import it.wang.ego.pojo.TbItemDesc;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbItemServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/815:21
 * @Version 1.0
 **/
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Autowired
    private SolrClient solrClient;

    @Override
    public void init() throws IOException, SolrServerException {
        //   SolrClient solrClient=new HttpSolrClient("http://192.168.249.131:8080/solr/");
        // SolrClient solrClient = new HttpSolrClient("http://47.102.102.102:8088/solr/");
        //先删除全部的索引库信息
        solrClient.deleteByQuery("*:*");
        solrClient.commit();

        //1. 首先查询所有的商品信息
        List<TbItem> tbItems = tbItemDubboServiceImpl.selAllByStatus((byte) 1);
        for (TbItem tbItem : tbItems) {
            //根据cid 查询所属类目
            TbItemCat tbItemCat = tbItemCatDubboServiceImpl.selById(tbItem.getCid());
            //根据itemid 查询商品的描述信息
            TbItemDesc tbItemDesc = tbItemDescDubboServiceImpl.selTbItemDescById(tbItem.getId());
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", tbItem.getId());
            doc.addField("item_title", tbItem.getTitle());
            doc.addField("item_sell_point", tbItem.getSellPoint());
            doc.addField("item_price", tbItem.getPrice());
            doc.addField("item_image", tbItem.getImage());
            doc.addField("item_category_name", tbItemCat.getName());
            doc.addField("item_desc", tbItemDesc.getItemDesc());
            doc.setField("item_update", tbItem.getUpdated());
            solrClient.add(doc);
        }
        solrClient.commit();
    }

    @Override
    public Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException {
        //查询条件对象
        SolrQuery params = new SolrQuery();
        //设置分页
        params.setStart(rows * (page - 1));
        params.setRows(rows);
        //设置要查询的条件 item_keywords是组合字段
        params.setQuery("item_keywords:" + query);
        params.setSort("item_update", SolrQuery.ORDER.desc);
        //开启高亮
        params.setHighlight(true);
        //设置高亮的字段
        params.addHighlightField("item_title");
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePost("</span>");
        QueryResponse response = solrClient.query(params);
        //用户封装数据的
        List<TbItemChild> listChild = new ArrayList();
        //获取未高亮的内容
        SolrDocumentList listSolr = response.getResults();
        //获取高亮的类容
        Map<String, Map<String, List<String>>> hh = response.getHighlighting();
        Map<String, Object> resultMap = new HashMap();

        for (SolrDocument doc : listSolr) {
            //封装页面要的值
            TbItemChild tbItemChild = new TbItemChild();
            tbItemChild.setId(Long.parseLong(doc.getFieldValue("id").toString()));
            String images = (String) doc.getFieldValue("item_image");
            tbItemChild.setImages(images == null || images.equals("") ? new String[1] : images.split(","));
            tbItemChild.setSellPoint(doc.getFieldValue("item_sell_point").toString());
            tbItemChild.setPrice((Long) doc.getFieldValue("item_price"));
            List<String> list = hh.get(doc.getFieldValue("id")).get("item_title");
            if (list != null && !list.equals("")) {
                tbItemChild.setTitle(list.get(0));
            } else {
                tbItemChild.setTitle(doc.getFieldValue("item_title").toString());
            }
            listChild.add(tbItemChild);
        }
        resultMap.put("itemList", listChild);
        resultMap.put("totalPages", listSolr.getNumFound() % rows == 0 ? (listSolr.getNumFound() / rows) : (listSolr.getNumFound() / rows) + 1);
        return resultMap;
    }

    @Override
    public int add(Map<String, Object> map, String desc) throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", map.get("id"));
        doc.setField("item_title", map.get("title"));
        doc.setField("item_sell_point",
                map.get("sellPoint"));
        doc.setField("item_price", map.get("price"));
        doc.setField("item_image", map.get("image"));
        doc.setField("item_category_name", tbItemCatDubboServiceImpl.selById((Integer) map.get("cid")).getName());
        doc.setField("item_desc", desc);
        UpdateResponse add = solrClient.add(doc);
        solrClient.commit();
        if (add.getStatus() == 0) {
            return 1;
        }
        return 0;
    }
}
