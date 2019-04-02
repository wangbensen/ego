package it.wang.ego.manage.controller;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.ego.commons.pojo.EgoResult;
import it.wang.ego.manage.service.TbItemParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName TbItemParamController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/29:26
 * @Version 1.0
 **/
@Controller
public class TbItemParamController {

    @Resource
    private TbItemParamService tbItemParamServiceImpl;

    @RequestMapping("item/param/list")
    @ResponseBody
    public EasyUIDataGrid showPage(int page, int rows) {
        EasyUIDataGrid easyUIDataGrid = tbItemParamServiceImpl.showPage(page, rows);
        return easyUIDataGrid;
    }
    @RequestMapping("item/param/delete")
    @ResponseBody
    public EgoResult showPage(String ids) {
        EgoResult egoResult =new EgoResult();
        try {
            int index = tbItemParamServiceImpl.delete(ids);
            egoResult.setStatus(200);
            egoResult.setData("批量删除成功");
            return egoResult;
        } catch (Exception e) {
            egoResult.setStatus(500);
            egoResult.setData(e.toString());
            return egoResult;
        }
    }
}
