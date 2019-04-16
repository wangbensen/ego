package com.ego.order.service;

import com.ego.order.pojo.MyOrderParam;
import it.ego.commons.pojo.TbItemChild;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TbOrderService {
    /**
     * 显示购物车的数据
     *
     * @return
     */
    List<TbItemChild> showCartOrder(List<Long> ids, HttpServletRequest request);

    /**
     * 创建订单
     *
     * @param myOrderParams
     * @return
     */
    int createOrder(MyOrderParam myOrderParam,HttpServletRequest request);
}