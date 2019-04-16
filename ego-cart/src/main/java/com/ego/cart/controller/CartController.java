package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.sun.org.apache.regexp.internal.RE;
import it.ego.commons.pojo.EgoResult;
import it.ego.commons.pojo.TbItemChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName CartController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1213:45
 * @Version 1.0
 **/
@Controller
public class CartController {

@Autowired
private CartService cartServiceImpl;
    /**
     * 添加购物车
     * @param id
     * @return
     */
    @RequestMapping("cart/add/{id}.html")
    public String addCart(@PathVariable long id, int num, HttpServletRequest request){
        cartServiceImpl.addCart(id, num, request);
        return "cartSuccess";
    }

    /**
     * 显示购物车
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("cart/cart.html")
    public String showCart(HttpServletRequest request, Model model){
        model.addAttribute("cartList",cartServiceImpl.showCart(request));
        return  "cart";
    }

    /**
     * 修改购物车中的商品
     * @param id
     * @param num
     * @param request
     * @return
     */
    @RequestMapping("cart/update/num/{id}/{num}.action")
    @ResponseBody
    public EgoResult update(@PathVariable Long id,@PathVariable int num,HttpServletRequest request){
        return    cartServiceImpl.update(id,num,request);
    }

    /**
     * 删除购物车中的商品
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("cart/delete/{id}.action")
    @ResponseBody
    public EgoResult delete(HttpServletRequest request,@PathVariable Long id){

     return    cartServiceImpl.delete(request,id);
    }
}

