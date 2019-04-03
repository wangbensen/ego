package it.wang.ego.manage.controller;

import it.ego.commons.pojo.EasyUiTree;
import it.ego.commons.pojo.EgoResult;
import it.wang.ego.manage.service.TbContentCategoryService;
import it.wang.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TbContentCategoryController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/39:40
 * @Version 1.0
 **/
@Controller
public class TbContentCategoryController {
    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUiTree> showCategory(@RequestParam(defaultValue="0")long id){
        return tbContentCategoryServiceImpl.showCategory(id);
    }
    @RequestMapping("content/category/create")
    @ResponseBody
    public EgoResult create(TbContentCategory cate){
        try {
            return tbContentCategoryServiceImpl.insTbContentCategory(cate);
        } catch (Exception e) {
            EgoResult egoResult = new EgoResult();
            egoResult.setStatus(500);
            return egoResult;
        }
    }


    /**
     * 重命名
     * @param id
     * @return
     */
    @RequestMapping("content/category/update")
    @ResponseBody
    public EgoResult update(Long id){
        try {
            return tbContentCategoryServiceImpl.update(id);
        } catch (Exception e) {
            EgoResult egoResult = new EgoResult();
            egoResult.setStatus(500);
            e.printStackTrace();
            return egoResult;
        }
    }
    /**
     * 删除
     * @param cate
     * @return
     */
    @RequestMapping("content/category/delete")
    @ResponseBody
    public EgoResult delete(long id){
        try {
            return tbContentCategoryServiceImpl.update(id);
        } catch (Exception e) {
            EgoResult egoResult = new EgoResult();
            egoResult.setStatus(500);
            e.printStackTrace();
            return egoResult;
        }
    }
}
