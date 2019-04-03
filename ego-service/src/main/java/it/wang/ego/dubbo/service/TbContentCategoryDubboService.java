package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryDubboService {
    /**
     * 根据pid查询所有的子类目
     * @param pid
     * @return
     */
    List<TbContentCategory> selByPid(long pid);

    /**
     * 新增商品分类 包含修改
     * @param tbContentCategory
     * @return
     */
    int insTbContentcategory(TbContentCategory tbContentCategory,TbContentCategory tbContentCategory2)throws Exception ;

    /**
     * 修改商品分类
     * @param tbContentCategory
     * @return
     */
    int updTbContentcategory(TbContentCategory tbContentCategory)throws Exception;

    /**
     * 通过 id 查询内容类目详细信息
     */
    TbContentCategory selById(long id);

    /**
     * 根据id删除 当前商品分类，同时要判断其父节点的 isParaent 状态
     * @param id
     * @return
     */
    int delTbContentCateGory (Long id) throws Exception;



}
