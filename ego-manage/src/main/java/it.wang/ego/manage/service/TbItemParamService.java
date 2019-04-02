package it.wang.ego.manage.service;

import it.ego.commons.pojo.EasyUIDataGrid;

public interface TbItemParamService {
    /**
     * 查询规格参数  分页
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    EasyUIDataGrid showPage(int page, int rows);

    /**
     * 批量删除 商品规格信息
     * @param ids
     * @return
     * @throws Exception
     */
    int delete(String ids) throws Exception;
}
