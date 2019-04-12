package it.wang.ego.manage.service.impl;


import com.ego.redis.dao.JedisDao;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.ego.commons.utils.HttpClientUtil;
import it.ego.commons.utils.IDUtils;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbItemDubboService;
import it.wang.ego.pojo.TbItem;
import it.wang.ego.manage.service.TbItemService;
import it.wang.ego.pojo.TbItemDesc;
import it.wang.ego.pojo.TbItemParam;
import it.wang.ego.pojo.TbItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class TbItemServiceImpl implements TbItemService {
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Value("${search.url}")
	private String url;
	@Value("${redis.item.key}")
	private  String itemKey;
	@Autowired
	private JedisDao jedisDaoImpl;

	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemDubboServiceImpl.show(page, rows);
	}
	@Override
	public int update(String ids, byte status) {
		int index = 0 ;
		TbItem item = new TbItem();
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			item.setId(Long.parseLong(id));
			item.setStatus(status);
			index +=tbItemDubboServiceImpl.updItemStatus(item);
			//商品修改上架下架 删除redis中对应的数据
			if(status==2||status==3){
				jedisDaoImpl.del(itemKey+id);
			}

		}
		if(index==idsStr.length){
			return 1;
		}
		return 0;
	}
	@Override
	public int save(TbItem item, String desc,String itemParams) throws
			Exception {
		long id = IDUtils.genItemId();
		item.setId(id);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte) 1);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(id);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		TbItemParamItem tbItemParamItem=new TbItemParamItem();
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setUpdated(date);
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setItemId(id);
		int index = 0;
		index =
				tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc,tbItemParamItem);
		System.out.println("index:" + index);
		//应为索引库同步成功与否和添加图片成功没有影响  所以开启另一个线程执行
		final TbItem itemFinal=item;
		final String descFinal=desc;
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map =new HashMap();
				map.put("item",itemFinal);
				map.put("desc",descFinal);
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
			}
		}.start();
		return index;
	}


}
