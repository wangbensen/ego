package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.TbItemService;
import com.ego.redis.dao.JedisDao;
import it.ego.commons.pojo.TbItemChild;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbItemDubboService;
import it.wang.ego.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName TbItemServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/109:33
 * @Version 1.0
 **/
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Autowired
    private JedisDao jedisDaoImpl;

    @Value("${redis.item.key}")
    private  String itemKey;

    public TbItemChild show(long id) {
        String key=itemKey+id;
        //首先看reids中是否有该数据
        if(jedisDaoImpl.exists(key)){
            String value = jedisDaoImpl.get(key);
            if(value!=null&&!value.equals("")){
                TbItemChild tbItemChild = JsonUtils.jsonToPojo(value, TbItemChild.class);
                return tbItemChild;
            }
        }
        TbItem item = tbItemDubboServiceImpl.selById(id);
        TbItemChild child = new TbItemChild();
        child.setId(item.getId());
        child.setTitle(item.getTitle());
        child.setPrice(item.getPrice());
        child.setSellPoint(item.getSellPoint());
        child.setImages(item.getImage()!=null&&!item.equals
                ("")?item.getImage().split(","):new String[1]);
        //保存数据到 redis
        jedisDaoImpl.set(key,JsonUtils.objectToJson(child));
        return child;
    }
}
