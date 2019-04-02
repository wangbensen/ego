package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbItem;
import it.wang.ego.pojo.TbItemCat;
import it.wang.ego.pojo.TbItemDesc;

import java.util.List;

/**
 * @ClassName TbItemCatDubboService
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/111:02
 * @Version 1.0
 **/
public interface TbItemCatDubboService {
    /**
     * 根据父类目 id 查询所有子类目
     * @param pid
     * @return
     */
    List<TbItemCat> show(long pid);

    /**
     * 根据id 查询具体的商品规格
     * @param id
     * @return
     */
    TbItemCat selById(long id);
}
