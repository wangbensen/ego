package it.wang.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.redis.dao.JedisDao;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbContentDubboService;
import it.wang.ego.manage.service.TbContentService;
import it.wang.ego.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName TbContentServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/314:27
 * @Version 1.0
 **/
@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.bigpic.key}")
    private String bigpic;
    @Override
    public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.selContentByPage(categoryId,page,rows);
    }

    @Override
    public int save(TbContent tbContent) throws Exception {
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        int index = tbContentDubboServiceImpl.insContent(tbContent);
        //同步数据到reids中
        if(jedisDaoImpl.exists(bigpic)){
            String s = jedisDaoImpl.get(bigpic);
            if(s!=null&&!s.equals("")){
                List<HashMap> hashMaps = JsonUtils.jsonToList(s, HashMap.class);
                HashMap<String,Object> map=new HashMap();
                map.put("srcB", tbContent.getPic2());
                map.put("height", 240);
                map.put("alt", "对不起,加载图片失败");
                map.put("width", 670);
                map.put("src", tbContent.getPic());
                map.put("widthB", 550);
                map.put("href", tbContent.getUrl() );
                map.put("heightB", 240);
                if (hashMaps.size()==6){
                    hashMaps.remove(5);
                }
                hashMaps.add(0,map);
                //放到redis 中
                jedisDaoImpl.set(bigpic,JsonUtils.objectToJson(hashMaps));
            }
        }
        return index;
    }


}
