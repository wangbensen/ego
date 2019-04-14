package com.ego.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.redis.dao.JedisDao;
import com.sun.org.apache.xpath.internal.operations.Bool;
import it.ego.commons.pojo.EgoResult;
import it.ego.commons.pojo.TbItemChild;
import it.ego.commons.utils.CookieUtils;
import it.ego.commons.utils.HttpClientUtil;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbItemDubboService;
import it.wang.ego.pojo.TbItem;
import it.wang.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @ClassName CartServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1215:21
 * @Version 1.0
 **/
@Service
public class CartServiceImpl implements CartService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImp;
    @Autowired
    private JedisDao jedisDaoImpl;
    @Value("${passport.url}")
    private String passportUrl;
    @Value("${cart.key}")
    private String cartKey;

    @Override
    public void addCart(long id, int num, HttpServletRequest request) {
        List<TbItemChild> childs = new ArrayList();
        //获取该用户的信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String s1 = HttpClientUtil.doPost(passportUrl + token);
        EgoResult egoResult = JsonUtils.jsonToPojo(s1, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) egoResult.getData()).get("username");


        //判断redis 的键是否存在 下面主要是更新商品数量
        if (jedisDaoImpl.exists(key)) {
            String value = jedisDaoImpl.get(key);
            //对应用户在reids 中是否有数据
            if (value != null && !"".equals(value)) {
                //获取reids中该用户对应的购物车数据
                childs = JsonUtils.jsonToList(value, TbItemChild.class);
                for (TbItemChild itemChild : childs) {
                    //如果有当前新增的商品 就直接数量相加
                    if ((long) itemChild.getId() == id) {
                        itemChild.setNum(itemChild.getNum() + num);
                        jedisDaoImpl.set(key, JsonUtils.objectToJson(childs));
                        return;
                    }
                }
            }
        }
        //查询商品的信息 ，并且装换成指定格式
        TbItem item = tbItemDubboServiceImp.selById(id);
        TbItemChild child = new TbItemChild();
        child.setId(item.getId());
        child.setTitle(item.getTitle());
        child.setImages(item.getImage() == null || item.getImage().equals("") ? new
                String[1] : item.getImage().split(","));
        child.setNum(num);
        child.setPrice(item.getPrice());
        //1. 商品第一次新增  2.如果reids 中有数据但是没有该商品的信息
        childs.add(child);
        jedisDaoImpl.set(key, JsonUtils.objectToJson(childs));
    }

    @Override
    public List<TbItemChild> showCart(HttpServletRequest request) {
        //获取该用户的信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String s1 = HttpClientUtil.doPost(passportUrl + token);
        EgoResult egoResult = JsonUtils.jsonToPojo(s1, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) egoResult.getData()).get("username");
        String s = jedisDaoImpl.get(key);
        List<TbItemChild> tbItemChildren = JsonUtils.jsonToList(s, TbItemChild.class);
        return tbItemChildren;
    }

    @Override
    public EgoResult update(Long id, int num,HttpServletRequest request) {
        //获取该用户的信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String s1 = HttpClientUtil.doPost(passportUrl + token);
        EgoResult egoResult = JsonUtils.jsonToPojo(s1, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) egoResult.getData()).get("username");
        String s = jedisDaoImpl.get(key);
        //从reids中获取该用户的购物车数据
        List<TbItemChild> tbItemChildren = JsonUtils.jsonToList(s, TbItemChild.class);
        for (TbItemChild tbItemChild:tbItemChildren){
            //遍历修改其中购物车商品的数量
            if((long)id==tbItemChild.getId()){
                tbItemChild.setNum(num);
            }
        }
        //从新把改用户的数据放入购物车
        String set = jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItemChildren));
        EgoResult egoResult1=new EgoResult();
        if (set.equals("OK")){
            egoResult1.setStatus(200);
        }
        return egoResult1;
    }

    @Override
    public EgoResult delete(HttpServletRequest request, Long id) {
        //获取该用户的信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String s1 = HttpClientUtil.doPost(passportUrl + token);
        EgoResult egoResult = JsonUtils.jsonToPojo(s1, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) egoResult.getData()).get("username");
        String s = jedisDaoImpl.get(key);
        //从reids中获取该用户的购物车数据
        List<TbItemChild> tbItemChildren = JsonUtils.jsonToList(s, TbItemChild.class);
        TbItemChild tbItemChild3=null;
        for (TbItemChild tbItemChild:tbItemChildren){
            //遍历购物车的商品  进行删除
            if((long)id==tbItemChild.getId()){
                tbItemChild3=tbItemChild;
            }
        }
        tbItemChildren.remove(tbItemChild3);
        //重新把购物车的数据放入redis中
        String set = jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItemChildren));
        EgoResult egoResult1 = new EgoResult();
        if(set.equals("OK")){
            egoResult1.setStatus(200);
        }
        return egoResult1;
    }
}
