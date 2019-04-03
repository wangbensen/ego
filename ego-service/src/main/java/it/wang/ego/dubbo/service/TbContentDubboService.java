package it.wang.ego.dubbo.service;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.pojo.TbContent;

public interface TbContentDubboService {

    /**
     * 分页查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid selContentByPage(long categoryId, int
            page, int rows);

    /**
     * 保存内容管理
     * @param tbContent
     * @return
     */
    int insContent(TbContent tbContent)throws Exception;
}
