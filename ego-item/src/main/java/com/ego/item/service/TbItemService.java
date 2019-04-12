package com.ego.item.service;

import it.ego.commons.pojo.TbItemChild;

public interface TbItemService {
    /**
     * 根据商品id 查询商品信息并封装成指定格式
     * @param  id
     * @return
     */
    TbItemChild show(long id);
}
