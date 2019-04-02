package it.wang.ego.manage.controller;


import it.ego.commons.pojo.EasyUiTree;
import it.wang.ego.manage.service.TbItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * @ClassName TbItemCatController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/113:22
 * @Version 1.0
 **/
@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;
    /**
     * 显示商品类目
     * @param id
     * @return
     */
    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<EasyUiTree> showCat(@RequestParam(defaultValue="0") long id){
        return tbItemCatServiceImpl.show(id);
    }

}
