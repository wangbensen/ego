package com.ego.search.test;

import com.alibaba.dubbo.container.Main;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

public class Test {
//	public static void main(String[] args) {
//		Main.main(args);
//	}



	public static void main(String[] args)  throws SolrServerException, IOException {
		SolrClient client = new HttpSolrClient("http://47.102.102.102:8088/solr/");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "005");
//		doc.addField("bjsxt", "java和大数据培训");
		doc.addField("bjsxt1", "大数据4");

		client.add(doc);
		client.commit();
		System.out.println("000000");
	}
}
