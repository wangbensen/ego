package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @ClassName TbContentController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/317:29
 * @Version 1.0
 **/
@Controller
public class TbContentController {
    @Resource
    private TbContentService tbContentServiceImpl;
    @RequestMapping("showBigPic")
    public String showBigPic(Model model){
        model.addAttribute("ad1",
                tbContentServiceImpl.showBigPic());
        return "index";
    }
}
