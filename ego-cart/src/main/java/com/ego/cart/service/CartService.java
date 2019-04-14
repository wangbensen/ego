package com.ego.cart.service;

import it.ego.commons.pojo.EgoResult;
import it.ego.commons.pojo.TbItemChild;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CartService {
    /**
     * 添加到购物车
     * @param id
     * @param num
     * @param request
     */
    void addCart(long id, int num , HttpServletRequest request);

    /**
     * 显示购物车信息
     * @return
     */
    List<TbItemChild> showCart(HttpServletRequest request);

    /**
     * 修改用户的商品
     * @param id
     * @param num
     * @return
     */
    EgoResult update(Long id, int num,HttpServletRequest request);


    /**
     * 根据商品id 删除某个商品
     * @param request
     * @param id
     */
    EgoResult delete(HttpServletRequest request,Long id);
}
