package com.ego.item.controller;

import com.ego.item.service.TbItemService;
import it.ego.commons.pojo.TbItemChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName TbItemController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1010:00
 * @Version 1.0
 **/
@Controller
public class TbItemController {
    @Autowired
    private TbItemService tbItemServiceImpl;

    @RequestMapping("item/{id}.html")
    public String showItemDetails(@PathVariable Long id, Model model){
        TbItemChild show = tbItemServiceImpl.show(id);
        model.addAttribute("item",show);
        return "item";
    }
}
