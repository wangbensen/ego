package it.wang.ego.dubbo.service;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {

    /**
     * 查询所有的商品规格
     * @param page
     * @param size
     * @return
     */
    EasyUIDataGrid showPage(int page, int size);

    /**
     * 批量删除商品规格
     * @param ids
     * @return
     */
    int delByIds(String ids)throws Exception ;

    /**
     * 根据itemCatId 查询是不是有当前端额规格参数
     * @param itemCatId
     * @return
     */
    TbItemParam selByCatid(Long itemCatId);
    /**
     * 新增,支持主键自增
     * @param param
     * @return
     */
    int insParam(TbItemParam param);

}
