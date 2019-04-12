package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.TbItemDescService;
import com.ego.redis.dao.JedisDao;
import it.wang.ego.dubbo.service.TbItemDescDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName TbItemDescImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1013:45
 * @Version 1.0
 **/
@Service
public class TbItemDescImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Autowired
    private JedisDao jedisDaoImpl;

    @Value("${redis.desc.key}")
    private  String descKey;

    public String desc(Long id) {
        if (jedisDaoImpl.exists(descKey+id)){
            String s = jedisDaoImpl.get(descKey + id);
            if(s!=null&&s.equals("")){
                return s;
            }
        }
        String itemDesc = tbItemDescDubboServiceImpl.selTbItemDescById(id).getItemDesc();
        //保存到reids中
        jedisDaoImpl.set(descKey+id ,itemDesc);
        return itemDesc;

    }
}
