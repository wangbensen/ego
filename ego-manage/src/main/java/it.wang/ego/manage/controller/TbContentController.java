package it.wang.ego.manage.controller;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.ego.commons.pojo.EgoResult;
import it.wang.ego.manage.service.TbContentService;
import it.wang.ego.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName TbContentController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/314:29
 * @Version 1.0
 **/
@Controller
public class TbContentController {
    @Resource
    private TbContentService tbContentServiceImpl;
    /**
     * 显示内容信息
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("content/query/list")
    @ResponseBody
    public EasyUIDataGrid showContent(long categoryId, int page, int rows){
        return tbContentServiceImpl.showContent(categoryId, page, rows);
    }

    /**
     * 新增内容管理
     * @param tbContent
     * @return
     */
    @RequestMapping("content/save")
    @ResponseBody
    public EgoResult save(TbContent tbContent){
        EgoResult egoResult=new EgoResult();
        try {
            int save = tbContentServiceImpl.save(tbContent);
            if (save==1){
                egoResult.setStatus(200);
            }
            return egoResult;
        } catch (Exception e) {
            e.printStackTrace();
            egoResult.setStatus(500);
            return egoResult;
        }
    }
}
