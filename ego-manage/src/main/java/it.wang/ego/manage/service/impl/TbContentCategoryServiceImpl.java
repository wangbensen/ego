package it.wang.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import it.ego.commons.pojo.EasyUiTree;
import it.ego.commons.pojo.EgoResult;
import it.ego.commons.utils.IDUtils;
import it.wang.ego.dubbo.service.TbContentCategoryDubboService;
import it.wang.ego.manage.service.TbContentCategoryService;
import it.wang.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName TbContentCategoryServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/39:16
 * @Version 1.0
 **/
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
    @Override
    public List<EasyUiTree> showCategory(long pid) {
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboServiceImpl.selByPid(pid);
        List<EasyUiTree> easyUiTreeList=new ArrayList<EasyUiTree>();
        for (TbContentCategory tbContentCategory:tbContentCategories){
            EasyUiTree easyUiTree=new EasyUiTree();
            easyUiTree.setId(tbContentCategory.getId());
            easyUiTree.setText(tbContentCategory.getName());
            easyUiTree.setState(tbContentCategory.getIsParent()?"closed":"open");
            easyUiTreeList.add(easyUiTree);
        }
        return easyUiTreeList;
    }

    @Override
    public EgoResult insTbContentCategory(TbContentCategory tbContentCategory) throws Exception {
        // 首先判断当前节点名称是否存在
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboServiceImpl.selByPid(tbContentCategory.getParentId());
        EgoResult egoResult = new EgoResult();
        for (TbContentCategory tbContentCategory1:tbContentCategories){
            if (tbContentCategory.getName().equals(tbContentCategory1.getName())){
                 egoResult.setStatus(500);
                return egoResult;
            }
        }
        //新增内容分类
        Date date = new Date();
        long id = IDUtils.genItemId();
        tbContentCategory.setId(id);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setCreated(date);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);

        TbContentCategory parent = new
                TbContentCategory();
        parent.setId(tbContentCategory.getParentId());
        parent.setIsParent(true);
        int i = tbContentCategoryDubboServiceImpl.insTbContentcategory(tbContentCategory, parent);
        egoResult.setStatus(200);
        Map<String,Long> map = new HashMap();
        map.put("id", id);
        egoResult.setData(map);
        return egoResult;
    }

    /**
     * 修改某个分类
     * @param tbContentCategory
     * @return
     * @throws Exception
     */
    @Override
    public EgoResult updTbContentCategory(TbContentCategory tbContentCategory) throws Exception {
        // 首先判断当前节点名称是否存在
        TbContentCategory tbContentCategory2 = tbContentCategoryDubboServiceImpl.selById(tbContentCategory.getId());
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboServiceImpl.selByPid(tbContentCategory2.getParentId());
        EgoResult egoResult = new EgoResult();
        for (TbContentCategory tbContentCategory1:tbContentCategories){
            if (tbContentCategory.getName().equals(tbContentCategory1.getName())){
                egoResult.setStatus(500);
                return egoResult;
            }
        }
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryDubboServiceImpl.updTbContentcategory(tbContentCategory);
        egoResult.setStatus(200);
        return egoResult;
    }

    @Override
    public EgoResult update(Long id) throws Exception{
        int i = tbContentCategoryDubboServiceImpl.delTbContentCateGory(id);
        EgoResult egoResult =new EgoResult();
        if (i==1){
            egoResult.setStatus(200);

        }else {
            egoResult.setStatus(500);
        }
         return  egoResult;
    }
}
