package it.wang.ego.dubbo.service;

import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.pojo.TbContent;

import java.util.List;

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

    /**
     * 查询 最近的前7个
     * @param count
     * @param bool
     * @return
     */
    List<TbContent> selByCount(int count, boolean bool);
}
