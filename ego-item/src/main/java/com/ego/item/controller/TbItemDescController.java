package com.ego.item.controller;

import com.ego.item.service.TbItemDescService;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TbItemDescController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1013:40
 * @Version 1.0
 **/
@Controller
public class TbItemDescController {
    @Autowired
    private TbItemDescService tbItemDescServiceImpl;

    @RequestMapping(value = "item/desc/{id}.html",produces ="text/html;charset=utf-8")
    @ResponseBody
    public String desc(@PathVariable Long id){
        return  tbItemDescServiceImpl.desc(id);
    }
}
