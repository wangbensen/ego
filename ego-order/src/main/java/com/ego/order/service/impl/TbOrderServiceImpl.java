package com.ego.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.redis.dao.JedisDao;
import com.sun.org.apache.regexp.internal.RE;
import it.ego.commons.pojo.EgoResult;
import it.ego.commons.pojo.TbItemChild;
import it.ego.commons.utils.CookieUtils;
import it.ego.commons.utils.HttpClientUtil;
import it.ego.commons.utils.IDUtils;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbItemDubboService;
import it.wang.ego.dubbo.service.TbOrderDubboService;
import it.wang.ego.pojo.TbOrder;
import it.wang.ego.pojo.TbOrderItem;
import it.wang.ego.pojo.TbOrderShipping;
import it.wang.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ClassName TbOrderServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1515:11
 * @Version 1.0
 **/
@Service
public class TbOrderServiceImpl implements TbOrderService {
    @Autowired
    private JedisDao jedisDaoImpl;
    @Reference
    private TbItemDubboService itemDubboServiceImpl;
    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;
    @Value("${passport.url}")
    private String passportUrl;
    @Value("${cart.key}")
    private String cartKey;

    @Override
    public List<TbItemChild> showCartOrder(List<Long> ids, HttpServletRequest request) {
        //首先从reids 中取出 取出所有数据
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String s = HttpClientUtil.doPost(passportUrl+token);
        EgoResult egoResult = JsonUtils.jsonToPojo(s, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) egoResult.getData()).get("username");
        String s1 = jedisDaoImpl.get(key);
        List<TbItemChild> tbItemChildren = JsonUtils.jsonToList(s1, TbItemChild.class);
       //用户返回的对象
        List<TbItemChild> tbItemChildren1=new ArrayList();
        //遍历tbItemChildren  移除 用户没有勾选的商品
        for (TbItemChild tbItemChild:tbItemChildren){
            for (Long id:ids){
                //判断商品是否勾选
                if(((long)tbItemChild.getId())==id){
                    //同时设置该商品有货无货状态
                    //获取系统改商品有的数量
                    Integer num = itemDubboServiceImpl.selById(id).getNum();
                    //获取该用户购买的数量
                    Integer num1 = tbItemChild.getNum();
                    if(num1<=num){
                        tbItemChild.setEnough(true);
                    }
                    tbItemChildren1.add(tbItemChild);
                }
            }
        }
        return tbItemChildren1;
    }

    @Override
    public int createOrder(MyOrderParam myOrderParam,HttpServletRequest request) {
        //首先从reids 中取出 取出所有数据
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String s = HttpClientUtil.doPost(passportUrl+token);
        EgoResult egoResult = JsonUtils.jsonToPojo(s, EgoResult.class);
       Map user = ((LinkedHashMap) egoResult.getData());
        //创建订单对象
        TbOrder tbOrder=new TbOrder();
        Date date = new Date();
        String orderId = IDUtils.genItemId() + "";
        tbOrder.setOrderId(orderId);
        tbOrder.setPaymentType(myOrderParam.getPaymentType());
        tbOrder.setPayment(myOrderParam.getPayment());
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        tbOrder.setUserId(Long.parseLong( user.get("id").toString()));
        tbOrder.setBuyerNick(user.get("username")+"");
        tbOrder.setBuyerRate(0);
        //创建订单商品对象
        List<TbOrderItem> orderItems = myOrderParam.getOrderItems();
        for (TbOrderItem tbOrderItem:orderItems){
            tbOrderItem.setId(IDUtils.genItemId()+"");
            tbOrderItem.setOrderId(orderId);
        }
        //创建订单 购买人物流信息
        TbOrderShipping tbOrderShipping = myOrderParam.getOrderShipping();
        tbOrderShipping.setOrderId(orderId);
        tbOrderShipping.setCreated(date);
        tbOrderShipping.setUpdated(date);
        try {
            //把具体数据插入数据库
            int order = tbOrderDubboServiceImpl.createOrder(tbOrder, orderItems, tbOrderShipping);
            if (order==1){
                //清空购物车
               //获取所有购物车的信息
                String key = cartKey + ((LinkedHashMap) egoResult.getData()).get("username");
                String s1 = jedisDaoImpl.get(key);
                //获取redis 中该用户对应的所有购物车的数据
                List<TbItemChild> tbItemChildren = JsonUtils.jsonToList(s1, TbItemChild.class);
                // 用户付过款的数据
                List<TbItemChild> tbItemChildren2=new ArrayList<TbItemChild>();
                for(TbItemChild tbItemChild:tbItemChildren){
                    for(TbOrderItem tbOrderItem:orderItems){
                        if ((long)Long.parseLong(tbOrderItem.getItemId())==(long)tbItemChild.getId()){
                            tbItemChildren2.add(tbItemChild);
                        }
                    }
                }
                //更新redis中的数据
                for (TbItemChild tbItemChild:tbItemChildren2){
                    tbItemChildren.remove(tbItemChild);
                }
                //把更新过的数据在放入reids中
                jedisDaoImpl.set(key,JsonUtils.objectToJson(tbItemChildren));
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
