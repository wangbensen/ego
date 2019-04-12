package com.ego.item.controller;

import com.ego.item.service.TbItemParamItemSerice;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.pojo.TbItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TbItemParamItem
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1016:06
 * @Version 1.0
 **/
@Controller
public class TbItemParamItemController {
    @Autowired
    private TbItemParamItemSerice itemParamItemSericeImpl;
    @RequestMapping(value = "item/param/{id}.html",produces="text/html;charset=utf-8")
    @ResponseBody
    public String param(@PathVariable long id){
        return  itemParamItemSericeImpl.param(id);
    }
}
