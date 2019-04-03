package it.wang.ego.manage.controller;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.ego.commons.pojo.EgoResult;
import it.wang.ego.manage.service.TbItemParamService;
import it.wang.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("item/param/query/itemcatid/{catId}")
    @ResponseBody
    public EgoResult show(@PathVariable  Long catId) {
        EgoResult egoResult =new EgoResult();
        try {
            TbItemParam tbItemParam = tbItemParamServiceImpl.showParam(catId);
            if (tbItemParam!=null){
                egoResult.setStatus(200);
                egoResult.setData(tbItemParam);
            }
            return egoResult;
        } catch (Exception e) {
            egoResult.setStatus(500);
            egoResult.setData(e.toString());
            return egoResult;
        }
    }

    @RequestMapping("item/param/save/{catId}")
    @ResponseBody
    public EgoResult save(@PathVariable  Long catId,String paramData) {
        EgoResult egoResult =new EgoResult();
        try {
            TbItemParam tbItemParam1 = new TbItemParam();
            tbItemParam1.setItemCatId(catId);
            tbItemParam1.setParamData(paramData);
            int i = tbItemParamServiceImpl.save(tbItemParam1);
            if (i==1){
                egoResult.setStatus(200);
            }else{
                egoResult.setStatus(500);
            }
            return egoResult;
        } catch (Exception e) {
            egoResult.setStatus(500);
            return egoResult;
        }
    }
}
