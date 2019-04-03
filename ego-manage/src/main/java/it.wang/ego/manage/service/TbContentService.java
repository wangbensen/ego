package it.wang.ego.manage.service;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.ego.commons.pojo.EgoResult;
import it.wang.ego.pojo.TbContent;

public interface TbContentService {

    /**
     * 分页显示内容信息
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid showContent(long categoryId, int
            page, int rows);

    int save(TbContent tbContent) throws Exception;
}
