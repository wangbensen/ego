package com.ego.order.controller;

import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import it.ego.commons.pojo.TbItemChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName TbOrderController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1515:05
 * @Version 1.0
 **/
@Controller
public class TbOrderController {
    @Autowired
    private TbOrderService tbOrderServiceImpl;

    @RequestMapping("order/order-cart.html")
    public String showCartOrder(@RequestParam("id") List<Long> ids, HttpServletRequest request, Model model) {
        List<TbItemChild> tbItemChildren = tbOrderServiceImpl.showCartOrder(ids, request);
            model.addAttribute("cartList",tbItemChildren);
        return "order-cart";
    }
    @RequestMapping("order/create.html")
    public String createOrder(MyOrderParam myOrderParam,HttpServletRequest request){
        int index = tbOrderServiceImpl.createOrder(myOrderParam, request);
        if (index==1) {
            return "success";
        }else {
            return  "error/exception";
        }
    }
}
