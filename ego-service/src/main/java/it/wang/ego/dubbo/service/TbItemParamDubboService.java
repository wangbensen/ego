package it.wang.ego.dubbo.service;

import it.ego.commons.pojo.EasyUIDataGrid;

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
}
