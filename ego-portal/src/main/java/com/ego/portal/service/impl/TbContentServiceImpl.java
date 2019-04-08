package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbContentDubboService;
import it.wang.ego.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbContentServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/317:28
 * @Version 1.0
 **/
@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Resource
    private JedisDao JedisDaoImpl;
    @Value("${redis.bigpic.key}")
    private String bigpic;
    @Override
    public String showBigPic() {
        if (JedisDaoImpl.exists(bigpic)){
            String s = JedisDaoImpl.get(bigpic);
            if(s!=null&&!s.equals("")){
            return  s;
            }
        }
        List<TbContent> list = tbContentDubboServiceImpl.selByCount(6, true);
        List<Map<String,Object>> listResult=new ArrayList<Map<String, Object>>();
        for (TbContent tc : list) {
            Map<String, Object> map = new HashMap();
            map.put("srcB", tc.getPic2());
            map.put("height", 240);
            map.put("alt", "对不起,加载图片失败");
            map.put("width", 670);
            map.put("src", tc.getPic());
            map.put("widthB", 550);
            map.put("href", tc.getUrl());
            map.put("heightB", 240);
            listResult.add(map);
        }
        String listJson =
                JsonUtils.objectToJson(listResult);
        //把数据放入到 redis 中
         JedisDaoImpl.set(bigpic, listJson);
        return listJson;
    }
}
