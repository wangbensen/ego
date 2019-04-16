package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbOrder;
import it.wang.ego.pojo.TbOrderItem;
import it.wang.ego.pojo.TbOrderShipping;

import java.util.List;

public interface TbOrderDubboService {
    /**
     * 创建订单
     * @param tbOrder
     * @param tbOrderItems
     * @param tbOrderShipping
     * @return
     */
    int createOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItems, TbOrderShipping tbOrderShipping)throws Exception;
}
