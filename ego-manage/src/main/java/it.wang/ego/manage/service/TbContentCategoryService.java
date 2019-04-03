package it.wang.ego.manage.service;

import it.ego.commons.pojo.EasyUiTree;
import it.ego.commons.pojo.EgoResult;
import it.wang.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryService {
    /**
     * 根据pid 查询所有的内容分类
     * @param pid
     * @return
     */
    List<EasyUiTree> showCategory(long pid);

    /**
     * 新增商品分类
     * @param tbContentCategory
     * @return
     */
    EgoResult insTbContentCategory(TbContentCategory tbContentCategory) throws Exception;

    /**
     * 新增商品分类
     * @param tbContentCategory
     * @return
     */
    EgoResult updTbContentCategory(TbContentCategory tbContentCategory) throws Exception;

    /**
     * 类目重命名
     * @param cate
     * @return
     */
    EgoResult update(Long id) throws Exception;
}
